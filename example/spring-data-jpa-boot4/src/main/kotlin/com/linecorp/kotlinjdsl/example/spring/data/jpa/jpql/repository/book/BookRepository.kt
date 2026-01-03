package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.book

import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository :
    JpaRepository<Book, Isbn>,
    KotlinJdslJpqlExecutor
