package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.expression.Expression
import com.linecorp.kotlinjdsl.querymodel.jpql.expression.impl.JpqlValue
import java.time.temporal.Temporal
import java.util.Calendar
import java.util.Date
import java.util.UUID

internal object ExpressionUtils {
    /**
     * Returns `true` if the expression is a [JpqlValue] containing a basic type.
     *
     * Basic types are [String], [Number], [Boolean], [Enum], [UUID], [Temporal], [Date], [Calendar], [ByteArray], [CharArray].
     * https://jakarta.ee/specifications/persistence/3.2/jakarta-persistence-spec-3.2#a486
     *
     * If all values in the IN or NOT IN clause are basic types, they can be grouped into a single parameter
     * (e.g. `IN (?1)` where `?1` is a `List`) for query plan caching optimization.
     * Other types (like Entities or Paths) often require individual rendering or specific serialization logic
     * (e.g. extracting an ID from an Entity) and therefore cannot be safely grouped into a single raw value collection.
     */
    fun isBasicType(expression: Expression<*>): Boolean =
        expression is JpqlValue<*> &&
            (
                when (expression.value) {
                    is String,
                    // Number includes int, long, float, double, BigInteger, BigDecimal, etc.
                    is Number,
                    is Boolean,
                    is Char,
                    is UUID,
                    is Enum<*>,
                    // Temporal includes LocalDate, LocalDateTime, etc.
                    is Temporal,
                    // Date includes Date, Time, Timestamp
                    is Date,
                    is Calendar,
                    is ByteArray,
                    is CharArray,
                    -> true

                    else -> false
                }
            )
}
