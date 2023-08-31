package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import java.math.BigDecimal
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SelectDslTest : WithAssertions {
    private val expression1 = Paths.path(Book::price)
    private val expression2 = Paths.path(Book::salePrice)

    private val entity1 = Entities.entity(Book::class)

    private class Dto
    private class View

    @Test
    fun `select() with an expression`() {
        // when
        val select = queryPart {
            select(
                expression1,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<BigDecimal> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = BigDecimal::class,
            distinct = false,
            select = listOf(expression1),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `select() with a generic type and expressions`() {
        // when
        val select = queryPart {
            select<View>(
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<View> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = View::class,
            distinct = false,
            select = listOf(expression1, expression2),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `select() with a class and expressions`() {
        // when
        val select = queryPart {
            select(
                View::class,
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<View> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = View::class,
            distinct = false,
            select = listOf(expression1, expression2),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `selectDistinct() with an expression`() {
        // when
        val select = queryPart {
            selectDistinct(
                expression1,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<BigDecimal> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = BigDecimal::class,
            distinct = true,
            select = listOf(expression1),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `selectDistinct() with a generic type and expressions`() {
        // when
        val select = queryPart {
            selectDistinct<View>(
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<View> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = View::class,
            distinct = true,
            select = listOf(expression1, expression2),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `selectDistinct() with a class and expressions`() {
        // when
        val select = queryPart {
            selectDistinct(
                View::class,
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<View> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = View::class,
            distinct = true,
            select = listOf(expression1, expression2),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `selectNew() with a generic type and expressions`() {
        // when
        val select = queryPart {
            selectNew<Dto>(
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<Dto> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = Dto::class,
            distinct = false,
            select = listOf(
                Expressions.new(
                    type = Dto::class,
                    args = listOf(expression1, expression2),
                ),
            ),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `selectNew() with a class and expressions`() {
        // when
        val select = queryPart {
            selectNew(
                Dto::class,
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<Dto> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = Dto::class,
            distinct = false,
            select = listOf(
                Expressions.new(
                    type = Dto::class,
                    args = listOf(expression1, expression2),
                ),
            ),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `selectDistinctNew() with a generic type and expressions`() {
        // when
        val select = queryPart {
            selectDistinctNew<Dto>(
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<Dto> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = Dto::class,
            distinct = true,
            select = listOf(
                Expressions.new(
                    type = Dto::class,
                    args = listOf(expression1, expression2),
                ),
            ),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `selectDistinctNew() with a class and expressions`() {
        // when
        val select = queryPart {
            selectDistinctNew(
                Dto::class,
                expression1,
                expression2,
            ).from(
                entity1,
            )
        }.toQuery()

        val actual: SelectQuery<Dto> = select // for type check

        // then
        val expected = SelectQueries.selectQuery(
            returnType = Dto::class,
            distinct = true,
            select = listOf(
                Expressions.new(
                    type = Dto::class,
                    args = listOf(expression1, expression2),
                ),
            ),
            from = listOf(entity1),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
