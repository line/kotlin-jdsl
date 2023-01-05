package com.linecorp.kotlinjdsl.spring.data.autoconfigure

import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreatorImpl
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreatorImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import jakarta.persistence.EntityManager

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EntityManager::class)
class QueryCreatorConfiguration {
    @Bean
    @ConditionalOnMissingBean(CriteriaQueryCreator::class)
    fun criteriaQueryCreator(entityManager: EntityManager): CriteriaQueryCreator {
        return CriteriaQueryCreatorImpl(entityManager)
    }

    @Bean
    @ConditionalOnMissingBean(SubqueryCreator::class)
    fun subqueryCreator(): SubqueryCreator {
        return SubqueryCreatorImpl()
    }
}
