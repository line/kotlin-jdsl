package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class FromDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)
    private val entity2 = Entities.entity(Author::class)

    private val expression1 = Paths.path(Book::isbn)

    private val join1 = Joins.innerJoin(
        entity = Entities.entity(BookAuthor::class),
        association = Paths.path(Book::authors),
    )

    private val join2 = Joins.innerJoin(
        entity = Entities.entity(BookPublisher::class),
        association = Paths.path(Book::publisher),
    )

    @Test
    fun `from() with an entity`() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<Isbn> = select // for type check

        // then
        val expected = SelectQueries.select(
            returnType = Isbn::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `from() with froms`() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity1,
                null,
                entity2,
                null,
                join1,
                null,
                join2,
            )
        }.toQuery()

        val actual: SelectQuery<Isbn> = select // for type check

        // then
        val expected = SelectQueries.select(
            returnType = Isbn::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(
                entity1,
                entity2,
                join1,
                join2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
