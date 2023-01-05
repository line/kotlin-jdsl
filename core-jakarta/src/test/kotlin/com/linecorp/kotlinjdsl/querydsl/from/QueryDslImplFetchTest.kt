package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.spec.FetchJoinSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test
import jakarta.persistence.criteria.JoinType

internal class QueryDslImplFetchTest : WithKotlinJdslAssertions {
    @Test
    fun fetch() {
        // when
        val actual = QueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            fetch(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            fetch(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            fetch(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            fetch(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
        }

        // then
        val fetchSpec = FetchJoinSpec(
            left = EntitySpec(Data1::class.java),
            right = EntitySpec(Data2::class.java),
            path = "data2",
            JoinType.LEFT
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(fetchSpec, fetchSpec, fetchSpec, fetchSpec))
        )

        val exception = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception).hasMessageContaining("This query does not support fetch")
    }

    class Data1 {
        val data2: Data2 = Data2()
    }

    class Data2
}
