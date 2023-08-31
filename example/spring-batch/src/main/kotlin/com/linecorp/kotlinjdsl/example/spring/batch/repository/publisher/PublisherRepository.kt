package com.linecorp.kotlinjdsl.example.spring.batch.repository.publisher

import com.linecorp.kotlinjdsl.example.spring.batch.entity.publisher.Publisher
import org.springframework.data.jpa.repository.JpaRepository

interface PublisherRepository : JpaRepository<Publisher, Long>
