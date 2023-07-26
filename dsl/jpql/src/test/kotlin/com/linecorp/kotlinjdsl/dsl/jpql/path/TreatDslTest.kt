package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class TreatDslTest : AbstractJpqlDslTest() {
    @Test
    fun `path treat subclass`() {
        // when
        val path = testJpql {
            path(TestTable1::field1).treat(TestSubField1::class)
        }

        val actual: Path<TestSubField1> = path // for type check

        // then
        val expected = Paths.treat(Paths.path(TestTable1::field1), TestSubField1::class)

        assertThat(actual).isEqualTo(expected)
    }

    private open class TestTable1 {
        val field1: TestField1 = TestSubField1()
    }

    private open class TestField1
    private open class TestSubField1 : TestField1()
}
