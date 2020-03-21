package edu.birzeit.financial.controller;

import edu.birzeit.financial.dto.BasicResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/health_check", produces = MediaType.APPLICATION_JSON_VALUE)
public class HealthCheckController {

    @Autowired
    private BasicResponse basicResponse;

    @RequestMapping(value = {"/",""}, method = RequestMethod.GET)
    public BasicResponse checkHealth() {

        return this.basicResponse;
    }
}