package com.linecorp.kotlinjdsl.dsl.jpql.update

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQueries
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class UpdateSetDslTest : WithAssertions {
    private val entity1 = Entities.entity(Book::class)

    private val path1 = Paths.path(Book::price)
    private val path2 = Paths.path(Book::salePrice)

    private val bigDecimal1 = BigDecimal.valueOf(100)
    private val bigDecimal2 = BigDecimal.valueOf(200)

    private val bigDecimalExpression1 = Expressions.value(BigDecimal.valueOf(100))
    private val bigDecimalExpression2 = Expressions.value(BigDecimal.valueOf(200))

    @Test
    fun `update() and set() with bigDecimals`() {
        // when
        val update = queryPart {
            update(
                entity1,
            ).set(
                path1,
                bigDecimal1,
            ).set(
                path2,
                bigDecimal2,
            )
        }.toQuery()

        val actual: UpdateQuery<Book> = update // for type check

        // then
        val expected = UpdateQueries.updateQuery(
            entity = entity1,
            set = mapOf(
                path1 to Expressions.value(bigDecimal1),
                path2 to Expressions.value(bigDecimal2),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `update() and set() with bigDecimal expressions`() {
        // when
        val update = queryPart {
            update(
                entity1,
            ).set(
                path1,
                bigDecimalExpression1,
            ).set(
                path2,
                bigDecimalExpression2,
            )
        }.toQuery()

        val actual: UpdateQuery<Book> = update // for type check

        // then
        val expected = UpdateQueries.updateQuery(
            entity = entity1,
            set = mapOf(
                path1 to bigDecimalExpression1,
                path2 to bigDecimalExpression2,
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }
}
