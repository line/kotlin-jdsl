package com.linecorp.kotlinjdsl.example.spring.batch.repository.book

import com.linecorp.kotlinjdsl.example.spring.batch.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.batch.entity.book.Isbn
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Isbn>
