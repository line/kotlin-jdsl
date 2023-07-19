package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.delete.DeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.delete.impl.JpqlDeleteQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Subquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlField
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlSubquery
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlAliasedPath
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.JpqlEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlAnd
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.Sort
import com.linecorp.kotlinjdsl.querymodel.jpql.sort.impl.JpqlSort
import com.linecorp.kotlinjdsl.querymodel.jpql.update.UpdateQuery
import com.linecorp.kotlinjdsl.querymodel.jpql.update.impl.JpqlUpdateQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class QueriesTest : WithAssertions {
    private val int1: Int = 1
    private val int2: Int = 2

    @Test
    fun `select paths distinct false from paths`() {
        // when
        val select = Queries.select<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            distinct = false,
            from = listOf(
                Paths.entity(TestTable1::class),
                Paths.entity(TestTable2::class),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        ).toQuery()

        val subquery = Expressions.subquery(select)

        val actual1: SelectQuery<TestTable1> = select // for type check
        val actual2: Subquery<TestTable1> = subquery // for type check

        // then
        val expected1 = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            distinct = false,
            from = listOf(
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        val expected2 = JpqlSubquery(
            selectQuery = expected1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `select paths distinct true from paths`() {
        // when
        val select = Queries.select<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            distinct = true,
            from = listOf(
                Paths.entity(TestTable1::class),
                Paths.entity(TestTable2::class),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        ).toQuery()

        val subquery = Expressions.subquery(select)

        val actual1: SelectQuery<TestTable1> = select // for type check
        val actual2: Subquery<TestTable1> = subquery // for type check

        // then
        val expected1 = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            distinct = true,
            from = listOf(
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = null,
            groupBy = null,
            having = null,
            orderBy = null,
        )

        val expected2 = JpqlSubquery(
            selectQuery = expected1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `select paths distinct false from paths where predicate`() {
        // when
        val select = Queries.select<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            distinct = false,
            from = listOf(
                Paths.entity(TestTable1::class),
                Paths.entity(TestTable2::class),
            ),
            where = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
            groupBy = null,
            having = null,
            orderBy = null,
        ).toQuery()

        val subquery = Expressions.subquery(select)

        val actual1: SelectQuery<TestTable1> = select // for type check
        val actual2: Subquery<TestTable1> = subquery // for type check

        // then
        val expected1 = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            distinct = false,
            from = listOf(
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
            groupBy = null,
            having = null,
            orderBy = null,
        )

        val expected2 = JpqlSubquery(
            selectQuery = expected1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `select paths distinct false from paths where predicate groupBy paths`() {
        // when
        val select = Queries.select<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            distinct = false,
            from = listOf(
                Paths.entity(TestTable1::class),
                Paths.entity(TestTable2::class),
            ),
            where = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
            groupBy = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            having = null,
            orderBy = null,
        ).toQuery()

        val subquery = Expressions.subquery(select)

        val actual1: SelectQuery<TestTable1> = select // for type check
        val actual2: Subquery<TestTable1> = subquery // for type check

        // then
        val expected1 = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            distinct = false,
            from = listOf(
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
            groupBy = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            having = null,
            orderBy = null,
        )

        val expected2 = JpqlSubquery(
            selectQuery = expected1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `select paths distinct false from paths where predicate groupBy paths having predicate`() {
        // when
        val select = Queries.select<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            distinct = false,
            from = listOf(
                Paths.entity(TestTable1::class),
                Paths.entity(TestTable2::class),
            ),
            where = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
            groupBy = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            having = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
            orderBy = null,
        ).toQuery()

        val subquery = Expressions.subquery(select)

        val actual1: SelectQuery<TestTable1> = select // for type check
        val actual2: Subquery<TestTable1> = subquery // for type check

        // then
        val expected1 = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            distinct = false,
            from = listOf(
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
            groupBy = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            having = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
            orderBy = null,
        )

        val expected2 = JpqlSubquery(
            selectQuery = expected1,
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `select paths distinct false from paths where predicate groupBy paths having predicate orderBy paths`() {
        // when
        val select = Queries.select<TestTable1>(
            returnType = TestTable1::class,
            select = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            distinct = false,
            from = listOf(
                Paths.entity(TestTable1::class),
                Paths.entity(TestTable2::class),
            ),
            where = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
            groupBy = listOf(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            having = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
            orderBy = listOf(
                Sorts.asc(Paths.path(TestTable1::int1)),
                Sorts.asc(Paths.path(TestTable2::int1)),
            ),
        ).toQuery()

        val subquery = Expressions.subquery(select)

        val actual1: SelectQuery<TestTable1> = select // for type check
        val actual2: Subquery<TestTable1> = subquery // for type check

        // then
        val expected1 = JpqlSelectQuery<TestTable1>(
            returnType = TestTable1::class,
            select = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            distinct = false,
            from = listOf(
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            ),
            where = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
            groupBy = listOf<Expression<Int>>(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::int1.name,
                ),
            ),
            having = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
            orderBy = listOf<Sort>(
                JpqlSort(
                    JpqlField<Int>(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                        TestTable1::int1.name,
                    ),
                    Sort.Order.ASC,
                ),
                JpqlSort(
                    JpqlField<Int>(
                        Int::class,
                        JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                        TestTable2::int1.name,
                    ),
                    Sort.Order.ASC,
                ),
            ),
        )

        val expected2 = JpqlSubquery(
            selectQuery = expected1.copy(orderBy = null),
        )

        assertThat(actual1).isEqualTo(expected1)
        assertThat(actual2).isEqualTo(expected2)
    }

    @Test
    fun `update set assignments`() {
        // when
        val update = Queries.update(
            entity = Paths.entity(TestTable1::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable1::int1),
                    Expressions.value(int1),
                ),
                Paths.pair(
                    Paths.path(TestTable1::int2),
                    Expressions.value(int2),
                ),
            ),
            where = null,
        ).toQuery()

        val actual: UpdateQuery<TestTable1> = update // for type check

        // then
        val expected = JpqlUpdateQuery(
            entity = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            set = mapOf(
                JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ) to JpqlValue(int1),
                JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ) to JpqlValue(int2),
            ),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `update set assignments where predicate`() {
        // when
        val update = Queries.update(
            entity = Paths.entity(TestTable1::class),
            set = listOf(
                Paths.pair(
                    Paths.path(TestTable1::int1),
                    Expressions.value(int1),
                ),
                Paths.pair(
                    Paths.path(TestTable1::int2),
                    Expressions.value(int2),
                ),
            ),
            where = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
        ).toQuery()

        val actual: UpdateQuery<TestTable1> = update // for type check

        // then
        val expected = JpqlUpdateQuery(
            entity = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            set = mapOf(
                JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ) to JpqlValue(int1),
                JpqlField<Int>(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int2.name,
                ) to JpqlValue(int2),
            ),
            where = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun delete() {
        // when
        val delete = Queries.delete(
            from = Paths.entity(TestTable1::class),
            where = null,
        ).toQuery()

        val actual: DeleteQuery<TestTable1> = delete // for type check

        // then
        val expected = JpqlDeleteQuery(
            from = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            where = null,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `delete where predicate`() {
        // when
        val delete = Queries.delete(
            from = Paths.entity(TestTable1::class),
            where = Predicates.and(
                listOf(
                    Predicates.equal(Paths.path(TestTable1::int1), Expressions.value(int1)),
                    Predicates.equal(Paths.path(TestTable2::int1), Expressions.value(int1)),
                ),
            ),
        ).toQuery()

        val actual: DeleteQuery<TestTable1> = delete // for type check

        // then
        val expected = JpqlDeleteQuery(
            from = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            where = JpqlAnd(
                listOf(
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                            TestTable1::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                    JpqlEqual(
                        value = JpqlField<Int>(
                            Int::class,
                            JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                            TestTable2::int1.name,
                        ),
                        compareValue = JpqlValue(int1),
                    ),
                ),
            ),
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1
        val int2: Int = 1
    }

    private class TestTable2 {
        val int1: Int = 1
    }
}
