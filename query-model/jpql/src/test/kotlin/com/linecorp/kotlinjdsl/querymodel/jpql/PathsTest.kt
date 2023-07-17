package com.linecorp.kotlinjdsl.querymodel.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.ExpressionAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlField
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import com.linecorp.kotlinjdsl.querymodel.jpql.path.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import com.linecorp.kotlinjdsl.querymodel.jpql.path.PathAndExpression
import com.linecorp.kotlinjdsl.querymodel.jpql.path.impl.*
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.impl.JpqlEqual
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class PathsTest : WithAssertions {
    private val string1: String = "string1"
    private val nullableString1: String? = null

    private val int1: Int = 1
    private val int2: Int = 1
    private val nullableInt1: Int? = null
    private val nullableInt2: Int? = null

    private val long1: Long = 1

    private val alias1: String = "alias1"
    private val alias2: String = "alias2"

    private val template1: String = "template1"

    private val paramName1: String = "paramName1"

    @Test
    fun `entity class`() {
        // when
        val path = Paths.entity(TestTable1::class)

        val actual: Path<TestTable1> = path // for type check

        // then
        assertThat(actual).isEqualTo(JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!))
    }

    @Test
    fun `entity class alias`() {
        // when
        val path = Paths.entity(TestTable1::class, alias1)

        val actual: Path<TestTable1> = path // for type check

        // then
        assertThat(actual).isEqualTo(JpqlAliasedPath(JpqlEntity(TestTable1::class), alias1))
    }

    @Test
    fun `path property`() {
        // when
        val path = Paths.path(TestTable1::int1)

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlField<Int>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
        )
    }

    @Test
    fun `path nullable property`() {
        // when
        val path = Paths.path(TestTable1::nullableInt1)

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlField<Int?>(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
        )
    }

    @Test
    fun `path property property`() {
        // when
        val path = Paths.path(Paths.path<SuperTable1>(TestTable1::table1), SuperTable1::int1)

        val actual: Path<Int> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlField<Int>(
                Int::class,
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::int1.name,
            ),
        )
    }

    @Test
    fun `path path property nullable property`() {
        // when
        val path = Paths.path(Paths.path<SuperTable1>(TestTable1::table1), SuperTable1::nullableInt1)

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlField<Int?>(
                Int::class,
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::nullableInt1.name,
            ),
        )
    }

    @Test
    fun `path path nullable property property`() {
        // when
        val path = Paths.path(Paths.path<SuperTable1?>(TestTable1::nullableTable1), SuperTable1::int1)

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlField<Int?>(
                Int::class,
                JpqlField<SuperTable1?>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableTable1.name,
                ),
                SuperTable1::int1.name,
            ),
        )
    }

    @Test
    fun `path path nullable property nullable property`() {
        // when
        val path = Paths.path(Paths.path<SuperTable1?>(TestTable1::nullableTable1), SuperTable1::nullableInt1)

        val actual: Path<Int?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlField<Int?>(
                Int::class,
                JpqlField<SuperTable1?>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableTable1.name,
                ),
                SuperTable1::nullableInt1.name,
            ),
        )
    }

    @Test
    fun `treat path subclass`() {
        // when
        val path = Paths.treat(
            Paths.path<SuperTable1>(TestTable1::table1),
            SubTable1::class,
        )

        val actual: Path<SubTable1> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlTreat<SuperTable1, SubTable1>(
                JpqlField(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SubTable1::class,
            ),
        )
    }

    @Test
    fun `treat nullable path subclass`() {
        // when
        val path = Paths.treat(
            Paths.path<SuperTable1?>(TestTable1::nullableTable1),
            SubTable1::class,
        )

        val actual: Path<SubTable1?> = path // for type check

        // then
        assertThat(actual).isEqualTo(
            JpqlTreat<SuperTable1?, SubTable1>(
                JpqlField(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableTable1.name,
                ),
                SubTable1::class,
            ),
        )
    }

    @Test
    fun `join entity entity joinType INNER fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `join entity entity joinType LEFT fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `join entity entity joinType INNER fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `join entity entity joinType LEFT fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate joinType INNER fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate joinType LEFT fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate joinType INNER fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate joinType LEFT fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.entity(TestTable2::class),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path joinType INNER fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path joinType LEFT fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path joinType INNER fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path joinType LEFT fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate joinType INNER fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate joinType LEFT fetch false`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate joinType INNER fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate joinType LEFT fetch true`() {
        // when
        val path = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.path(TestTable1::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path joinType INNER fetch false`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path joinType LEFT fetch false`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path joinType INNER fetch true`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path joinType LEFT fetch true`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable2::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate joinType INNER fetch false`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    SuperTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate joinType LEFT fetch false`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    SuperTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate joinType INNER fetch true`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    SuperTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate joinType LEFT fetch true`() {
        // when
        val path = Paths.join(
            Paths.path(TestTable1::table1),
            Paths.path(TestTable2::table1),
            Predicates.equal(
                Paths.path(TestTable1::int1),
                Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        val actual: Path<Any> = path // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    SuperTable1::table1.name,
                ),
                SuperTable1::class.simpleName!!,
            ),
            right = JpqlAliasedPath(
                JpqlField<SuperTable2>(
                    SuperTable2::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                SuperTable2::class.simpleName!!,
            ),
            on = JpqlEqual<Int>(
                left = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                right = JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable2::class), TestTable2::class.simpleName!!),
                    TestTable1::int1.name,
                ),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `alias path string`() {
        // when
        val path = Paths.alias(
            Paths.path(TestTable1::int1),
            alias1,
        )

        val actual: Path<Int> = path // for type check

        // then
        val expected = JpqlAliasedPath<Int>(
            JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable path alias string`() {
        // when
        val path = Paths.alias(
            Paths.path(TestTable1::nullableInt1),
            alias1,
        )

        val actual: Path<Int?> = path // for type check

        // then
        val expected = JpqlAliasedPath<Int?>(
            JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            alias1,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path alias string alias string`() {
        // when
        val path = Paths.alias(
            Paths.alias(
                Paths.path(TestTable1::int1),
                alias1,
            ),
            alias2,
        )

        val actual: Path<Int> = path // for type check

        // then
        val expected = JpqlAliasedPath<Int>(
            JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::int1.name,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `nullable path alias string alias string`() {
        // when
        val path = Paths.alias(
            Paths.alias(
                Paths.path(TestTable1::nullableInt1),
                alias1,
            ),
            alias2,
        )

        val actual: Path<Int?> = path // for type check

        // then
        val expected = JpqlAliasedPath<Int?>(
            JpqlField(
                Int::class,
                JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                TestTable1::nullableInt1.name,
            ),
            alias2,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path alias string`() {
        // when
        val path1 = Paths.alias(
            Paths.join(
                Paths.entity(TestTable1::class),
                Paths.path(TestTable1::table1),
                null,
                joinType = JoinType.INNER,
                fetch = false,
            ),
            alias1,
        )

        val actual1: Path<Any> = path1 // for type check

        val path2 = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.alias(
                Paths.path(TestTable1::table1),
                alias1,
            ),
            null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                alias1,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join nullable path alias string`() {
        // when
        val path1 = Paths.alias(
            Paths.join(
                Paths.entity(TestTable1::class),
                Paths.path(TestTable1::nullableTable1),
                null,
                joinType = JoinType.INNER,
                fetch = false,
            ),
            alias1,
        )

        val actual1: Path<Any> = path1 // for type check

        val path2 = Paths.join(
            Paths.entity(TestTable1::class),
            Paths.alias(
                Paths.path(TestTable1::nullableTable1),
                alias1,
            ),
            null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableTable1.name,
                ),
                alias1,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `alias alias entity path string string`() {
        // when
        val path1 = Paths.alias(
            Paths.alias(
                Paths.join(
                    Paths.entity(TestTable1::class),
                    Paths.path(TestTable1::table1),
                    null,
                    joinType = JoinType.INNER,
                    fetch = false,
                ),
                alias1,
            ),
            alias2,
        )

        val actual1: Path<Any> = path1 // for type check

        val path2 = Paths.alias(
            Paths.join(
                Paths.entity(TestTable1::class),
                Paths.alias(
                    Paths.path(TestTable1::table1),
                    alias1,
                ),
                null,
                joinType = JoinType.INNER,
                fetch = false,
            ),
            alias2,
        )

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::table1.name,
                ),
                alias2,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `entity join nullable path alias string alias string`() {
        // when
        val path1 = Paths.alias(
            Paths.alias(
                Paths.join(
                    Paths.entity(TestTable1::class),
                    Paths.path(TestTable1::nullableTable1),
                    null,
                    joinType = JoinType.INNER,
                    fetch = false,
                ),
                alias1,
            ),
            alias2,
        )

        val actual1: Path<Any> = path1 // for type check

        val path2 = Paths.alias(
            Paths.join(
                Paths.entity(TestTable1::class),
                Paths.alias(
                    Paths.path(TestTable1::nullableTable1),
                    alias1,
                ),
                null,
                joinType = JoinType.INNER,
                fetch = false,
            ),
            alias2,
        )

        val actual2: Path<Any> = path2 // for type check

        // then
        val expected = JpqlJoin(
            left = JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
            right = JpqlAliasedPath(
                JpqlField<SuperTable1>(
                    SuperTable1::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableTable1.name,
                ),
                alias2,
            ),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual1).isEqualTo(expected)
        assertThat(actual2).isEqualTo(expected)
    }

    @Test
    fun `pair path expression`() {
        // when
        val expression = Paths.pair(
            Paths.path(TestTable1::int1),
            Expressions.value(int1),
        )

        val actual: PathAndExpression<Int> = expression

        // then
        assertThat(actual).isEqualTo(
            JpqlPathAndExpression(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::int1.name,
                ),
                JpqlValue(int1),
            ),
        )
    }

    @Test
    fun `pair nullable path expression`() {
        // when
        val expression = Paths.pair(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(int1),
        )

        val actual: PathAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            JpqlPathAndExpression(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(int1),
            ),
        )
    }

    @Test
    fun `pair nullable path nullable expression`() {
        // when
        val expression = Paths.pair(
            Paths.path(TestTable1::nullableInt1),
            Expressions.value(nullableInt1),
        )

        val actual: ExpressionAndExpression<Int?> = expression

        // then
        assertThat(actual).isEqualTo(
            JpqlPathAndExpression(
                JpqlField(
                    Int::class,
                    JpqlAliasedPath(JpqlEntity(TestTable1::class), TestTable1::class.simpleName!!),
                    TestTable1::nullableInt1.name,
                ),
                JpqlValue(nullableInt1),
            ),
        )
    }

    private class TestTable1 {
        val int1: Int = 1
        val nullableInt1: Int? = null

        val table1: SuperTable1 = SuperTable1()
        val nullableTable1: SuperTable1? = null
    }

    private class TestTable2 {
        val int1: Int = 1

        val table1: SuperTable2 = SuperTable2()
    }

    private open class SuperTable1 {
        val int1: Int = 1
        val nullableInt1: Int? = null

        val table1: SuperTable1 = SuperTable1()
    }

    private open class SuperTable2

    private open class SubTable1 : SuperTable1()
}
