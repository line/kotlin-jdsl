package com.linecorp.kotlinjdsl.querymodel.jpql.select

import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.author.Author
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.publisher.Publisher
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.from.impl.JpqlJoinedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnion
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQueryUnionAll
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sorts
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SelectQueriesTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)
    private val entity2 = Entities.entity(Author::class)
    private val entity3 = Entities.entity(Publisher::class)

    private val join1 = Joins.innerJoin(Entities.entity(BookAuthor::class), Paths.path(Book::authors))
    private val join2 = Joins.innerJoin(Entities.entity(BookPublisher::class), Paths.path(Book::publisher))

    private val expression1 = Paths.path(Book::isbn)
    private val expression2 = Paths.path(Book::title)
    private val expression3 = Paths.path(Book::price)
    private val expression4 = Paths.path(Book::salePrice)

    private val predicate1 = Predicates.equal(Paths.path(Book::isbn), Expressions.value(Isbn("isbn1")))
    private val predicate2 = Predicates.equal(Paths.path(Book::title), Expressions.value("title1"))

    private val sort1 = Sorts.asc(expression1)
    private val sort2 = Sorts.desc(expression2)

    private class Class1

    @Test
    fun selectQuery() {
        // when
        val actual = SelectQueries.selectQuery(
            returnType = Class1::class,
            distinct = false,
            select = listOf(expression1, expression2),
            from = listOf(entity1, join1, join2, entity2, entity3),
            where = predicate1,
            groupBy = listOf(expression3, expression4),
            having = predicate2,
            orderBy = listOf(sort1, sort2),
        )

        // then
        val expected = JpqlSelectQuery(
            returnType = Class1::class,
            distinct = false,
            select = listOf(expression1, expression2),
            from = listOf(
                JpqlJoinedEntity(JpqlJoinedEntity(entity1, join1), join2),
                entity2,
                entity3,
            ),
            where = predicate1,
            groupBy = listOf(expression3, expression4),
            having = predicate2,
            orderBy = listOf(sort1, sort2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun selectUnionQuery() {
        // when
        val left = SelectQueries.selectQuery(
            returnType = Class1::class,
            distinct = false,
            select = listOf(expression1, expression2),
            from = listOf(entity1, join1, join2, entity2, entity3),
            where = predicate1,
            groupBy = listOf(expression3, expression4),
            having = predicate2,
            orderBy = listOf(sort1, sort2),
        )
        val right = SelectQueries.selectQuery(
            returnType = Class1::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
            where = predicate1,
            groupBy = listOf(expression3, expression4),
            having = predicate2,
            orderBy = listOf(sort1, sort2),
        )
        val actual = SelectQueries.selectUnionQuery(
            returnType = Class1::class,
            left = left,
            right = right,
            orderBy = listOf(sort1, sort2),
        )

        // then
        val expected = JpqlSelectQueryUnion(
            returnType = Class1::class,
            left = left,
            right = right,
            orderBy = listOf(sort1, sort2),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun selectUnionAllQuery() {
        // when
        val left = SelectQueries.selectQuery(
            returnType = Class1::class,
            distinct = false,
            select = listOf(expression1, expression2),
            from = listOf(entity1, join1, join2, entity2, entity3),
            where = predicate1,
            groupBy = listOf(expression3, expression4),
            having = predicate2,
            orderBy = listOf(sort1, sort2),
        )
        val right = SelectQueries.selectQuery(
            returnType = Class1::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
            where = predicate1,
            groupBy = listOf(expression3, expression4),
            having = predicate2,
            orderBy = listOf(sort1, sort2),
        )
        val actual = SelectQueries.selectUnionAllQuery(
            returnType = Class1::class,
            left = left,
            right = right,
            orderBy = listOf(sort1, sort2),
        )

        // then
        val expected = JpqlSelectQueryUnionAll(
            returnType = Class1::class,
            left = left,
            right = right,
            orderBy = listOf(sort1, sort2),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
