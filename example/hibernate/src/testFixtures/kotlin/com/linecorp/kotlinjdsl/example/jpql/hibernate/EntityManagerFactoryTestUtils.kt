package com.linecorp.kotlinjdsl.example.jpql.hibernate

import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

object EntityManagerFactoryTestUtils {
    fun getEntityManagerFactory(): EntityManagerFactory {
        return entityManagerFactory
    }
}

private val entityManagerFactory = Persistence.createEntityManagerFactory("example").also {
    val thread = Thread {
        it.close()

        println("EntityManagerFactory is closed")
    }

    Runtime.getRuntime().addShutdownHook(thread)
}
