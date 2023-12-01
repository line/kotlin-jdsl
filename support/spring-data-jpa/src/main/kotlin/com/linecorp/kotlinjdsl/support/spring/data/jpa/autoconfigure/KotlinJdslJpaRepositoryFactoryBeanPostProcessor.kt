package com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.Lazy
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments
import org.springframework.stereotype.Component

@Component
@SinceJdsl("3.0.0")
open class KotlinJdslJpaRepositoryFactoryBeanPostProcessor : BeanPostProcessor {
    @Lazy
    @Autowired
    lateinit var kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is JpaRepositoryFactoryBean<*, *, *>) {
            bean.setRepositoryFragments(RepositoryFragments.just(kotlinJdslJpqlExecutor))
        }

        return super.postProcessAfterInitialization(bean, beanName)
    }
}
