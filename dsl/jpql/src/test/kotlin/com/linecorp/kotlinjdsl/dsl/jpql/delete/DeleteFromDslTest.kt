package com.linecorp.kotlinjdsl.dsl.jpql.delete

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.JpqlDeleteQuery
import org.junit.jupiter.api.Test

class DeleteFromDslTest : AbstractJpqlDslTest() {
    private val alias1 = "alias1"

    @Test
    fun `update entity`() {
        // when
        val delete = testJpql {
            deleteFrom(entity(TestTable::class))
        }.toQuery()

        val actual: DeleteQuery<TestTable> = delete // for type check

        // then
        val expected = JpqlDeleteQuery(
            from = AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `update entity as string`() {
        // when
        val delete = testJpql {
            deleteFrom(entity(TestTable::class).`as`(alias1))
        }.toQuery()

        val actual: DeleteQuery<TestTable> = delete // for type check

        // then
        val expected = JpqlDeleteQuery(
            from = AliasedPath(Entity(TestTable::class), alias1),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable
}
