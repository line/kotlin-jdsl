package com.linecorp.kotlinjdsl.example.spring.batch.jpql.repository.department

import com.linecorp.kotlinjdsl.example.spring.batch.jpql.entity.department.Department
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long>
