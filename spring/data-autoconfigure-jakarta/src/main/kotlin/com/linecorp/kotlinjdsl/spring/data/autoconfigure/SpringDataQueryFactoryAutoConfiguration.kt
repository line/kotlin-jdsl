package com.linecorp.kotlinjdsl.spring.data.autoconfigure

import com.linecorp.kotlinjdsl.QueryFactory
import com.linecorp.kotlinjdsl.query.creator.CriteriaQueryCreator
import com.linecorp.kotlinjdsl.query.creator.SubqueryCreator
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactory
import com.linecorp.kotlinjdsl.spring.data.SpringDataQueryFactoryImpl
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import jakarta.persistence.EntityManager

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EntityManager::class)
@Import(QueryCreatorConfiguration::class)
class SpringDataQueryFactoryAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(QueryFactory::class)
    fun springDataQueryFactory(
        criteriaQueryCreator: CriteriaQueryCreator,
        subqueryCreator: SubqueryCreator,
    ): SpringDataQueryFactory {
        return SpringDataQueryFactoryImpl(
            criteriaQueryCreator = criteriaQueryCreator,
            subqueryCreator = subqueryCreator,
        )
    }
}
