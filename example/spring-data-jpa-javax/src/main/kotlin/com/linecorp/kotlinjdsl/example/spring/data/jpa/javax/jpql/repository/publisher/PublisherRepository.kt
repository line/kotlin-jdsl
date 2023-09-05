@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.repository.publisher

import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.entity.publisher.Publisher
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface PublisherRepository : JpaRepository<Publisher, Long>, KotlinJdslJpqlExecutor
