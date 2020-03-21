package edu.birzeit.financial.controller;


import edu.birzeit.financial.dto.Subscription;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value="/businesses/{businessId}", produces = MediaType.APPLICATION_JSON_VALUE)
public class BusinessController {

    @RequestMapping(value = {"/subscriptions"}, method = RequestMethod.GET)
    public List<Subscription> getActiveSubscriptions(@PathVariable Long businessId) {

        List<Subscription> jobs = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            jobs.add(new Subscription());
        }
        return jobs;
    }

    @RequestMapping(value = {"/subscriptions"}, method = RequestMethod.POST)
    public Subscription createSubscription(@PathVariable Long businessId){

        return new Subscription();
    }

}
