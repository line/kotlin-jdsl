package com.linecorp.kotlinjdsl.example.spring.batch.repository.employee

import com.linecorp.kotlinjdsl.example.spring.batch.entity.employee.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>
