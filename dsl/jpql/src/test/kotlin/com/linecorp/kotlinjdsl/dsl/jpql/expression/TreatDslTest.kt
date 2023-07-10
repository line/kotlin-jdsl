package com.linecorp.kotlinjdsl.dsl.jpql.expression

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Treat
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
            Treat<SuperTable, SubTable>(
                Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::table1.name,
                ),
                SubTable::class,
            ),
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
            Treat<SuperTable, SubTable>(
                Field(
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableTable1.name,
                ),
                SubTable::class,
            ),
        )
    }

    private open class TestTable {
        val table1: SuperTable = SubTable()

        val nullableTable1: SuperTable? = null
    }

    private open class SuperTable {}
    private open class SubTable : SuperTable() {}
}
