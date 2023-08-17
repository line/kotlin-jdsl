package com.linecorp.kotlinjdsl.executor.spring.jpa.jpql

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.select.impl.JpqlSelectQuery

interface KotlinJdslJpqlExecutor {
    fun <T : Any> findOne(query: JpqlSelectQuery<T>): T?

    fun <T : Any> findOne(init: Jpql.() -> JpqlQueryable<Q>): T?
}
