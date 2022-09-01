package com.dailycodebuffer.SpringBoot.tutorial.repository;

import com.dailycodebuffer.SpringBoot.tutorial.entity.Department;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.util.Assert;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
         Department department =
                Department.builder()
                        .departmentName("Mechanical Engineering")
                        .departmentCode("ME-11")
                        .departmentAddress("Utah")
                        .build();

        entityManager.persist(department);
    }

    @Test
    public void whenFindById_thenReturnDepartment(){
        Department department = departmentRepository.findById(1L).get();
        assertEquals("Mechanical Engineering", department.getDepartmentName());
    }

    @Test
    public void whenFindById_thenReturnDepartment_2(){

        Department tutorials = departmentRepository.findDepartmentByDepartmentName("Mechanical Engineering");
        assertEquals("ME-11", tutorials.getDepartmentCode());
    }

}