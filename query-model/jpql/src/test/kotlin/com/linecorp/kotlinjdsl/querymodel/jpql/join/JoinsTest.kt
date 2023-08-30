package com.linecorp.kotlinjdsl.querymodel.jpql.join

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.querymodel.jpql.join.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class JoinsTest : WithAssertions {
    private val entity1 = Entities.entity(BookAuthor::class)

    private val path1 = Paths.path(Book::authors)

    private val predicate1 = Predicates.equal(
        Paths.path(BookAuthor::authorId),
        Paths.path(Author::authorId),
    )

    @Test
    fun innerJoin() {
        // when
        val actual = Joins.innerJoin(
            entity = entity1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlInnerJoin(
            entity = entity1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerJoin() with an association`() {
        // when
        val actual = Joins.innerJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlInnerAssociationJoin(
            entity = entity1,
            association = path1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun leftJoin() {
        // when
        val actual = Joins.leftJoin(
            entity = entity1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlLeftJoin(
            entity = entity1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftJoin() with an association`() {
        // when
        val actual = Joins.leftJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlLeftAssociationJoin(
            entity = entity1,
            association = path1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun innerFetchJoin() {
        // when
        val actual = Joins.innerFetchJoin(
            entity = entity1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlInnerFetchJoin(
            entity = entity1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `innerFetchJoin() with an association`() {
        // when
        val actual = Joins.innerFetchJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlInnerAssociationFetchJoin(
            entity = entity1,
            association = path1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun leftFetchJoin() {
        // when
        val actual = Joins.leftFetchJoin(
            entity = entity1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlLeftFetchJoin(
            entity = entity1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `leftFetchJoin() with an association`() {
        // when
        val actual = Joins.leftFetchJoin(
            entity = entity1,
            association = path1,
            predicate = predicate1,
        )

        // then
        val expected = JpqlLeftAssociationFetchJoin(
            entity = entity1,
            association = path1,
            on = predicate1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
