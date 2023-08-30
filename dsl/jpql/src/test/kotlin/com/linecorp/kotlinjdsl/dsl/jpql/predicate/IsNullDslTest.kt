package com.linecorp.kotlinjdsl.dsl.jpql.predicate

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.path.Paths
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicates
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class IsNullDslTest : WithAssertions {
    private val expression1 = Paths.path(Employee::nickname)

    @Test
    fun isNull() {
        // when
        val predicate = queryPart {
            expression1.isNull()
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.isNull(
            expression1,
        )

        assertThat(actual).isEqualTo(excepted)
    }

    @Test
    fun isNotNull() {
        // when
        val predicate = queryPart {
            expression1.isNotNull()
        }

        val actual: Predicate = predicate // for type check

        // then
        val excepted = Predicates.isNotNull(
            expression1,
        )

        assertThat(actual).isEqualTo(excepted)
    }
}
