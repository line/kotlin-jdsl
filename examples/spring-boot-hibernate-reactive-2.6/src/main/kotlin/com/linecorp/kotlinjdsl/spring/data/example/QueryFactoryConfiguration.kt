package com.linecorp.kotlinjdsl.spring.data.example

import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import org.hibernate.reactive.mutiny.Mutiny.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory
import javax.persistence.Persistence

@Configuration
class QueryFactoryConfiguration {
    @Bean
    fun entityManagerFactory() = Persistence.createEntityManagerFactory("book")

    @Bean
    fun mutinySessionFactory(entityManagerFactory: EntityManagerFactory) =
        entityManagerFactory.unwrap(SessionFactory::class.java)
            .apply {
                withSession {
                    // currently H2 db does not support officially
                    // and does not allow extract & create schema with h2 db in hibernate-reactive
                    // so DDL query execute directly
                    it.createNativeQuery<Int>(
                        """
                        create table book (
                            id bigint not null auto_increment,
                            name varchar(255),
                            primary key (id)
                        )
                    """.trimIndent()
                    ).executeUpdate()
                }.subscribeAsCompletionStage().get()
            }

    @Bean
    fun queryFactory(sessionFactory: SessionFactory): SpringDataHibernateMutinyReactiveQueryFactory {
        return SpringDataHibernateMutinyReactiveQueryFactory(
            sessionFactory = sessionFactory,
            subqueryCreator = SubqueryCreatorImpl()
        )
    }
}
