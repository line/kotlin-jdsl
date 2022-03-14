package com.linecorp.kotlinjdsl

import com.linecorp.kotlinjdsl.query.ReactiveQuery
import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.from.SimpleAssociatedJoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.GroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.HavingClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaReactiveQueryHintClauseImpl
import com.linecorp.kotlinjdsl.query.clause.hint.emptySqlHintClause
import com.linecorp.kotlinjdsl.query.clause.limit.ReactiveLimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.OrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.clause.set.SetClause
import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.creator.ReactiveCriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.EqualValueSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.ReactiveQueryDslImpl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ReactiveQueryFactoryImplTest : WithKotlinJdslAssertions {
    @InjectMockKs
    private lateinit var sut: ReactiveQueryFactoryImpl

    @MockK
    private lateinit var criteriaQueryCreator: ReactiveCriteriaQueryCreator

    @MockK
    private lateinit var subqueryCreator: SubqueryCreator

    @Test
    fun selectQuery() {
        // given
        val query: ReactiveQuery<Data1> = mockk()

        every { criteriaQueryCreator.createQuery(any<ReactiveQueryDslImpl.CriteriaQuerySpecImpl<Data1, ReactiveQuery<Data1>>>()) } returns query

        // when
        val actual = sut.selectQuery(Data1::class.java) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        val actualSelectQuery = sut.selectQuery(Data1::class.java) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // then
        assertThat(actual).isEqualTo(query)
        assertThat(actualSelectQuery).isEqualTo(query)

        verify(exactly = 2) {
            criteriaQueryCreator.createQuery(
                ReactiveQueryDslImpl.CriteriaQuerySpecImpl(
                    select = SingleSelectClause(
                        Data1::class.java,
                        distinct = false,
                        EntitySpec(Data1::class.java)
                    ),
                    from = FromClause(EntitySpec(Data1::class.java)),
                    join = JoinClause(emptyList()),
                    where = WhereClause(PredicateSpec.empty),
                    groupBy = GroupByClause(emptyList()),
                    having = HavingClause(PredicateSpec.empty),
                    orderBy = OrderByClause(emptyList()),
                    limit = ReactiveLimitClause.empty(),
                    jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
                    sqlHint = emptySqlHintClause(),
                )
            )
        }

        confirmVerified(criteriaQueryCreator)
    }

    @Test
    fun updateQuery() {
        // given
        val query: ReactiveQuery<Data1> = mockk()

        every { criteriaQueryCreator.createQuery(any<ReactiveQueryDslImpl.CriteriaUpdateQuerySpecImpl<Data1, ReactiveQuery<Data1>>>()) } returns query

        // when
        val actual = sut.updateQuery(Data1::class) {
            where(col(Data1::id).equal(1))
            set(col(Data1::id), 2)
        }

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            val columnSpec = ColumnSpec<Int>(EntitySpec(Data1::class.java), Data1::id.name)
            criteriaQueryCreator.createQuery(
                ReactiveQueryDslImpl.CriteriaUpdateQuerySpecImpl(
                    from = FromClause(EntitySpec(Data1::class.java)),
                    associate = SimpleAssociatedJoinClause(emptyList()),
                    where = WhereClause(EqualValueSpec(columnSpec, 1)),
                    jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
                    sqlHint = emptySqlHintClause(),
                    set = SetClause(mapOf(columnSpec to 2)),
                    targetEntity = Data1::class.java
                )
            )
        }

        confirmVerified(criteriaQueryCreator)
    }

    @Test
    fun deleteQuery() {
        // given
        val query: ReactiveQuery<Data1> = mockk()

        every { criteriaQueryCreator.createQuery(any<ReactiveQueryDslImpl.CriteriaDeleteQuerySpecImpl<Data1, ReactiveQuery<Data1>>>()) } returns query

        // when
        val actual = sut.deleteQuery(Data1::class) {
            where(col(Data1::id).equal(1))
        }

        // then
        assertThat(actual).isEqualTo(query)

        verify(exactly = 1) {
            val columnSpec = ColumnSpec<Int>(EntitySpec(Data1::class.java), Data1::id.name)
            criteriaQueryCreator.createQuery(
                ReactiveQueryDslImpl.CriteriaDeleteQuerySpecImpl(
                    from = FromClause(EntitySpec(Data1::class.java)),
                    associate = SimpleAssociatedJoinClause(emptyList()),
                    where = WhereClause(EqualValueSpec(columnSpec, 1)),
                    jpaHint = JpaReactiveQueryHintClauseImpl(emptyMap()),
                    sqlHint = emptySqlHintClause(),
                    targetEntity = Data1::class.java
                )
            )
        }

        confirmVerified(criteriaQueryCreator)
    }

    @Test
    fun subquery() {
        // when
        val actual = sut.subquery(Data1::class.java) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // then
        val subquerySpec = ReactiveQueryDslImpl.SubquerySpecImpl(
            select = SingleSelectClause(
                Data1::class.java,
                distinct = false,
                EntitySpec(Data1::class.java)
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
        )

        assertThat(actual).isEqualTo(SubqueryExpressionSpec(subquerySpec, subqueryCreator))
    }

    data class Data1(
        val id: Int = 1
    )
}
