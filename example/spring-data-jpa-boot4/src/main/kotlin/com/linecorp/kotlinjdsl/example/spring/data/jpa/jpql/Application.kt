package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql

import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.orm.jpa.SharedEntityManagerCreator

@SpringBootApplication
class Application {
    /**
     * In Spring Boot 4, the EntityManager bean is no longer auto-registered.
     * KotlinJdslJpqlExecutor requires EntityManager, so manual registration is needed.
     * See the Spring supports documentation in docs for more details.
     */
    @Bean
    fun entityManager(entityManagerFactory: EntityManagerFactory): EntityManager =
        SharedEntityManagerCreator.createSharedEntityManager(entityManagerFactory)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
