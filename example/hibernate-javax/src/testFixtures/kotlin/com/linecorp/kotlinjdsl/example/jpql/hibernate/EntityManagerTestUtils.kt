package com.linecorp.kotlinjdsl.example.jpql.hibernate

import javax.persistence.EntityManager
import javax.persistence.Persistence

object EntityManagerTestUtils {
    fun getEntityManager(): EntityManager = entityManagerFactory.createEntityManager()
}

private val entityManagerFactory = Persistence.createEntityManagerFactory("example").also {
    val thread = Thread {
        it.close()

        println("EntityManagerFactory is closed")
    }

    Runtime.getRuntime().addShutdownHook(thread)
}
