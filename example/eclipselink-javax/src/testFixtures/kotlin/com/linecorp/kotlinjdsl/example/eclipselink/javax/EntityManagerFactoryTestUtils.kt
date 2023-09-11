package com.linecorp.kotlinjdsl.example.eclipselink.javax

import com.linecorp.kotlinjdsl.example.eclipselink.javax.reader.MultipleLinesSqlCommandFileReader
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

object EntityManagerFactoryTestUtils {
    fun getEntityManagerFactory(): EntityManagerFactory {
        return entityManagerFactory
    }
}

private val entityManagerFactory = Persistence.createEntityManagerFactory(
    "example",
    mapOf(
        "javax.persistence.schema-generation.create-script-source" to
            MultipleLinesSqlCommandFileReader("../src/main/resources/schema.sql"),
        "javax.persistence.schema-generation.drop-script-source" to
            MultipleLinesSqlCommandFileReader("../src/main/resources/drop.sql"),
        "javax.persistence.sql-load-script-source" to
            MultipleLinesSqlCommandFileReader("../src/main/resources/data.sql"),
    ),
).also {
    val thread = Thread {
        it.close()

        println("EntityManagerFactory is closed")
    }

    Runtime.getRuntime().addShutdownHook(thread)
}
