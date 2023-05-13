package com.ascendingdc.training.project2020.service;

import com.ascendingdc.training.project2020.dao.hibernate.DummyDao;
import com.ascendingdc.training.project2020.service.impl.DummyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DummyServiceTest {

    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Mock
    private DummyDao mockDummyDao;

    @InjectMocks
    private DummyService dummyService;

    @BeforeEach
    public void setupEach() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testQuery() {
        assertNotNull(mockDummyDao);
        assertNotNull(dummyService);
        when(mockDummyDao.isKeyAvailable()).thenReturn(true);

        boolean flag = dummyService.query("aaa");
        assertFalse(flag);

        verify(mockDummyDao, times(1)).isKeyAvailable();
    }

    @Test
    public void testFindGradeCommentById_withGoodCommentReturned() {
        when(mockDummyDao.getUniqueId()).thenReturn(90);

        String gradeComment = dummyService.findGradeCommentById(90);

        assertEquals("Good", gradeComment);

        verify(mockDummyDao, times(1)).getUniqueId();
    }

    @Test
    public void testFindGradeCommentById_withExcellentReturned() {
        when(mockDummyDao.getUniqueId()).thenReturn(90);

        String gradeComment = dummyService.findGradeCommentById(95);

        assertEquals("Excellent", gradeComment);

        verify(mockDummyDao, times(2)).getUniqueId();
    }

    @Test
    public void testFindGradeCommentById_withNotGoodCommentReturned() {
        when(mockDummyDao.getUniqueId()).thenReturn(90);

        String gradeComment = dummyService.findGradeCommentById(88);

        assertEquals("Not Good", gradeComment);

        verify(mockDummyDao, times(2)).getUniqueId();
    }

    @Test
    public void testFindGradeCommentById_withExcellentCommentReturned() {
        when(mockDummyDao.getUniqueId()).thenReturn(90);

        String gradeComment = dummyService.findGradeCommentById(95);

        assertEquals("Excellent", gradeComment);

        verify(mockDummyDao, times(2)).getUniqueId();
    }

}
