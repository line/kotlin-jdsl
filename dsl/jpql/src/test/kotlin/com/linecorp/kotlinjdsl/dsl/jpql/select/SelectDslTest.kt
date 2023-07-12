package com.linecorp.kotlinjdsl.dsl.jpql.select

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.dsl.jpql.select.impl.SelectQueryFromStepDsl
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.AliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.impl.Field
import org.junit.jupiter.api.Test

class SelectDslTest : AbstractJpqlDslTest() {
    @Test
    fun `select expression`() {
        // when
        val select = testJpql {
            select(path(TestTable::int1))
        }

        val actual: SelectQueryFromStep<Int> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Int::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select nullable expression`() {
        // when
        val select = testJpql {
            select(path(TestTable::nullableInt1))
        }

        val actual: SelectQueryFromStep<Int?> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int?>(
            Int::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select expression distinct true`() {
        // when
        val select = testJpql {
            select(path(TestTable::int1), distinct = true)
        }

        val actual: SelectQueryFromStep<Int> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Int::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select nullable expression distinct true`() {
        // when
        val select = testJpql {
            select(path(TestTable::nullableInt1), distinct = true)
        }

        val actual: SelectQueryFromStep<Int?> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int?>(
            Int::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select expression expression`() {
        // when
        val select = testJpql {
            select(path(TestTable::int1), path(TestTable::int2))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select expression nullable expression`() {
        // when
        val select = testJpql {
            select(path(TestTable::int1), path(TestTable::nullableInt1))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select nullable expression nullable expression`() {
        // when
        val select = testJpql {
            select(path(TestTable::nullableInt1), path(TestTable::nullableInt2))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select collection expression expression`() {
        // when
        val select = testJpql {
            select(listOf(path(TestTable::int1), path(TestTable::int2)))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select collection expression nullable expression`() {
        // when
        val select = testJpql {
            select(listOf(path(TestTable::int1), path(TestTable::nullableInt1)))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select collection nullable expression nullable expression`() {
        // when
        val select = testJpql {
            select(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select expression expression distinct true`() {
        // when
        val select = testJpql {
            select(path(TestTable::int1), path(TestTable::int2), distinct = true)
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select(path(TestTable::int1), path(TestTable::nullableInt1), distinct = true)
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select nullable expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select(path(TestTable::nullableInt1), path(TestTable::nullableInt2), distinct = true)
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select collection expression expression distinct true`() {
        // when
        val select = testJpql {
            select(listOf(path(TestTable::int1), path(TestTable::int2)), distinct = true)
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select collection expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select(listOf(path(TestTable::int1), path(TestTable::nullableInt1)), distinct = true)
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select collection nullable expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)), distinct = true)
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type expression expression`() {
        // when
        val select = testJpql {
            select<Row>(path(TestTable::int1), path(TestTable::int2))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type expression nullable expression`() {
        // when
        val select = testJpql {
            select<Row>(path(TestTable::int1), path(TestTable::nullableInt1))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type nullable expression nullable expression`() {
        // when
        val select = testJpql {
            select<Row>(path(TestTable::nullableInt1), path(TestTable::nullableInt2))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type collection expression expression`() {
        // when
        val select = testJpql {
            select<Row>(listOf(path(TestTable::int1), path(TestTable::int2)))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type collection expression nullable expression`() {
        // when
        val select = testJpql {
            select<Row>(listOf(path(TestTable::int1), path(TestTable::nullableInt1)))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type collection nullable expression nullable expression`() {
        // when
        val select = testJpql {
            select<Row>(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            false,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type expression expression distinct true`() {
        // when
        val select = testJpql {
            select<Row>(path(TestTable::int1), path(TestTable::int2), distinct = true)
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select<Row>(path(TestTable::int1), path(TestTable::nullableInt1), distinct = true)
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type nullable expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select<Row>(path(TestTable::nullableInt1), path(TestTable::nullableInt2), distinct = true)
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type collection expression expression distinct true`() {
        // when
        val select = testJpql {
            select<Row>(listOf(path(TestTable::int1), path(TestTable::int2)), distinct = true)
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type collection expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select<Row>(listOf(path(TestTable::int1), path(TestTable::nullableInt1)), distinct = true)
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `select type collection nullable expression nullable expression distinct true`() {
        // when
        val select = testJpql {
            select<Row>(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)), distinct = true)
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct expression`() {
        // when
        val select = testJpql {
            selectDistinct(path(TestTable::int1))
        }

        val actual: SelectQueryFromStep<Int> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Int::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct(path(TestTable::nullableInt1))
        }

        val actual: SelectQueryFromStep<Int?> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int?>(
            Int::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct expression expression`() {
        // when
        val select = testJpql {
            selectDistinct(path(TestTable::int1), path(TestTable::int2))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct(path(TestTable::int1), path(TestTable::nullableInt1))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct nullable expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct(path(TestTable::nullableInt1), path(TestTable::nullableInt2))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct collection expression expression`() {
        // when
        val select = testJpql {
            selectDistinct(listOf(path(TestTable::int1), path(TestTable::int2)))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct collection expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct(listOf(path(TestTable::int1), path(TestTable::nullableInt1)))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct collection nullable expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)))
        }

        val actual: SelectQueryFromStep<Any> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Any::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct type expression expression`() {
        // when
        val select = testJpql {
            selectDistinct<Row>(path(TestTable::int1), path(TestTable::int2))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct type expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct<Row>(path(TestTable::int1), path(TestTable::nullableInt1))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct type nullable expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct<Row>(path(TestTable::nullableInt1), path(TestTable::nullableInt2))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct type collection expression expression`() {
        // when
        val select = testJpql {
            selectDistinct<Row>(listOf(path(TestTable::int1), path(TestTable::int2)))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct type collection expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct<Row>(listOf(path(TestTable::int1), path(TestTable::nullableInt1)))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::int1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    @Test
    fun `selectDistinct type collection nullable expression nullable expression`() {
        // when
        val select = testJpql {
            selectDistinct<Row>(listOf(path(TestTable::nullableInt1), path(TestTable::nullableInt2)))
        }

        val actual: SelectQueryFromStep<Row> = select // for type check

        // then
        val expected = SelectQueryFromStepDsl<Int>(
            Row::class,
            listOf<Path<Any?>>(
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt1.name,
                ),
                Field(
                    Int::class,
                    AliasedPath(Entity(TestTable::class), TestTable::class.simpleName!!),
                    TestTable::nullableInt2.name,
                ),
            ),
            true,
        )

        assertThat(actual)
            .isNotInstanceOf(JpqlQueryable::class.java)
            .isEqualTo(expected)
    }

    private class TestTable {
        val int1: Int = 1
        val int2: Int = 2

        val nullableInt1: Int? = null
        val nullableInt2: Int? = null
    }

    private class Row
}
