package com.dailycodebuffer.SpringBoot.tutorial.repository;

import com.dailycodebuffer.SpringBoot.tutorial.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    public Department findDepartmentByDepartmentName(String departmentName);

    public Department findDepartmentByDepartmentNameIgnoreCase(String departmentName);
}
