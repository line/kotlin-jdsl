package com.linecorp.kotlinjdsl.querydsl.hint

import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClauseImpl
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test
import jakarta.persistence.Query

internal class QueryDslImplHintTest : WithKotlinJdslAssertions {
    @Test
    fun noHints() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            JpaQueryHintClauseImpl<Query>(emptyMap())
        )
    }

    @Test
    fun hintsVararg() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            hints("hint1" to true, "hint2" to "comment")
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            JpaQueryHintClauseImpl<Query>(
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
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            hints(mapOf("hint1" to true, "hint2" to "comment"))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            JpaQueryHintClauseImpl<Query>(
                mapOf(
                    "hint1" to true,
                    "hint2" to "comment"
                )
            )
        )
    }

    class Data1
}
