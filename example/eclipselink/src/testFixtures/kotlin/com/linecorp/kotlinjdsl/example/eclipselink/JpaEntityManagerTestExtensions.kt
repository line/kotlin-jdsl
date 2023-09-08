package com.linecorp.kotlinjdsl.example.eclipselink

import org.eclipse.persistence.jpa.JpaEntityManager

fun <T> JpaEntityManager.withTransaction(work: () -> T): T {
    transaction.begin()
    return try {
        work()
    } finally {
        transaction.rollback()
    }
}
