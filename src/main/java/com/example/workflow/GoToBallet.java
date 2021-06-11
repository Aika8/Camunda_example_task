package com.example.workflow;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class GoToBallet implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        int weight = (int) delegateExecution.getVariable("weight");
        String examResult = "Undefined";
        boolean isPass = false;

        if(weight > 50){
            throw new BpmnError("weightError");
        }

        int score = (int) delegateExecution.getVariable("exam_score");

        if(score > 70){
            isPass = true;
            examResult = "intermediate group";
        }else if(score > 50){
            isPass = true;
            examResult = "beginner group";
        }else{
            isPass = false;
            examResult = "you failed";
        }

        delegateExecution.setVariable("examResult", examResult);
        delegateExecution.setVariable("isPass", isPass);
    }
}
