package com.linecorp.kotlinjdsl.support.spring.batch.javax.item.database.orm

import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.support.spring.batch.javax.JpqlEntityManagerUtils
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import io.mockk.verifySequence
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
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
    private lateinit var context: RenderContext

    @MockK
    private lateinit var query1: SelectQuery<String>

    @MockK
    private lateinit var queryParams1: Map<String, Any?>

    @MockK
    private lateinit var stringTypedQuery1: TypedQuery<String>

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
        } returns stringTypedQuery1

        // when
        val actual = sut.createQuery()

        // then
        assertThat(actual).isEqualTo(stringTypedQuery1)

        verifySequence {
            JpqlEntityManagerUtils.createQuery(
                entityManager = entityManager,
                query = query1,
                queryParams = queryParams1,
                context = context,
            )
        }
    }
}
