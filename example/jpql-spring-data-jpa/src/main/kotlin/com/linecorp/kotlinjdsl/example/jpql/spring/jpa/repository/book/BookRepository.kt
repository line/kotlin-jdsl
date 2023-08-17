package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.repository.book

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.book.Book
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.entity.book.Isbn
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Isbn>, KotlinJdslJpqlExecutor
