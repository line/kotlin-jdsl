package com.linecorp.kotlinjdsl.benchmark.sample.query.select

import com.linecorp.kotlinjdsl.benchmark.sample.entity.book.Book
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

object SelectQuery4 : () -> SelectQuery<*> {
    override fun invoke(): SelectQuery<*> {
        return jpql {
            select(
                customExpression(String::class, "CAST({0} AS VARCHAR)", path(Book::price)),
            ).from(
                entity(Book::class),
            )
        }
    }
}
