package com.linecorp.kotlinjdsl.spring.data.example

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.example.entity.Book
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateStageReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.listQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import kotlinx.coroutines.future.await
import org.hibernate.reactive.stage.Stage.SessionFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService,
) {
    @PostMapping
    suspend fun createBook(@RequestBody spec: BookService.CreateBookSpec): ResponseEntity<Long> {
        val book = bookService.create(spec)

        return ResponseEntity.ok(book.id)
    }

    @GetMapping("/{bookId}")
    suspend fun findById(@PathVariable bookId: Long): ResponseEntity<Book> {
        val book = bookService.findById(bookId)

        return ResponseEntity.ok(book)
    }

    @GetMapping
    suspend fun findAll(@RequestParam("name") name: String): ResponseEntity<List<Book>> {
        val books = bookService.findAll(BookService.FindBookSpec(name = name))

        return ResponseEntity.ok(books)
    }
}

@Service
class BookService(
    private val sessionFactory: SessionFactory,
    private val queryFactory: SpringDataHibernateStageReactiveQueryFactory,
) {
    suspend fun create(spec: CreateBookSpec): Book {
        return Book(name = spec.name).also {
            sessionFactory.withSession { session -> session.persist(it).thenCompose { session.flush() } }.await()
        }
    }

    suspend fun findById(id: Long): Book {
        return queryFactory.singleQuery {
            select(entity(Book::class))
            from(entity(Book::class))
            where(col(Book::id).equal(id))
        }
    }

    suspend fun findAll(spec: FindBookSpec): List<Book> {
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
