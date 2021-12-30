package com.linecorp.kotlinjdsl.spring.data.example

import com.linecorp.kotlinjdsl.spring.data.example.entity.Book
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class BookControllerIntegrationTest : WithAssertions {
    @Autowired
    private lateinit var mockMvc: MockMvc

    private val context = "/api/v1/books"

    @Test
    fun createBook() {
        createBook(BookService.CreateBookSpec("name")) {
            status { isOk() }
        }
    }

    @Test
    fun findById() {
        val spec = BookService.CreateBookSpec("name1")
        val id = createBook(spec)
        mockMvc.get("$context/$id")
            .andExpect {
                status { isOk() }
                content {
                    json(Book(id = id, name = spec.name).toJson())
                }
            }
    }

    @Test
    fun findByName() {
        val spec1 = BookService.CreateBookSpec("name2")
        val spec2 = BookService.CreateBookSpec("name2")
        val id1 = createBook(spec1)
        val id2 = createBook(spec2)
        mockMvc.get(context) {
            param("name", spec1.name)
        }
            .andExpect {
                status { isOk() }
                content {
                    json(
                        buildString {
                            append("[")
                            append(Book(id = id1, name = spec1.name).toJson())
                            append(",")
                            append(Book(id = id2, name = spec2.name).toJson())
                            append("]")
                        }
                    )
                }
            }
    }

    private fun createBook(spec: BookService.CreateBookSpec, dsl: MockMvcResultMatchersDsl.() -> Unit = {}) = mockMvc.post(context) {
        contentType = MediaType.APPLICATION_JSON
        content = spec.toJson()
    }.andExpect(dsl)
        .andReturn()
        .response
        .contentAsString.toLong()
}
