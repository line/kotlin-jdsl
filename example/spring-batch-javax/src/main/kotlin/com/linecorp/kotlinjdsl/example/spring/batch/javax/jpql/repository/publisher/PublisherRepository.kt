@file:Suppress("unused")

package com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.repository.publisher

import com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.entity.publisher.Publisher
import org.springframework.data.jpa.repository.JpaRepository

interface PublisherRepository : JpaRepository<Publisher, Long>
