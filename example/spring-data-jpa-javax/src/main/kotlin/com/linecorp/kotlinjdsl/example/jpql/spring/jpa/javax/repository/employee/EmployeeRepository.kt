package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.repository.employee

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee.Employee
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>, KotlinJdslJpqlExecutor
