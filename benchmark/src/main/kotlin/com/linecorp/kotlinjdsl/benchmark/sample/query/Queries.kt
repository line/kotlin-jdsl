package com.linecorp.kotlinjdsl.benchmark.sample.query

import com.linecorp.kotlinjdsl.benchmark.sample.query.delete.DeleteQuery1
import com.linecorp.kotlinjdsl.benchmark.sample.query.select.SelectQuery1
import com.linecorp.kotlinjdsl.benchmark.sample.query.select.SelectQuery2
import com.linecorp.kotlinjdsl.benchmark.sample.query.select.SelectQuery3
import com.linecorp.kotlinjdsl.benchmark.sample.query.update.UpdateQuery1
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery

object Queries {
    private val queries = listOf(
        SelectQuery1(),
        SelectQuery2(),
        SelectQuery3(),
        UpdateQuery1(),
        DeleteQuery1(),
    )

    fun get(index: Int): JpqlQuery<*> {
        return queries[index]
    }
}
