package com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.autoconfigure

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.support.spring.data.jpa.javax.repository.KotlinJdslJpqlExecutor
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.BeanFactoryAware
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean

@SinceJdsl("3.0.0")
open class KotlinJdslJpaRepositoryFactoryBeanPostProcessor : BeanPostProcessor, BeanFactoryAware {
    private lateinit var kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor

    override fun setBeanFactory(beanFactory: BeanFactory) {
        kotlinJdslJpqlExecutor = beanFactory.getBean("kotlinJdslJpqlExecutor", KotlinJdslJpqlExecutor::class.java)
    }

    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        if (bean is JpaRepositoryFactoryBean<*, *, *> && bean.hasJdsl()) {
            bean.setCustomImplementation(kotlinJdslJpqlExecutor)
        }

        return super.postProcessAfterInitialization(bean, beanName)
    }

    private fun JpaRepositoryFactoryBean<*, *, *>.hasJdsl(): Boolean {
        return this.objectType.interfaces
            .any { it == KotlinJdslJpqlExecutor::class.java }
    }
}
