@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.department

import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.department.Department
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository :
    JpaRepository<Department, Long>,
    KotlinJdslJpqlExecutor
