package com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl

import com.linecorp.kotlinjdsl.Internal
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import java.time.LocalDate
import java.time.LocalTime

/**
 * Expression that represents the current date & time.
 */
@Internal
sealed interface JpqlCurrent<T : Any> : Expression<T> {

    object CurrentDate : JpqlCurrent<LocalDate>

    object CurrentTime : JpqlCurrent<LocalTime>
}
