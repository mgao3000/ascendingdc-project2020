package com.ascendingdc.training.project2020.daoimpl.demo;

import com.ascendingdc.training.project2020.dao.demo.DaoDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DaoDemoImpl implements DaoDemo {

    private static final Logger logger = LoggerFactory.getLogger(DaoDemoImpl.class);

    public String generateGreetingMessage(String name) {
        return "Hi " + name + ", how are you?";
    }

    public boolean validateDrinkingEligibility(int age) {
        boolean isEligible = false;
        if(age > 25)
            isEligible = true;
        return isEligible;
    }


}
