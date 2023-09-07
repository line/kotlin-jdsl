package com.linecorp.kotlinjdsl.example.eclipselink

import com.linecorp.kotlinjdsl.example.eclipselink.reader.MultipleLinesSqlCommandFileReader
import jakarta.persistence.Persistence
import org.eclipse.persistence.jpa.JpaEntityManagerFactory

object JpaEntityManagerFactoryTestUtils {
    fun getJpaEntityManagerFactory(): JpaEntityManagerFactory {
        return entityManagerFactory.unwrap(JpaEntityManagerFactory::class.java)
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
