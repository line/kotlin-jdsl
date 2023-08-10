package com.linecorp.kotlinjdsl.benchmark.sample.query.select

import com.linecorp.kotlinjdsl.benchmark.entity.author.Author
import com.linecorp.kotlinjdsl.benchmark.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery

object SelectQuery2 : () -> SelectQuery<*> {
    override fun invoke(): SelectQuery<*> {
        return jpql {
            select(
                path(Author::authorId),
            ).from(
                entity(Author::class),
                join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
            ).groupBy(
                path(Author::authorId),
            ).orderBy(
                count(Author::authorId).desc(),
            )
        }
    }
}
