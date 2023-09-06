package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PathDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val path1 = Paths.path(Book::publisher)

    @Test
    fun `path() with a property`() {
        // when
        val path = queryPart {
            path(Book::publisher)
        }

        val actual: Path<BookPublisher> = path // for type check

        // then
        val excepted = Paths.path(
            Book::publisher,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `path() with a entity and a property`() {
        // when
        val path = queryPart {
            entity1.path(Book::publisher)
        }

        val actual: Path<BookPublisher> = path // for type check

        // then
        val excepted = Paths.path(
            entity1,
            Book::publisher,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `path() with a path and a property`() {
        // when
        val path = queryPart {
            path1.path(BookPublisher::publisherId)
        }

        val actual: Path<Long> = path // for type check

        // then
        val excepted = Paths.path(
            path1,
            BookPublisher::publisherId,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `invoke() with a entity and a property`() {
        // when
        val path = queryPart {
            entity1(Book::publisher)
        }

        val actual: Path<BookPublisher> = path // for type check

        // then
        val excepted = Paths.path(
            entity1,
            Book::publisher,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun `invoke() with a path and a property`() {
        // when
        val path = queryPart {
            path1(BookPublisher::publisherId)
        }

        val actual: Path<Long> = path // for type check

        // then
        val excepted = Paths.path(
            path1,
            BookPublisher::publisherId,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
