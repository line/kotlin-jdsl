package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.junit.jupiter.api.Test

class AndDslTest : AbstractJpqlDslTest() {
    private val int1: Int = 1
    private val int2: Int = 1

    @Test
    fun `and predicate predicate`() {
        // when
        val actual = testJpql {
            and(
                path(TestTable::int1).equal(int1),
                path(TestTable::int1).equal(int2),
            )
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Predicates.and(
                listOf(
                    Predicates.equal(
                        Paths.path(TestTable::int1),
                        Expressions.value(int1),
                    ),
                    Predicates.equal(
                        Paths.path(TestTable::int1),
                        Expressions.value(int2),
                    ),
                ),
            ),
        )
    }

    @Test
    fun `predicate and predicate`() {
        // when
        val actual = testJpql {
            path(TestTable::int1).equal(int1).and(path(TestTable::int1).equal(int2))
        }.toPredicate()

        // then
        assertThat(actual).isEqualTo(
            Predicates.and(
                listOf(
                    Predicates.equal(
                        Paths.path(TestTable::int1),
                        Expressions.value(int1),
                    ),
                    Predicates.equal(
                        Paths.path(TestTable::int1),
                        Expressions.value(int2),
                    ),
                ),
            ),
        )
    }

    private class TestTable {
        val int1: Int = 1
    }
}
