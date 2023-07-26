package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import org.junit.jupiter.api.Test

class PathDslTest : AbstractJpqlDslTest() {
    @Test
    fun `path property`() {
        // when
        val path = testJpql {
            path(TestTable1::field1)
        }

        val actual: Path<TestField1> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(TestTable1::field1),
        )
    }

    @Test
    fun `path nullable property`() {
        // when
        val path = testJpql {
            path(TestTable1::nullableField1)
        }

        val actual: Path<TestField1> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(TestTable1::nullableField1),
        )
    }

    @Test
    fun `path property path property`() {
        // when
        val path = testJpql {
            path(TestTable1::field1).path(TestField1::int1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::field1), TestField1::int1),
        )
    }

    @Test
    fun `path property path nullable property`() {
        // when
        val path = testJpql {
            path(TestTable1::field1).path(TestField1::nullableInt1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::field1), TestField1::nullableInt1),
        )
    }

    @Test
    fun `path nullable property path property`() {
        // when
        val path = testJpql {
            path(TestTable1::nullableField1).path(TestField1::int1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::nullableField1), TestField1::int1),
        )
    }

    @Test
    fun `path nullable property path nullable property`() {
        // when
        val path = testJpql {
            path(TestTable1::nullableField1).path(TestField1::nullableInt1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::nullableField1), TestField1::nullableInt1),
        )
    }

    @Test
    fun `path property invoke property`() {
        // when
        val path = testJpql {
            path(TestTable1::field1)(TestField1::int1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::field1), TestField1::int1),
        )
    }

    @Test
    fun `path property invoke nullable property`() {
        // when
        val path = testJpql {
            path(TestTable1::field1)(TestField1::nullableInt1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::field1), TestField1::nullableInt1),
        )
    }

    @Test
    fun `path nullable property invoke property`() {
        // when
        val path = testJpql {
            path(TestTable1::nullableField1)(TestField1::int1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::nullableField1), TestField1::int1),
        )
    }

    @Test
    fun `path nullable property invoke nullable property`() {
        // when
        val path = testJpql {
            path(TestTable1::nullableField1)(TestField1::nullableInt1)
        }

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.path(Paths.path(TestTable1::nullableField1), TestField1::nullableInt1),
        )
    }

    private class TestTable1 {
        val field1: TestField1 = TestField1()
        val nullableField1: TestField1? = null
    }

    private class TestField1 {
        val int1: Int = 1
        val nullableInt1: Int? = null
    }
}
