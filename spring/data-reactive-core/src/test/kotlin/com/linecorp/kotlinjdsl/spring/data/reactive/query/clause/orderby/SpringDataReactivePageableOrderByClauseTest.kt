package com.linecorp.kotlinjdsl.spring.data.reactive.query.clause.orderby

import com.linecorp.kotlinjdsl.query.spec.Froms
import com.linecorp.kotlinjdsl.spring.reactive.query.clause.orderby.SpringDataReactivePageableOrderByClause
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.query.QueryUtils
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Order
import javax.persistence.criteria.Root

@ExtendWith(MockKExtension::class)
internal class SpringDataReactivePageableOrderByClauseTest : WithAssertions {
    @MockK
    private lateinit var froms: Froms

    @MockK
    private lateinit var query: CriteriaQuery<*>

    @MockK
    private lateinit var criteriaBuilder: CriteriaBuilder

    @Test
    fun apply() {
        // given
        mockkStatic(QueryUtils::class)

        val sort = Sort.by(Sort.Direction.ASC, "test")
        val pageable = PageRequest.of(1, 10, sort)

        val root: Root<*> = mockk()
        val order: Order = mockk()

        every { froms.root } returns root
        every { QueryUtils.toOrders(sort, root, criteriaBuilder) } returns listOf(order)
        every { query.orderBy(listOf(order)) } returns query

        // when
        SpringDataReactivePageableOrderByClause(pageable).apply(froms, query, criteriaBuilder)

        // then
        verify(exactly = 1) {
            froms.root
            QueryUtils.toOrders(sort, root, criteriaBuilder)
            query.orderBy(listOf(order))
        }

        clearStaticMockk(QueryUtils::class)
    }
}
