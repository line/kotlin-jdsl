package com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.repository.book

import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.data.jpa.javax.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor
