package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import org.junit.jupiter.api.Test

class EntityDslTest : AbstractJpqlDslTest() {
    private val alias1 = "alias1"

    @Test
    fun `entity class`() {
        // when
        val path = testJpql {
            entity(TestTable::class)
        }

        val actual: Path<TestTable> = path // for type check

        // then
        val expected = Paths.entity(TestTable::class)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity class alias`() {
        // when
        val path = testJpql {
            entity(TestTable::class, alias1)
        }

        val actual: Path<TestTable> = path // for type check

        // then
        val expected = Paths.entity(TestTable::class, alias1)

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable
}
