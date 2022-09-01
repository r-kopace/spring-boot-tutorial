package com.dailycodebuffer.SpringBoot.tutorial.controller;

import com.dailycodebuffer.SpringBoot.tutorial.entity.Department;
import com.dailycodebuffer.SpringBoot.tutorial.error.DepartmentNotFoundException;
import com.dailycodebuffer.SpringBoot.tutorial.service.DepartmentService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department department;

    @BeforeEach
    void setUp() {

        department =
                Department.builder()
                        .departmentName("IT")
                        .departmentAddress("USA")
                        .departmentCode("IT-06")
                        .departmentId(1L)
                        .build();
    }


    @Test
    void saveDepartment() throws Exception {
        Department inputDepartment =
                Department.builder()
                        .departmentName("IT")
                        .departmentAddress("USA")
                        .departmentCode("IT-06")
                        .build();

        Mockito.when(departmentService.saveDepartment(inputDepartment))
                .thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"departmentName\":\"ITA\",\n" +
                        "\t\"departmentAddress\":\"Michigan\",\n" +
                        "\t\"departmentCode\":\"IT-70\"\n" +
                        "}"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void fetchDepartmentById() throws Exception {

        Mockito.when(departmentService.fetchDepartmentById(1L)).thenReturn(department);

        mockMvc.perform(MockMvcRequestBuilders.get("/departments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.departmentName")
                        .value(department.getDepartmentName())
        );
    }








}