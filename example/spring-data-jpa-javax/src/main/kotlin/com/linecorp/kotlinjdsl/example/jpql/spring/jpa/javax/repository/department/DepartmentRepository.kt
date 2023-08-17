package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.repository.department

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.department.Department
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long>, KotlinJdslJpqlExecutor
