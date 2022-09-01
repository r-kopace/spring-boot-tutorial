package com.dailycodebuffer.SpringBoot.tutorial.service;

import com.dailycodebuffer.SpringBoot.tutorial.entity.Department;
import com.dailycodebuffer.SpringBoot.tutorial.error.DepartmentNotFoundException;
import com.dailycodebuffer.SpringBoot.tutorial.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @MockBean
    DepartmentRepository departmentRepository;

    Department department;
    List<Department> departmentList;

    @BeforeEach
    void setUp() {
        departmentList = new ArrayList<>();
         department =
                Department.builder()
                        .departmentName("IT")
                        .departmentAddress("USA")
                        .departmentCode("IT-06")
                        .departmentId(1L)
                        .build();

        departmentList.add(department);
    }

    @Test
    @DisplayName("Get Data based on Validate Department Name")
    public void whenValidDepartmentName_thenDepartmentShouldFound(){

        Mockito.when(departmentRepository.findDepartmentByDepartmentNameIgnoreCase("IT"))
                .thenReturn(department);

        String departmentName = "IT";
        Department found = departmentService.fetchDepartmentByName(departmentName);
        assertEquals(departmentName, found.getDepartmentName());

    }

    @Test
    public void givenValidDepartmentId_thenShouldDeleteTheDepartment(){

        Long departmentId = 1L;
        departmentService.deleteDepartmentById(departmentId);
        Mockito.verify(departmentRepository, times(1)).deleteById(eq(departmentId));

    }

    @Test
    public void givenValidDepartmentObj_thenShouldSaveTheDepartment(){

        Mockito.when(departmentRepository.save(Mockito.any())).thenReturn(department);
        departmentService.saveDepartment(department);
        Mockito.verify(departmentRepository, times(1)).save(Mockito.any());
    }

    @Test
    public void givenFetchDeptListShouldReturnListOfAllDepartments(){

        departmentRepository.save(department);
        when(departmentRepository.findAll()).thenReturn(departmentList);
        List<Department> departmentList2 = departmentService.fetchDeptList();
        assertEquals(departmentList, departmentList2);
        verify(departmentRepository, times(1)).save(department);
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    public void givenFetchDepartmentId_ShouldReturnTheDepartment() throws DepartmentNotFoundException {
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(department));
        //assertThat(departmentService.fetchDepartmentById(department.getDepartmentId())).isEqualTo(department);

        Long departmentId = 1L;
        Department found = departmentService.fetchDepartmentById(departmentId);
        assertEquals(1L, found.getDepartmentId());
    }

    @Test
    public void givenNotPresentDepartmentId_ShouldReturnException(){
        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable(department));

        Long departmentId = 2L;

        assertThrows(DepartmentNotFoundException.class,
                () -> departmentService.fetchDepartmentById(departmentId),
                "Department Not Available ...");
           //     () -> "Department Not Available ...AA");

    }

    @Test
    public void givenValidateDepartmentId_ShouldReturnUpdatedDepartment(){

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.ofNullable((department)));

        department.setDepartmentName("AAA");

        departmentService.updateDepartment(1L, department);
        assertEquals("AAA", department.getDepartmentName());

    }
}