package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dao.demo.DaoDemo;
import com.ascendingdc.training.project2020.daoimpl.demo.DaoDemoImpl;
import com.ascendingdc.training.project2020.daoimpl.demo.DaoDemoSecondImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

//    private DaoDemoImpl daoDemo;
//    private DaoDemoSecondImpl daoDemoSecond;
    private DaoDemo daoDemo;

    public DemoService(DaoDemo daoDemo) {
//        daoDemo = new DaoDemoImpl();
//         daoDemoSecond = new DaoDemoSecondImpl();
        this.daoDemo = daoDemo;
    }

    public String getMessage(String name) {
//        String message = "Additional Messages " + daoDemo.generateGreetingMessage(name);
//        String message = "Additional Messages " + daoDemoSecond.generateGreetingMessage(name);
        String message = "Additional Messages " + daoDemo.generateGreetingMessage(name);
        return message;
    }

    public boolean checkIfDrinkingOK(int age) {
//        boolean isOK = daoDemo.validateDrinkingEligibility(age);
//        boolean isOK = daoDemoSecond.validateDrinkingEligibility(age);
        boolean isOK = daoDemo.validateDrinkingEligibility(age);
        return isOK;
    }
}
