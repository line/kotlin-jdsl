package com.linecorp.kotlinjdsl.spring.data

import com.linecorp.kotlinjdsl.query.clause.from.FromClause
import com.linecorp.kotlinjdsl.query.clause.from.JoinClause
import com.linecorp.kotlinjdsl.query.clause.groupby.GroupByClause
import com.linecorp.kotlinjdsl.query.clause.having.HavingClause
import com.linecorp.kotlinjdsl.query.clause.hint.HibernateSqlQueryHintClause
import com.linecorp.kotlinjdsl.query.clause.hint.JpaQueryHintClauseImpl
import com.linecorp.kotlinjdsl.query.clause.limit.LimitClause
import com.linecorp.kotlinjdsl.query.clause.orderby.OrderByClause
import com.linecorp.kotlinjdsl.query.clause.select.CountSingleSelectClause
import com.linecorp.kotlinjdsl.query.clause.select.SingleSelectClause
import com.linecorp.kotlinjdsl.query.clause.where.WhereClause
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.CountSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.query.spec.expression.SubqueryExpressionSpec
import com.linecorp.kotlinjdsl.query.spec.predicate.PredicateSpec
import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.query.clause.limit.SpringDataPageableLimitClause
import com.linecorp.kotlinjdsl.spring.data.query.clause.orderby.SpringDataPageableOrderByClause
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
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import javax.persistence.TypedQuery

@ExtendWith(MockKExtension::class)
internal class SpringDataQueryFactoryImplTest : WithKotlinJdslAssertions {
    @InjectMockKs
    private lateinit var sut: SpringDataQueryFactoryImpl

    @MockK
    private lateinit var criteriaQueryCreator: CriteriaQueryCreator

    @MockK
    private lateinit var subqueryCreator: SubqueryCreator

    @Test
    fun typedQuery() {
        // given
        val typedQuery: TypedQuery<Data1> = mockk()

        every { criteriaQueryCreator.createQuery<Data1>(any()) } returns typedQuery

        // when
        val actual = sut.typedQuery(Data1::class.java) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // then
        assertThat(actual).isEqualTo(typedQuery)

        verify(exactly = 1) {
            criteriaQueryCreator.createQuery(
                QueryDslImpl.CriteriaQuerySpecImpl(
                    select = SingleSelectClause(
                        Data1::class.java,
                        distinct = false,
                        EntitySpec(Data1::class.java),
                    ),
                    from = FromClause(EntitySpec(Data1::class.java)),
                    join = JoinClause(emptyList()),
                    where = WhereClause(PredicateSpec.empty),
                    groupBy = GroupByClause(emptyList()),
                    having = HavingClause(PredicateSpec.empty),
                    orderBy = OrderByClause(emptyList()),
                    limit = LimitClause.empty,
                    jpaHint = JpaQueryHintClauseImpl(emptyMap()),
                    sqlHint = HibernateSqlQueryHintClause(emptyList()),
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
        val subquerySpec = QueryDslImpl.SubquerySpecImpl(
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

    @Test
    fun pageQuery() {
        // given
        val pageable = PageRequest.of(0, 5)

        val expectedPageableSpec = QueryDslImpl.CriteriaQuerySpecImpl(
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
            orderBy = SpringDataPageableOrderByClause(pageable),
            limit = SpringDataPageableLimitClause(pageable),
            jpaHint = JpaQueryHintClauseImpl(emptyMap()),
            sqlHint = HibernateSqlQueryHintClause(emptyList()),
        )

        val expectedPageableCountSpec = QueryDslImpl.CriteriaQuerySpecImpl(
            select = CountSingleSelectClause(
                distinct = false,
                EntitySpec(Data1::class.java)
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
            orderBy = OrderByClause(emptyList()),
            limit = LimitClause.empty,
            jpaHint = JpaQueryHintClauseImpl(emptyMap()),
            sqlHint = HibernateSqlQueryHintClause(emptyList()),
        )

        val pageableQuery: TypedQuery<Data1> = mockk {
            every { resultList } returns listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5))
        }

        val pageableCountQuery: TypedQuery<Long> = mockk {
            every { resultList } returns listOf(50L)
        }

        every { criteriaQueryCreator.createQuery(expectedPageableSpec) } returns pageableQuery
        every { criteriaQueryCreator.createQuery(expectedPageableCountSpec) } returns pageableCountQuery

        // when
        val actual = sut.pageQuery(Data1::class.java, pageable) {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }

        // then
        assertThat(actual).isEqualTo(PageImpl(listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5)), pageable, 50))

        verify(exactly = 1) {
            criteriaQueryCreator.createQuery(expectedPageableSpec)
            criteriaQueryCreator.createQuery(expectedPageableCountSpec)

            pageableQuery.resultList
            pageableCountQuery.resultList
        }

        confirmVerified(pageableQuery, pageableCountQuery, criteriaQueryCreator)
    }

    @Test
    fun `pageQuery with countProjection`() {
        // given
        val pageable = PageRequest.of(0, 5)

        val expectedPageableSpec = QueryDslImpl.CriteriaQuerySpecImpl(
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
            orderBy = SpringDataPageableOrderByClause(pageable),
            limit = SpringDataPageableLimitClause(pageable),
            jpaHint = JpaQueryHintClauseImpl(emptyMap()),
            sqlHint = HibernateSqlQueryHintClause(emptyList()),
        )

        val expectedPageableCountSpec = QueryDslImpl.CriteriaQuerySpecImpl(
            select = SingleSelectClause(
                returnType = Long::class.java,
                distinct = false,
                expression = CountSpec(
                    distinct = false,
                    column = ColumnSpec<Long>(EntitySpec(Data1::class.java), "id")
                )
            ),
            from = FromClause(EntitySpec(Data1::class.java)),
            join = JoinClause(emptyList()),
            where = WhereClause(PredicateSpec.empty),
            groupBy = GroupByClause(emptyList()),
            having = HavingClause(PredicateSpec.empty),
            orderBy = OrderByClause(emptyList()),
            limit = LimitClause.empty,
            jpaHint = JpaQueryHintClauseImpl(emptyMap()),
            sqlHint = HibernateSqlQueryHintClause(emptyList()),
        )

        val pageableQuery: TypedQuery<Data1> = mockk {
            every { resultList } returns listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5))
        }

        val pageableCountQuery: TypedQuery<Long> = mockk {
            every { resultList } returns listOf(50L)
        }

        every { criteriaQueryCreator.createQuery(expectedPageableSpec) } returns pageableQuery
        every { criteriaQueryCreator.createQuery(expectedPageableCountSpec) } returns pageableCountQuery

        // when
        val actual = sut.pageQuery(Data1::class.java, pageable, dsl = {
            select(entity(Data1::class))
            from(entity(Data1::class))
        }, countProjection = {
            select(count(column(Data1::id)))
        })

        // then
        assertThat(actual).isEqualTo(PageImpl(listOf(Data1(1), Data1(2), Data1(3), Data1(4), Data1(5)), pageable, 50))

        verify(exactly = 1) {
            criteriaQueryCreator.createQuery(expectedPageableSpec)
            criteriaQueryCreator.createQuery(expectedPageableCountSpec)

            pageableQuery.resultList
            pageableCountQuery.resultList
        }

        confirmVerified(pageableQuery, pageableCountQuery, criteriaQueryCreator)
    }

    data class Data1(
        val id: Int = 1
    )
}