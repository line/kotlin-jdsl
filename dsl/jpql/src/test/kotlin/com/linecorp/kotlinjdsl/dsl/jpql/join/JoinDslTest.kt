package com.linecorp.kotlinjdsl.dsl.jpql.join

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Joins
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class JoinDslTest : AbstractJpqlDslTest() {
    @Test
    fun `join entity association joinType inner fetch false`() {
        // when
        val join = testJpql {
            join(TestTable1::field1)
        }

        val actual: Join = join.toJoin()

        // then
        val expected = Joins.innerJoin(
            entity = Entities.entity(TestField1::class),
            association = Paths.path(TestTable1::field1),
            predicate = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val field1: TestField1 = TestField1()
    }

    private class TestField1
}
