package com.linecorp.kotlinjdsl.example.eclipselink

import com.linecorp.kotlinjdsl.example.eclipselink.reader.MultipleLinesSqlCommandFileReader
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.Persistence

object EntityManagerFactoryTestUtils {
    fun getEntityManagerFactory(): EntityManagerFactory {
        return entityManagerFactory
    }
}

private val entityManagerFactory = Persistence.createEntityManagerFactory(
    "example",
    mapOf(
        "jakarta.persistence.schema-generation.create-script-source" to
            MultipleLinesSqlCommandFileReader("../src/main/resources/schema.sql"),
        "jakarta.persistence.schema-generation.drop-script-source" to
            MultipleLinesSqlCommandFileReader("../src/main/resources/drop.sql"),
        "jakarta.persistence.sql-load-script-source" to
            MultipleLinesSqlCommandFileReader("../src/main/resources/data.sql"),
    ),
).also {
    val thread = Thread {
        it.close()

        println("EntityManagerFactory is closed")
    }

    Runtime.getRuntime().addShutdownHook(thread)
}
