package com.linecorp.kotlinjdsl.hibernate.example

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.QueryFactoryImpl
import com.linecorp.kotlinjdsl.hibernate.example.entity.Book
import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.querydsl.expression.col
import javax.persistence.EntityManager
import javax.persistence.Persistence

private val entityManagerFactory = Persistence.createEntityManagerFactory("example")

fun main(args: Array<String>) {
    val entityManager = entityManagerFactory.createEntityManager().also {
        it.initializeData()
    }

    val queryFactory: QueryFactory = QueryFactoryImpl(
        criteriaQueryCreator = CriteriaQueryCreatorImpl(entityManager),
        subqueryCreator = SubqueryCreatorImpl()
    )

    val books: List<Book> = queryFactory.listQuery {
        select(entity(Book::class))
        from(entity(Book::class))
        where(or(args.map { col(Book::name).like("%${it}%") }))
    }

    println(books)
}

private fun EntityManager.initializeData() {
    transaction.run {
        begin()
        persist(Book(name = "Hamlet"))
        persist(Book(name = "King Lear"))
        persist(Book(name = "Romeo and Juliet"))
        persist(Book(name = "Macbeth"))
        commit()
    }
}