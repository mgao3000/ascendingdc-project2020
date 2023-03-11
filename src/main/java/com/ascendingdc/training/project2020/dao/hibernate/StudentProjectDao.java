package com.ascendingdc.training.project2020.dao.hibernate;

import com.ascendingdc.training.project2020.entity.Project;
import com.ascendingdc.training.project2020.entity.Student;

public interface StudentProjectDao {
    boolean deleteStudentProjectRelationshipByStudentId(long studentId);
    boolean deleteStudentProjectRelationshipByProjectId(long projectId);
    boolean deleteStudentProjectRelationship(Student student, Project project);
    boolean deleteStudentProjectRelationship(long studentId, long projectId);
    boolean addStudentProjectRelationship(Student student, Project project);
    boolean addStudentProjectRelationship(long studentId, long projectId);
}
