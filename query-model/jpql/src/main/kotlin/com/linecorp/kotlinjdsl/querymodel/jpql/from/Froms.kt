package com.linecorp.kotlinjdsl.querymodel.jpql.from

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.querymodel.jpql.entity.Entity
import com.linecorp.kotlinjdsl.querymodel.jpql.from.impl.JpqlJoinedEntity
import com.linecorp.kotlinjdsl.querymodel.jpql.join.Join

@SinceJdsl("3.0.0")
object Froms {
    @SinceJdsl("3.0.0")
    fun reduce(froms: Iterable<From>): Iterable<From> {
        val reduced = mutableListOf<From>()

        for ((index, from) in froms.withIndex()) {
            if (index == 0) {
                reduced.add(from)
                continue
            }

            val last = reduced.last()

            if (last is Entity<*> && from is Join) {
                reduced[reduced.lastIndex] = JpqlJoinedEntity(last, from)
            } else {
                reduced.add(from)
            }
        }

        return reduced
    }
}
