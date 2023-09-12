@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.repository.department

import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.entity.department.Department
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long>, KotlinJdslJpqlExecutor
