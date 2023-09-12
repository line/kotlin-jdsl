package com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderModule
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutorImpl
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(EntityManager::class, JpqlRenderContext::class)
@SinceJdsl("3.0.0")
open class KotlinJdslAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @SinceJdsl("3.0.0")
    open fun jpqlRenderContext(serializers: List<JpqlSerializer<*>>): JpqlRenderContext {
        val userDefinedSerializers = object : JpqlRenderModule {
            override fun setupModule(context: JpqlRenderModule.SetupContext) {
                context.addAllSerializer(serializers.reversed())
            }
        }

        return JpqlRenderContext()
            .registerModules(userDefinedSerializers)
    }

    @Bean
    @SinceJdsl("3.0.0")
    open fun kotlinJdslJpqlExecutor(
        entityManager: EntityManager,
        renderContexts: List<RenderContext>,
    ): KotlinJdslJpqlExecutor {
        val renderContext = renderContexts.reversed().reduce { acc, renderContext -> acc + renderContext }

        return KotlinJdslJpqlExecutorImpl(
            entityManager = entityManager,
            renderContext = renderContext,
        )
    }

    @Bean
    @SinceJdsl("3.0.0")
    open fun kotlinJdslJpaRepositoryFactoryBeanPostProcessor(
        kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
    ): KotlinJdslJpaRepositoryFactoryBeanPostProcessor {
        return KotlinJdslJpaRepositoryFactoryBeanPostProcessor(kotlinJdslJpqlExecutor)
    }
}
