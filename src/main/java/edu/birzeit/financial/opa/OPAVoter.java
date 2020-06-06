package edu.birzeit.financial.opa;

import edu.birzeit.financial.util.GenericUtil;
import org.springframework.http.HttpEntity;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.FilterInvocation;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class OPAVoter implements AccessDecisionVoter<Object> {

    private String opaUrl;

    public OPAVoter(String opaUrl) {
        this.opaUrl = opaUrl;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class clazz) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object obj, Collection<ConfigAttribute> attrs) {
	
	    System.out.println("Voter called ...." );
	    if(attrs.stream().anyMatch(p -> p.toString().equals("permitAll"))){
            System.out.println(".... its a permitAll API -> ACCESS_GRANTED");
            return ACCESS_GRANTED;
        }

	    if(authentication instanceof AnonymousAuthenticationToken){
            System.out.println(".... its an AnonymousAuthenticationToken -> ACCESS_DENIED");
	        return ACCESS_DENIED;
        }

        if (!(obj instanceof FilterInvocation)) {
            return ACCESS_ABSTAIN;
        }

        FilterInvocation filter = (FilterInvocation) obj;

        // Construct an input for OPA
        Map<String, Object> input = new HashMap<String, Object>();

        // Construct url generic path and request meta
        String[] path = filter.getRequest().getRequestURI().replaceAll("^/|/$", "").split("/");
        String genericPath = "";
        Map<String, Object> requestMeta = new HashMap<String, Object>();
        for (int i=0; i<path.length; i++){

            if (GenericUtil.isNumeric(path[i])){
                String variable= (path[i-1] + "_id");
                genericPath += variable;
                requestMeta.put(variable, Integer.valueOf(path[i]));
            }else {
                genericPath += path[i];
            }
            genericPath += "/";
        }
        input.put("request_meta", requestMeta);
        input.put("url", genericPath);
        System.out.println("genericPath " + genericPath);

        input.put("method", filter.getRequest().getMethod());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        input.put("username", userDetails.getUsername());

        RestTemplate client = new RestTemplate();
        HttpEntity<?> request = new HttpEntity<>(new OPADataRequest(input));
        OPADataResponse response = client.postForObject(this.opaUrl, request, OPADataResponse.class);

        if (!response.getResult()) {
            return ACCESS_DENIED;
        }

        return ACCESS_GRANTED;
    }
}
