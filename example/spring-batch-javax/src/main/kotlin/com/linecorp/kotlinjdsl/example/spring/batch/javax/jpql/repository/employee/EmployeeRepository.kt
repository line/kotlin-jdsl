@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.repository.employee

import com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.entity.employee.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long>
