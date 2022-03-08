package com.linecorp.kotlinjdsl.test.reactive.query

import org.hibernate.reactive.stage.Stage
import javax.persistence.Persistence


fun initializeDbQueries(filePath: String, factory: Stage.SessionFactory) {
    factory.withSession { stage ->
        object {}.javaClass.getResourceAsStream(filePath).bufferedReader()
            .readText()
            .split(";").map {
                stage.createNativeQuery<Int>(it).executeUpdate().toCompletableFuture()
            }.reduce { it1, it2 ->
                it1.thenCombine(it2) { r1, r2 -> r1 + r2 }
            }
        stage.flush()
    }.toCompletableFuture().get()
}

fun initFactory(): Stage.SessionFactory =
    Persistence.createEntityManagerFactory("order")
        .unwrap(Stage.SessionFactory::class.java).apply {
            initializeDbQueries("/drop-table.sql", this)
            initializeDbQueries("/init-table.sql", this)
        }
