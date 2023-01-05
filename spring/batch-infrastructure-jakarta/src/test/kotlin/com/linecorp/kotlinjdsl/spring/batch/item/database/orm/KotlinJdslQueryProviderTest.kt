package com.linecorp.kotlinjdsl.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.EntityManager
import jakarta.persistence.Query

@ExtendWith(MockKExtension::class)
internal class KotlinJdslQueryProviderTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var entityManager: EntityManager

    @Test
    fun createQuery() {
        // given
        val query: Query = mockk()

        val createQuery: (SpringDataQueryFactory) -> Query = {
            query
        }

        // when
        val actual = KotlinJdslQueryProvider(createQuery).apply {
            setEntityManager(entityManager)
        }.createQuery()

        // then
        assertThat(actual).isEqualTo(query)
    }

    @Test
    fun `createQuery - if there is no entityManager before call createQuery then throw exception`() {
        // given
        val createQuery: (SpringDataQueryFactory) -> Query = {
            mockk()
        }

        // when
        val exception = catchThrowable(IllegalStateException::class) {
            KotlinJdslQueryProvider(createQuery).createQuery()
        }

        // then
        assertThat(exception)
            .hasMessageContaining("There is no entityManager. Please set entityManager before create query")
    }
}
