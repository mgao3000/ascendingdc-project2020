package com.ascendingdc.training.project2020.daoimpl.springdatajpa;

import com.ascendingdc.training.project2020.dao.hibernate.MajorDao;
import com.ascendingdc.training.project2020.entity.Major;

import java.util.ArrayList;
import java.util.List;

public class MajorDaoStub implements MajorDao {
    @Override
    public Major save(Major major) {
        return new Major();
    }

    @Override
    public Major update(Major major) {
        return new Major();
    }

    @Override
    public boolean deleteByName(String majorName) {
        return true;
    }

    @Override
    public boolean deleteById(Long majorId) {
        return false;
    }

    @Override
    public boolean delete(Major major) {
        return true;
    }

    @Override
    public List<Major> getMajors() {
        List<Major> majorList = new ArrayList<>();
        Major major = new Major();
        majorList.add(major);

        return majorList;
    }

    @Override
    public Major getMajorById(Long id) {
        return new Major();
    }

    @Override
    public Major getMajorByName(String majorName) {
        return  new Major();
    }

    @Override
    public List<Major> getMajorsWithChildren() {
        return null;
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorId(Long majorId) {
        return  new Major();
    }

    @Override
    public Major getMajorAndStudentsAndProjectsByMajorName(String majorName) {
        return  new Major();
    }
}
