package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.book

import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface BookPublisherRepository : JpaRepository<BookPublisher, BookPublisher.BookPublisherId>, KotlinJdslJpqlExecutor
