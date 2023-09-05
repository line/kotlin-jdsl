@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.repository.department

import com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.entity.department.Department
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long>
