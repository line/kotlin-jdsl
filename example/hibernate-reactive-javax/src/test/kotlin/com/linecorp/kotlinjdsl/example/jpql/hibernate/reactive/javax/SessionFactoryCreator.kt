package com.linecorp.kotlinjdsl.example.jpql.hibernate.reactive.javax

import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.stage.Stage

object SessionFactoryCreator {
    val queries = sequenceOf("drop.sql", "schema.sql", "data.sql").map {
        Thread.currentThread().contextClassLoader.getResourceAsStream(it)!!.bufferedReader().readText().split(";")
            .map { it.trim() }
    }.flatten().toList()

    fun mutinySessionFactory(factory: EntityManagerFactory = entityManagerFactory()): Mutiny.SessionFactory =
        factory.unwrap(Mutiny.SessionFactory::class.java).apply {
            queries.forEach { query ->
                withSession {
                    it.createNativeQuery<Unit>(query).executeUpdate()
                }.await().indefinitely()
            }
        }
    fun stageSessionFactory(factory: EntityManagerFactory = entityManagerFactory()): Stage.SessionFactory =
        factory.unwrap(Stage.SessionFactory::class.java).apply {
            queries.forEach { query ->
                withSession {
                    it.createNativeQuery<Unit>(query).executeUpdate()
                }.toCompletableFuture().get()
            }
        }

    private fun entityManagerFactory(): EntityManagerFactory = Persistence.createEntityManagerFactory("jdsl")
}
