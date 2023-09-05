package com.linecorp.kotlinjdsl.example.hibernate.reactive

import jakarta.persistence.Persistence
import org.hibernate.reactive.mutiny.Mutiny
import org.hibernate.reactive.stage.Stage

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
        it.close()

        println("EntityManagerFactory is closed")
    }

    Runtime.getRuntime().addShutdownHook(thread)
}
