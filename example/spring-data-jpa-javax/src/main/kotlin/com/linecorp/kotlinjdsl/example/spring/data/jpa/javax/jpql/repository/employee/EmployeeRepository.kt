package com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.repository.employee

import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>, KotlinJdslJpqlExecutor
