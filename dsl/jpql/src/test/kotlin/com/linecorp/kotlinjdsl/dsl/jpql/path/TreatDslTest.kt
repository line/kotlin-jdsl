package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import org.junit.jupiter.api.Test

class TreatDslTest : AbstractJpqlDslTest() {
    @Test
    fun `path treat subclass`() {
        // when
        val path = testJpql {
            path(TestTable::table1).treat(SubTable::class)
        }

        val actual: Path<SubTable> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.treat(Paths.path<SuperTable>(TestTable::table1), SubTable::class),
        )
    }

    @Test
    fun `nullable path treat subclass`() {
        // when
        val path = testJpql {
            path(TestTable::nullableTable1).treat(SubTable::class)
        }

        val actual: Path<SubTable?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            Paths.treat(Paths.path<SuperTable?>(TestTable::nullableTable1), SubTable::class),
        )
    }

    private open class TestTable {
        val table1: SuperTable = SubTable()

        val nullableTable1: SuperTable? = null
    }

    private open class SuperTable {}
    private open class SubTable : SuperTable() {}
}
