package com.linecorp.kotlinjdsl.spring.data.querydsl.hint

import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClauseImpl
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test
import jakarta.persistence.Query

internal class SpringDataQueryDslImplHintTest : WithKotlinJdslAssertions {
    @Test
    fun noHints() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
        }

        // then
        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            JpaQueryHintClauseImpl<Query>(emptyMap())
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.jpaHint).isEqualTo(
            JpaQueryHintClauseImpl<Query>(emptyMap())
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.jpaHint).isEqualTo(
            JpaQueryHintClauseImpl<Query>(emptyMap())
        )
    }

    @Test
    fun hintsVararg() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            hints("hint1" to true, "hint2" to "comment")
        }

        // then
        val hintClause = JpaQueryHintClauseImpl<Query>(
            mapOf(
                "hint1" to true,
                "hint2" to "comment"
            )
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            hintClause
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.jpaHint).isEqualTo(
            hintClause
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.jpaHint).isEqualTo(
            hintClause
        )
    }

    @Test
    fun hintsMap() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(entity(Data1::class))
            hints(mapOf("hint1" to true, "hint2" to "comment"))
        }

        // then
        val hintClause = JpaQueryHintClauseImpl<Query>(
            mapOf(
                "hint1" to true,
                "hint2" to "comment"
            )
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.jpaHint).isEqualTo(
            hintClause
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.jpaHint).isEqualTo(
            hintClause
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.jpaHint).isEqualTo(
            hintClause
        )
    }

    class Data1
}
