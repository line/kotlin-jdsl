package com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.repository.author

import com.linecorp.kotlinjdsl.example.spring.batch.javax.jpql.entity.author.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>
