package com.ascendingdc.training.project2020.service.impl;

import com.ascendingdc.training.project2020.dao.hibernate.DummyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DummyService {

    @Autowired
    private DummyDao dummyDao;

    public boolean query(String query) {
        if(query != null && query.length() > 0) {
            if(dummyDao.isKeyAvailable()) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public String findGradeCommentById(int id) {
        if(id == dummyDao.getUniqueId()) {
            return "Good";
        } else if(id > dummyDao.getUniqueId()) {
            return "Excellent";
        } else {
            return "Not Good";
        }
    }
}
