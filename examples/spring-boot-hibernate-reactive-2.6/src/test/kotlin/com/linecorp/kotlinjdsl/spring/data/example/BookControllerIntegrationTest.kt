package com.linecorp.kotlinjdsl.spring.data.example

import com.linecorp.kotlinjdsl.spring.data.example.entity.Book
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest
@AutoConfigureMockMvc
internal class BookControllerIntegrationTest : WithAssertions {
    @Autowired
    private lateinit var client: WebTestClient

    private val context = "/api/v1/books"

    @Test
    fun createBook() {
        createBook(BookService.CreateBookSpec("name"))
    }

    @Test
    fun findById() {
        val spec = BookService.CreateBookSpec("name1")
        val id = createBook(spec)
        client.get().uri("$context/$id")
            .exchange()
            .expectStatus().isOk
            .expectBody(Book::class.java)
            .value {
                assertThat(Book(id = id, name = spec.name)).isEqualTo(it)
            }

    }

    @Test
    fun findByName() {
        val spec1 = BookService.CreateBookSpec("name2")
        val spec2 = BookService.CreateBookSpec("name2")
        val id1 = createBook(spec1)
        val id2 = createBook(spec2)
        client.get().uri(context + "?name=${spec1.name}")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(Book::class.java)
            .contains(Book(id = id1, name = spec1.name), Book(id = id2, name = spec2.name))
    }

    private fun createBook(spec: BookService.CreateBookSpec) = client.post().uri(context)
        .bodyValue(spec)
        .exchange()
        .expectStatus().isOk
        .returnResult(Long::class.java)
        .responseBody
        .blockFirst()!!
}
