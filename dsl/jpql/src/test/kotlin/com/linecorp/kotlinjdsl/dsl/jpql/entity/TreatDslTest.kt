package com.linecorp.kotlinjdsl.dsl.jpql.entity

import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.dsl.jpql.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.dsl.jpql.queryPart
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entities
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class TreatDslTest : WithAssertions {
    private val entity1 = Entities.entity(Employee::class)

    @Test
    fun treat() {
        // when
        val entity = queryPart {
            entity1.treat(FullTimeEmployee::class)
        }

        val actual: Entity<FullTimeEmployee> = entity // for type check

        // then
        val expected = Entities.treat(
            entity1,
            FullTimeEmployee::class,
        )

        assertThat(actual).isEqualTo(expected)
    }
}
