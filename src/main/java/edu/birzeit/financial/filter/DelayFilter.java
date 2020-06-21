package edu.birzeit.financial.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@Order
public class DelayFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("Start filter " + LocalDateTime.now());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
        System.out.println("End filter " + LocalDateTime.now());
    }
}
