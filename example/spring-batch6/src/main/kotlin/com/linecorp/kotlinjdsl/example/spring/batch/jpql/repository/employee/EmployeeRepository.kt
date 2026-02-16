@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.batch.jpql.repository.employee

import com.linecorp.kotlinjdsl.example.spring.batch.jpql.entity.employee.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>
