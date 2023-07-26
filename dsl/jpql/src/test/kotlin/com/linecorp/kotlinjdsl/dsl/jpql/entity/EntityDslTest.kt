package com.linecorp.kotlinjdsl.dsl.jpql.entity

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import org.junit.jupiter.api.Test

class EntityDslTest : AbstractJpqlDslTest() {
    private val alias1 = "alias1"

    @Test
    fun `entity class`() {
        // when
        val entity = testJpql {
            entity(TestTable::class)
        }

        val actual: Entity<TestTable> = entity // for type check

        // then
        val expected = Entities.entity(TestTable::class)

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity class alias`() {
        // when
        val entity = testJpql {
            entity(TestTable::class, alias1)
        }

        val actual: Entity<TestTable> = entity // for type check

        // then
        val expected = Entities.entity(TestTable::class, alias1)

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable
}
