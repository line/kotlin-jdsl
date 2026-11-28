package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expressions
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.render.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.render.jpql.entity.book.BookStatus
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.UUID

class ExpressionUtilsTest : WithAssertions {
    @Test
    fun isBasicType() {
        assertThat(ExpressionUtils.isBasicType(Expressions.value("string"))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(1))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(1L))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(BigDecimal.ONE))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(true))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value('a'))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(UUID.randomUUID()))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(BookStatus.IN_STOCK))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(LocalDate.of(2024, 1, 1)))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(Date()))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(Calendar.getInstance()))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(byteArrayOf(1, 2, 3)))).isTrue
        assertThat(ExpressionUtils.isBasicType(Expressions.value(charArrayOf('A', 'B', 'C')))).isTrue

        assertThat(ExpressionUtils.isBasicType(Paths.path(Book::price))).isFalse
        assertThat(ExpressionUtils.isBasicType(Expressions.nullValue<Int>())).isFalse
        assertThat(ExpressionUtils.isBasicType(Expressions.value(listOf(1)))).isFalse
    }
}
