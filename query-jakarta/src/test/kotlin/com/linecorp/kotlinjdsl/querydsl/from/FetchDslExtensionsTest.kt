package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import jakarta.persistence.criteria.JoinType

@ExtendWith(MockKExtension::class)
internal class FetchDslExtensionsTest : WithKotlinJdslAssertions {
    @MockK
    private lateinit var dsl: FetchDsl

    @Test
    fun fetch() {
        // given
        with(dsl) {
            justRun { fetch(Data1::class, Data2::class, Relation("data2"), JoinType.LEFT) }
            justRun { fetch(Data1::class, Data2::class, Relation("data2List"), JoinType.RIGHT) }
        }

        // when
        with(dsl) {
            fetch(Data1::data2, JoinType.LEFT)
            fetch(Data1::data2List, JoinType.RIGHT)
        }

        // then
        verify(exactly = 1) {
            with(dsl) {
                fetch(Data1::class, Data2::class, Relation("data2"), JoinType.LEFT)
                fetch(Data1::class, Data2::class, Relation("data2List"), JoinType.RIGHT)
            }
        }

        confirmVerified(dsl)
    }

    class Data1 {
        val data2: Data2 = Data2()
        val data2List: List<Data2> = emptyList()
    }

    class Data2
}
