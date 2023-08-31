package com.linecorp.kotlinjdsl.example.spring.batch.repository.department

import com.linecorp.kotlinjdsl.example.spring.batch.entity.department.Department
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long>
