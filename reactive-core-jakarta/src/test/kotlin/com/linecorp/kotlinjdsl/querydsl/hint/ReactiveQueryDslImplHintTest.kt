package com.linecorp.kotlinjdsl.querydsl.hint

import com.linecorp.kotlinjdsl.query.clause.hint.JpaReactiveQueryHintClauseImpl
import com.linecorp.kotlinjdsl.querydsl.ReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class ReactiveQueryDslImplHintTest : WithKotlinJdslAssertions {
    @Test
    fun noHints() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            JpaReactiveQueryHintClauseImpl<Any>(emptyMap())
        )
    }

    @Test
    fun hintsVararg() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            hints("hint1" to true, "hint2" to "comment")
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            JpaReactiveQueryHintClauseImpl<Any>(
                mapOf(
                    "hint1" to true,
                    "hint2" to "comment"
                )
            )
        )
    }

    @Test
    fun hintsMap() {
        // when
        val actual = ReactiveQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            hints(mapOf("hint1" to true, "hint2" to "comment"))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            JpaReactiveQueryHintClauseImpl<Any>(
                mapOf(
                    "hint1" to true,
                    "hint2" to "comment"
                )
            )
        )
    }

    class Data1
}
