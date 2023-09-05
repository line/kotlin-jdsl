package com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.batch.JpqlEntityManagerUtils
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import jakarta.persistence.EntityManager
import jakarta.persistence.TypedQuery
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class KotlinJdslQueryProviderTest : WithAssertions {
    @InjectMockKs
    private lateinit var sut: KotlinJdslQueryProvider<String>

    @MockK
    private lateinit var entityManager: EntityManager

    @MockK
    private lateinit var query: SelectQuery<String>

    @MockK
    private lateinit var queryParams: Map<String, Any?>

    @MockK
    private lateinit var context: RenderContext

    @MockK
    private lateinit var stringTypedQuery: TypedQuery<String>

    @BeforeEach
    fun setUp() {
        mockkObject(JpqlEntityManagerUtils)

        sut.setEntityManager(entityManager)
    }

    @Test
    fun createQuery() {
        // given
        every {
            JpqlEntityManagerUtils.createQuery(any(), any<SelectQuery<String>>(), any(), any())
        } returns stringTypedQuery

        // when
        val actual = sut.createQuery()

        // then
        assertThat(actual).isEqualTo(stringTypedQuery)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(
                entityManager = entityManager,
                query = query,
                queryParams = queryParams,
                context = context,
            )
        }
    }
}
