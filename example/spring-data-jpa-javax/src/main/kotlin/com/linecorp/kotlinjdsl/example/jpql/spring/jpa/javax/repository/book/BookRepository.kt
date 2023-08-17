package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.repository.book

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.book.Isbn
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor
