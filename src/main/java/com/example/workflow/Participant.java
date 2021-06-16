package com.example.workflow;

import com.netflix.discovery.EurekaClient;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Participant implements JavaDelegate {

    @Qualifier("eurekaClient")
    @Autowired
    private EurekaClient discoveryClient;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String name = (String) delegateExecution.getVariable("name");
        String url = discoveryClient.getNextServerFromEureka("PERSON_APP", false).getHomePageUrl();

        RestTemplate restTemplate = new RestTemplate();

        System.out.println(url);
        System.out.println(restTemplate.getForEntity(url + "api/persons/by-name?name=" + name, String.class).getBody());
        delegateExecution.setVariable("name", name);

        delegateExecution.setVariable("person", restTemplate.getForEntity(url + "api/persons/by-name?name=" + name, String.class).getBody());
    }
}
