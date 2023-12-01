package com.linecorp.kotlinjdsl.example.jpql.hibernate

import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

object EntityManagerFactoryTestUtils {
    fun getEntityManagerFactory(): EntityManagerFactory {
        return entityManagerFactory
    }
}

private val entityManagerFactory = Persistence.createEntityManagerFactory("example").also {
    val thread = Thread {
        if (it.isOpen) it.close()

        println("EntityManagerFactory is closed")
    }

    Runtime.getRuntime().addShutdownHook(thread)
}
