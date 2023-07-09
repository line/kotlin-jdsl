package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
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
        assertThat(actual).isEqualTo(AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!))
    }

    @Test
    fun `entity class alias`() {
        // when
        val path = testJpql {
            entity(TestTable::class, alias1)
        }

        val actual: Path<TestTable> = path // for type check

        // then
        assertThat(actual).isEqualTo(AliasedPath(Entity(TestTable::class), alias1))
    }

    private class TestTable
}
