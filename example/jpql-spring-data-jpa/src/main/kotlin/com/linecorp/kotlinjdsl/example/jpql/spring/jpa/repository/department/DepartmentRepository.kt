package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.repository.department

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.department.Department
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long>, KotlinJdslJpqlExecutor
