package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class VersionDslTest : WithAssertions {
    private val entity = Entities.entity(Book::class)

    @Test
    fun `version() with a class`() {
        // when
        val expression = queryPart {
            version<Long>(Book::class)
        }.toExpression()

        val actual: Expression<Any> = expression // for type check

        // then
        val expected = Expressions.version<Long>(
            entity,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `version() with an entity path`() {
        // when
        val expression = queryPart {
            version<Long>(entity)
        }.toExpression()

        val actual: Expression<Any> = expression // for type check

        // then
        val expected = Expressions.version<Long>(
            entity,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
