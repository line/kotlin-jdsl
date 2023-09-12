package com.linecorp.kotlinjdsl.dsl.jpql.entity

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class EntityDslTest : WithAssertions {
    private val alias1 = "alias1"

    @Test
    fun `entity() with a class`() {
        // when
        val entity = queryPart {
            entity(Book::class)
        }

        val actual: Entity<Book> = entity // for type check

        // then
        val expected = Entities.entity(
            Book::class,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity() with a class and an alias`() {
        // when
        val entity = queryPart {
            entity(Book::class, alias1)
        }

        val actual: Entity<Book> = entity // for type check

        // then
        val expected = Entities.entity(
            Book::class,
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
