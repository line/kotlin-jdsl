package com.linecorp.kotlinjdsl.spring.data.example

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.example.entity.Book
import com.linecorp.kotlinjdsl.spring.data.listQuery
import com.linecorp.kotlinjdsl.spring.data.singleQuery
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.data.jpa.repository.JpaRepository

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService,
) {
    @PostMapping
    fun createBook(@RequestBody spec: BookService.CreateBookSpec): ResponseEntity<Long> {
        val book = bookService.create(spec)

        return ResponseEntity.ok(book.id)
    }

    @GetMapping("/{bookId}")
    fun findById(@PathVariable bookId: Long): ResponseEntity<Book> {
        val book = bookService.findById(bookId)

        return ResponseEntity.ok(book)
    }

    @GetMapping
    fun findAll(@RequestParam("name") name: String): ResponseEntity<List<Book>> {
        val books = bookService.findAll(BookService.FindBookSpec(name = name))

        return ResponseEntity.ok(books)
    }
}

interface BookRepository : JpaRepository<Book, Long>

@Service
@Transactional
class BookService(
    private val repository: BookRepository,
    private val queryFactory: SpringDataQueryFactory,
) {
    fun create(spec: CreateBookSpec): Book {
        return Book(name = spec.name).also {
            repository.save(it)
        }
    }

    fun findById(id: Long): Book {
        return queryFactory.singleQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::id).equal(id))
        }
    }

    fun findAll(spec: FindBookSpec): List<Book> {
        return queryFactory.listQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::name).like("%${spec.name}%"))
        }
    }

    data class CreateBookSpec(
        val name: String
    ) {
        fun toJson() = """{"name":"$name"}"""
    }

    data class FindBookSpec(
        val name: String
    ) {
        fun toJson() = """{"name":"$name"}"""
    }
}
