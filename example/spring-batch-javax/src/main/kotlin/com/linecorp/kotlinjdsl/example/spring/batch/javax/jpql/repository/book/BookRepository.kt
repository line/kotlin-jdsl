package com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.repository.book

import com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.entity.book.Isbn
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Isbn>
