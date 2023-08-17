package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.repository.publisher

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.publisher.Publisher
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface PublisherRepository : JpaRepository<Publisher, Long>, KotlinJdslJpqlExecutor
