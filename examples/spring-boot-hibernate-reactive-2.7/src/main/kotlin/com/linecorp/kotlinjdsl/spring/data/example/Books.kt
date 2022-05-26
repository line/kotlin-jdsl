package com.linecorp.kotlinjdsl.spring.data.example

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.example.entity.Book
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.listQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import org.hibernate.reactive.mutiny.Mutiny
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.concurrent.CompletionStage

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService,
) {
    @PostMapping
    fun createBook(@RequestBody spec: BookService.CreateBookSpec): Mono<ResponseEntity<Long>> =
        Mono.fromCompletionStage { bookService.create(spec) }.map { ResponseEntity.ok().body(it.id) }

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
    private val mutinySessionFactory: Mutiny.SessionFactory,
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
) {
    fun create(spec: CreateBookSpec): CompletionStage<Book> {
        val book = Book(name = spec.name)
        return mutinySessionFactory.withSession { session -> session.persist(book).flatMap { session.flush() } }
            .map { book }
            .subscribeAsCompletionStage()
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
