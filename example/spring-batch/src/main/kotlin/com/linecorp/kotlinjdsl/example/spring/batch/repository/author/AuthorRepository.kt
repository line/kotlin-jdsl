package com.linecorp.kotlinjdsl.example.spring.batch.repository.author

import com.linecorp.kotlinjdsl.example.spring.batch.entity.author.Author
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository : JpaRepository<Author, Long>
