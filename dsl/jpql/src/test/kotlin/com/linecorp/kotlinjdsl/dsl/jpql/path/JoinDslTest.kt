package com.linecorp.kotlinjdsl.dsl.jpql.path

import com.linecorp.kotlinjdsl.dsl.jpql.AbstractJpqlDslTest
import com.linecorp.kotlinjdsl.querymodel.jpql.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.Predicates
import com.linecorp.kotlinjdsl.querymodel.jpql.path.JoinType
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Path
import org.junit.jupiter.api.Test

class JoinDslTest : AbstractJpqlDslTest() {
    @Test
    fun `entity join entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(entity(TestTable2::class), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(entity(TestTable2::class), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity joinType LEFT fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(entity(TestTable2::class), joinType = JoinType.LEFT, fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(
                    entity(TestTable2::class),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(
                    entity(TestTable2::class),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join entity on predicate joinType LEFT fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(
                    entity(TestTable2::class),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(path(TestTable1::table1), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(path(TestTable1::table1), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path joinType LEFT fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).join(path(TestTable1::table1), joinType = JoinType.LEFT, fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(
                    path(TestTable1::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(
                    path(TestTable1::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity join path on predicate joinType LEFT fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .join(
                    path(TestTable1::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).join(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path joinType LEFT`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).join(path(TestTable2::table1), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).join(path(TestTable2::table1), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path joinType LEFT fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).join(path(TestTable2::table1), joinType = JoinType.LEFT, fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .join(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .join(
                    path(TestTable2::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .join(
                    path(TestTable2::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path join path on predicate joinType LEFT fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .join(
                    path(TestTable2::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerJoin(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin entity fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerJoin(entity(TestTable2::class), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerJoin(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin entity on predicate fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerJoin(
                    entity(TestTable2::class),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerJoin(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin path fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerJoin(path(TestTable1::table1), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerJoin(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoin path on predicate fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerJoin(
                    path(TestTable1::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerJoin path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).innerJoin(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerJoin path fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).innerJoin(path(TestTable2::table1), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerJoin path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .innerJoin(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerJoin path on predicate fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .innerJoin(
                    path(TestTable2::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftJoin(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin entity fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftJoin(entity(TestTable2::class), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftJoin(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin entity on predicate fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftJoin(
                    entity(TestTable2::class),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftJoin(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin path fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftJoin(path(TestTable1::table1), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftJoin(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoin path on predicate fetch true`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftJoin(
                    path(TestTable1::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftJoin path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).leftJoin(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftJoin path fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).leftJoin(path(TestTable2::table1), fetch = true)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftJoin path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .leftJoin(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = false,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftJoin path on predicate fetch true`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .leftJoin(
                    path(TestTable2::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    fetch = true,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).joinFetch(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch entity joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).joinFetch(entity(TestTable2::class), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .joinFetch(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch entity on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .joinFetch(
                    entity(TestTable2::class),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).joinFetch(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch path joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).joinFetch(path(TestTable1::table1), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .joinFetch(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity joinFetch path on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .joinFetch(
                    path(TestTable1::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path joinFetch path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).joinFetch(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path joinFetch path joinType LEFT`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).joinFetch(path(TestTable2::table1), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path joinFetch path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .joinFetch(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path joinFetch path on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .joinFetch(
                    path(TestTable2::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoinFetch entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerJoinFetch(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoinFetch entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerJoinFetch(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoinFetch path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerJoinFetch(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerJoinFetch path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerJoinFetch(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerJoinFetch path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).innerJoinFetch(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerJoinFetch path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .innerJoinFetch(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoinFetch entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftJoinFetch(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoinFetch entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftJoinFetch(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoinFetch path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftJoinFetch(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftJoinFetch path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftJoinFetch(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftJoinFetch path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).leftJoinFetch(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftJoinFetch path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .leftJoinFetch(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).fetch(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch entity joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).fetch(entity(TestTable2::class), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .fetch(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch entity on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .fetch(
                    entity(TestTable2::class),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).fetch(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch path joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).fetch(path(TestTable1::table1), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .fetch(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity fetch path on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .fetch(
                    path(TestTable1::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path fetch path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).fetch(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path fetch path joinType LEFT`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).fetch(path(TestTable2::table1), joinType = JoinType.LEFT)
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path fetch path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .fetch(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path fetch path on predicate joinType LEFT`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .fetch(
                    path(TestTable2::table1),
                    on = path(TestTable1::int1).equal(path(TestTable2::int1)),
                    joinType = JoinType.LEFT,
                )
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerFetch entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerFetch(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerFetch entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerFetch(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerFetch path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).innerFetch(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity innerFetch path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .innerFetch(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerFetch path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).innerFetch(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path innerFetch path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .innerFetch(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.INNER,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftFetch entity`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftFetch(entity(TestTable2::class))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftFetch entity on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftFetch(entity(TestTable2::class), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.entity(TestTable2::class),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftFetch path`() {
        // when
        val path = testJpql {
            entity(TestTable1::class).leftFetch(path(TestTable1::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `entity leftFetch path on predicate`() {
        // when
        val path = testJpql {
            entity(TestTable1::class)
                .leftFetch(path(TestTable1::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.entity(TestTable1::class),
            right = Paths.path(TestTable1::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftFetch path`() {
        // when
        val path = testJpql {
            path(TestTable1::table1).leftFetch(path(TestTable2::table1))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = null,
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `path leftFetch path on predicate`() {
        // when
        val path = testJpql {
            path(TestTable1::table1)
                .leftFetch(path(TestTable2::table1), on = path(TestTable1::int1).equal(path(TestTable2::int1)))
        }

        val actual: Path<Any> = path // for type check

        // then
        val expected = Paths.join(
            left = Paths.path(TestTable1::table1),
            right = Paths.path(TestTable2::table1),
            on = Predicates.equal(
                left = Paths.path(TestTable1::int1),
                right = Paths.path(TestTable2::int1),
            ),
            joinType = JoinType.LEFT,
            fetch = true,
        )

        assertThat(actual).isEqualTo(expected)
    }

    private class TestTable1 {
        val int1: Int = 1

        val table1: OtherTable1 = OtherTable1()
    }

    private class TestTable2 {
        val int1: Int = 1

        val table1: OtherTable2 = OtherTable2()
    }

    private class OtherTable1
    private class OtherTable2
}
