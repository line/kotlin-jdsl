package com.linecorp.kotlinjdsl.spring.data

import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.EntityDsl
import com.linecorp.kotlinjdsl.test.entity.order.Order
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import javax.persistence.EntityManager

@DataJpaTest
@EntityScan(basePackages = ["com.linecorp.kotlinjdsl.test.entity"])
internal open class SpringDataQueryFactoryIntegrationTest : EntityDsl(), WithKotlinJdslAssertions {
    @Autowired
    private lateinit var entityManager: EntityManager

    private lateinit var queryFactory: SpringDataQueryFactory

    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 1000 }
    private val order3 = order { purchaserId = 1000 }
    private val order4 = order { purchaserId = 2000 }

    @BeforeEach
    fun setUp() {
        queryFactory = SpringDataQueryFactoryImpl(
            criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
            subqueryCreator = SubqueryCreatorImpl()
        )
        entityManager.persistAll(order1, order2, order3, order4)
        entityManager.flushAndClear()
    }

    @Test
    fun pageQuery() {
        // when
        val actual = queryFactory.pageQuery<Long>(PageRequest.of(1, 2, Sort.by("id"))) {
            select(col(Order::id))
            from(entity(Order::class))
            where(col(Order::purchaserId).equal(1000))
        }

        // then
        assertThat(actual.content).isEqualTo(listOf(order3.id))

        assertThat(actual.totalElements).isEqualTo(3)
        assertThat(actual.totalPages).isEqualTo(2)
        assertThat(actual.number).isEqualTo(1)
    }
}