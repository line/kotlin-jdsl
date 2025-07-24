package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class IdDslTest : WithAssertions {
    private val entity = Entities.entity(Book::class)

    @Test
    fun `id() with a class`() {
        // when
        val expression = queryPart {
            id<Isbn>(Book::class)
        }.toExpression()

        val actual: Expression<Isbn> = expression // for type check

        // then
        val expected = Expressions.id<Isbn>(
            entity,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `id() with an entity path`() {
        // when
        val expression = queryPart {
            id<Isbn>(entity)
        }.toExpression()

        val actual: Expression<Isbn> = expression // for type check

        // then
        val expected = Expressions.id<Isbn>(
            entity,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
