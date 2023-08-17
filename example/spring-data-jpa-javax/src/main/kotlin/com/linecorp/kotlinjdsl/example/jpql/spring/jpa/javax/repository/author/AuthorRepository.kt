package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.repository.author

import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.author.Author
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>, KotlinJdslJpqlExecutor
