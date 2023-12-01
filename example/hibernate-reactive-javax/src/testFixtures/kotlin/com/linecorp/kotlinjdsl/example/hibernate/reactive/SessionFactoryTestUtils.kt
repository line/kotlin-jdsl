package com.linecorp.kotlinjdsl.example.hibernate.reactive

import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.stage.Stage
import javax.persistence.Persistence

object SessionFactoryTestUtils {
    fun getMutinySessionFactory(): Mutiny.SessionFactory {
        return entityManagerFactory.unwrap(Mutiny.SessionFactory::class.java)
    }

    fun getStageSessionFactory(): Stage.SessionFactory {
        return entityManagerFactory.unwrap(Stage.SessionFactory::class.java)
    }
}

private val entityManagerFactory = Persistence.createEntityManagerFactory("example").also {
    val thread = Thread {
        if (it.isOpen) it.close()

        println("EntityManagerFactory is closed")
    }

    Runtime.getRuntime().addShutdownHook(thread)
}
