package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dao.demo.DaoDemo;
import com.ascendingdc.training.project2020.daoimpl.demo.DaoDemoImpl;
import com.ascendingdc.training.project2020.daoimpl.demo.DaoDemoSecondImpl;
import com.ascendingdc.training.project2020.daoimpl.demo.DaoDemoThirdImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(ServiceClient.class);

    public static void main(String[] args) {
        DaoDemoImpl daoDemoImpl = new DaoDemoImpl();
        DaoDemoSecondImpl daoDemoSecond = new DaoDemoSecondImpl();

        DaoDemo daoDemo = new DaoDemoImpl();
        daoDemo = new DaoDemoThirdImpl(); //new DaoDemoImpl();  // new DaoDemoThirdImpl();

        String name = "Frank";
        DemoService demoService = new DemoService(daoDemo);
        logger.info("====Message = {}", demoService.getMessage(name));

        logger.info("====Can the person drink = {}", demoService.checkIfDrinkingOK(22));

    }
}
