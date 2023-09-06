package com.linecorp.kotlinjdsl.example.jpql.hibernate

import jakarta.persistence.EntityManager
import java.io.File

object TestDataInitializer {
    private var initialized = false
    private const val PATH = "../src/main/resources"

    fun initialize(entityManager: EntityManager) {
        if (initialized) {
            return
        }

        val files = listOf(File("$PATH/schema.sql"), File("$PATH/data.sql"))
        val sqlStatements = files.flatMap { it.readText().split(";") }

        entityManager.transaction.begin()

        for (sqlStatement in sqlStatements) {
            val trimmedStatement = sqlStatement.trim()
            if (trimmedStatement.isNotEmpty()) {
                entityManager.createNativeQuery(trimmedStatement).executeUpdate()
            }
        }

        entityManager.transaction.commit()

        initialized = true
    }
}
