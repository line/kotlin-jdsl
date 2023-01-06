package com.linecorp.kotlinjdsl.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import com.linecorp.kotlinjdsl.test.entity.EntityDsl
import com.linecorp.kotlinjdsl.test.entity.order.Order
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import javax.persistence.EntityManagerFactory

@DataJpaTest
@EntityScan("com.linecorp.kotlinjdsl.test.entity")
internal open class KotlinJdslQueryProviderIntegrationTest : EntityDsl, WithKotlinJdslAssertions {
    @Autowired
    private lateinit var entityManagerFactory: EntityManagerFactory

    private val order1 = order { purchaserId = 1000 }
    private val order2 = order { purchaserId = 2000 }
    private val order3 = order { purchaserId = 1000 }
    private val order4 = order { purchaserId = 1000 }

    @BeforeEach
    fun setUp() {
        val entityManager = entityManagerFactory.createEntityManager()

        entityManager.transaction.run {
            begin()
            sequenceOf(order1, order2, order3, order4).forEach {
                entityManager.persist(it)
                entityManager.flush()
            }
            commit()
        }

        entityManager.close()
    }

    @Test
    fun test() {
        // when
        val queryProvider = KotlinJdslQueryProvider.typedQuery<Long> {
            select(col(Order::id))
            from(Order::class)
            where(col(Order::purchaserId).equal(1000))
            orderBy(col(Order::id).asc())
        }

        val reader = JpaCursorItemReaderBuilder<Long>()
            .entityManagerFactory(entityManagerFactory)
            .name("testReader")
            .queryProvider(queryProvider)
            .build()

        reader.open(ExecutionContext())

        // then
        assertThat(reader.read()!!).isEqualTo(order1.id)
        assertThat(reader.read()!!).isEqualTo(order3.id)
        assertThat(reader.read()!!).isEqualTo(order4.id)
    }
}
