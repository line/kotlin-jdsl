package com.linecorp.kotlinjdsl.querydsl.from

import com.linecorp.kotlinjdsl.querydsl.QueryDslImpl
import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.Test

internal class QueryDslImplRelationTest : WithKotlinJdslAssertions {
    @Test
    fun on() {
        val actual: Relation<Data1, Data2>

        QueryDslImpl(Data1::class.java).apply {
            actual = on(Data1::data2)
        }

        assertThat(actual).isEqualTo(Relation<Data1, Data2>("data2"))
    }

    @Test
    fun onCollection() {
        val actual: Relation<Data1, Data2>

        QueryDslImpl(Data1::class.java).apply {
            actual = on(Data1::data2List)
        }

        assertThat(actual).isEqualTo(Relation<Data1, Data2>("data2List"))
    }

    class Data1 {
        val data2: Data2 = Data2()
        val data2List: List<Data2> = emptyList()
    }

    class Data2
}
