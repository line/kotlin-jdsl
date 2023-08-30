package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.select.Selects
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SubqueryDslTest : WithAssertions {
    private val selectQuery1 = Selects.select(
        returnType = String::class,
        distinct = false,
        select = listOf(Paths.path(Book::title)),
        from = listOf(Entities.entity(Book::class)),
    )

    @Test
    fun asSubquery() {
        // when
        val subquery = queryPart {
            selectQuery1.asSubquery()
        }

        val actual: Subquery<String> = subquery

        // then
        val expected = Expressions.subquery(
            selectQuery1,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
