package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.autoconfigure

import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments

open class KotlinJdslJpaRepositoryFactoryBeanPostProcessor(
    private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
) : BeanPostProcessor {
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is JpaRepositoryFactoryBean<*, *, *>) {
            bean.setRepositoryFragments(RepositoryFragments.just(kotlinJdslJpqlExecutor))
        }

        return super.postProcessAfterInitialization(bean, beanName)
    }
}
