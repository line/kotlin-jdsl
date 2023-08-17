package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.repository.employee

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.employee.Employee
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>, KotlinJdslJpqlExecutor
