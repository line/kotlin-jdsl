package com.linecorp.kotlinjdsl.spring.data.querydsl.from

import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.spec.CrossJoinSpec
import com.linecorp.kotlinjdsl.query.spec.SimpleAssociatedJoinSpec
import com.linecorp.kotlinjdsl.query.spec.SimpleJoinSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.predicate.AndSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.spring.data.querydsl.SpringDataQueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.mockk
import org.junit.jupiter.api.Test
import jakarta.persistence.criteria.JoinType

internal class SpringDataQueryDslImplJoinTest : WithKotlinJdslAssertions {
    @Test
    fun on() {
        val predicateSpec: PredicateSpec = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = on(predicateSpec)
        }

        assertThat(actual).isEqualTo(predicateSpec)
    }

    @Test
    fun onLambda() {
        val predicateSpec: PredicateSpec = mockk()

        val actual: PredicateSpec

        SpringDataQueryDslImpl(Data1::class.java).apply {
            actual = on { predicateSpec }
        }

        assertThat(actual).isEqualTo(predicateSpec)
    }

    @Test
    fun join() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            join(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            join(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            join(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            join(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
        }

        // then
        val joinSpec = SimpleJoinSpec(
            left = EntitySpec(Data1::class.java),
            right = EntitySpec(Data2::class.java),
            path = "data2",
            JoinType.LEFT
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )
    }

    @Test
    fun associate() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            associate(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
        }

        // then
        val joinSpec = SimpleAssociatedJoinSpec(
            left = EntitySpec(Data1::class.java),
            right = EntitySpec(Data2::class.java),
            path = "data2"
        )

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )
    }

    @Test
    fun updateAssociate() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            associate(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
        }

        // then
        val joinSpec = SimpleAssociatedJoinSpec(
            left = EntitySpec(Data1::class.java),
            right = EntitySpec(Data2::class.java),
            path = "data2"
        )

        val criteriaQuerySpec = actual.createCriteriaUpdateQuerySpec()

        assertThat(criteriaQuerySpec.associate).isEqualTo(
            SimpleAssociatedJoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec, joinSpec, joinSpec))
        )
    }

    @Test
    fun updateAssociateOnlyAllowsSimpleAssociatedJoinSpec() {
        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            associate(entity(Data1::class), entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, entity(Data2::class), on(Data1::data2), JoinType.LEFT)
            associate(entity(Data1::class), Data2::class, on(Data1::data2), JoinType.LEFT)
            associate(Data1::class, Data2::class, on(Data1::data2), JoinType.LEFT)
            join(Data1::class, Data2::class, on(Data1::data2))
        }

        val result = catchThrowable(IllegalStateException::class) { actual.createCriteriaUpdateQuerySpec() }

        assertThat(result).hasMessage("This query only support associate")
    }

    @Test
    fun crossJoin() {
        // given
        val predicateSpec1: PredicateSpec = mockk()
        val predicateSpec2: PredicateSpec = mockk()

        // when
        val actual = SpringDataQueryDslImpl(Data1::class.java).apply {
            select(Data1::class.java)
            from(Data1::class.java)
            join(Data2::class, on(predicateSpec1))
            join(entity(Data2::class), on(predicateSpec1))
            where(predicateSpec2)
        }

        // then
        val joinSpec = CrossJoinSpec(EntitySpec(Data2::class.java))

        val criteriaQuerySpec = actual.createCriteriaQuerySpec()

        assertThat(criteriaQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec))
        )

        assertThat(criteriaQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec1, predicateSpec2)))
        )

        val subquerySpec = actual.createSubquerySpec()

        assertThat(subquerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec))
        )

        assertThat(subquerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec1, predicateSpec2)))
        )

        val pageableQuerySpec = actual.createPageableQuerySpec()

        assertThat(pageableQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec))
        )

        assertThat(pageableQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec1, predicateSpec2)))
        )

        val pageableCountQuerySpec = actual.createPageableCountQuerySpec()

        assertThat(pageableCountQuerySpec.join).isEqualTo(
            JoinClause(listOf(joinSpec, joinSpec))
        )

        assertThat(pageableCountQuerySpec.where).isEqualTo(
            WhereClause(AndSpec(listOf(predicateSpec1, predicateSpec1, predicateSpec2)))
        )
    }

    class Data1 {
        val data2: Data2 = Data2()
    }

    class Data2
}
