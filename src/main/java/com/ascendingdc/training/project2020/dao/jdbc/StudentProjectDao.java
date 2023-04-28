package com.ascendingdc.training.project2020.dao.jdbc;

import com.ascendingdc.training.project2020.dto.ProjectDto;
import com.ascendingdc.training.project2020.dto.StudentDto;

import java.util.List;

public interface StudentProjectDao {
    boolean deleteStudentProjectRelationshipByStudentId(long studentId);
    boolean deleteStudentProjectRelationshipByProjectId(long projectId);
    boolean deleteStudentProjectRelationship(StudentDto student, ProjectDto project);
    boolean deleteStudentProjectRelationship(long studentId, long projectId);
    boolean addStudentProjectRelationship(StudentDto student, ProjectDto project);
    boolean addStudentProjectRelationship(long studentId, long projectId);

    List<Long> findStudentIdListByProjectId(long projectId);

    List<Long> findProjectIdListByStudentId(long studentId);

}
