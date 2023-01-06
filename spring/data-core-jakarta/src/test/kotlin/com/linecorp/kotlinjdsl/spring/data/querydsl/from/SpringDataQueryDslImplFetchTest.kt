package com.linecorp.kotlinjdsl.spring.data.querydsl.from

import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.spec.FetchJoinSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test
import jakarta.persistence.criteria.JoinType

internal class SpringDataQueryDslImplFetchTest : WithKotlinJdslAssertions {
    @Test
    fun fetch() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
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

        val exception1 = catchThrowable(IllegalStateException::class) {
            actual.createSubquerySpec()
        }

        assertThat(exception1).hasMessageContaining("This query does not support fetch")

        val exception2 = catchThrowable(IllegalStateException::class) {
            actual.createPageableQuerySpec()
        }

        assertThat(exception2).hasMessageContaining("This query does not support fetch")

        val exception3 = catchThrowable(IllegalStateException::class) {
            actual.createPageableCountQuerySpec()
        }

        assertThat(exception3).hasMessageContaining("This query does not support fetch")
    }

    class Data1 {
        val data2: Data2 = Data2()
    }

    class Data2
}
