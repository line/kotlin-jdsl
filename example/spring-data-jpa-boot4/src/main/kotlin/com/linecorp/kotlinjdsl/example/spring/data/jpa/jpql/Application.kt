package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.orm.jpa.SharedEntityManagerCreator

@SpringBootApplication
class Application {
    @Bean
    fun entityManager(entityManagerFactory: EntityManagerFactory): EntityManager =
        SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
