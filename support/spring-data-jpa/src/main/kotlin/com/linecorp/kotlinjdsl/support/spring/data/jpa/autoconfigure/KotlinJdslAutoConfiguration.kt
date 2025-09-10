package com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderModule
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutorImpl
import jakarta.persistence.EntityManager
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.support.CrudMethodMetadataPostProcessorAdaptor

@AutoConfiguration
@ConditionalOnClass(EntityManager::class, JpqlRenderContext::class)
@Import(KotlinJdslJpaRepositoryFactoryBeanPostProcessor::class)
@SinceJdsl("3.0.0")
open class KotlinJdslAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @SinceJdsl("3.0.0")
    open fun jpqlRenderContext(
        serializers: List<JpqlSerializer<*>>,
        introspectors: List<JpqlIntrospector>,
    ): JpqlRenderContext {
        val userDefinedModule = object : JpqlRenderModule {
            override fun setupModule(context: JpqlRenderModule.SetupContext) {
                context.addAllSerializer(serializers.reversed())

                introspectors.reversed().forEach {
                    context.prependIntrospector(it)
                }
            }
        }

        return JpqlRenderContext()
            .registerModules(userDefinedModule)
    }

    @Bean
    @SinceJdsl("3.0.0")
    open fun kotlinJdslJpqlExecutor(
        entityManager: EntityManager,
        renderContexts: List<RenderContext>,
    ): KotlinJdslJpqlExecutor {
        val renderContext = renderContexts.reversed().reduce { acc, renderContext -> acc + renderContext }
        val metadata = CrudMethodMetadataPostProcessorAdaptor().getCrudMethodMetadata()

        return KotlinJdslJpqlExecutorImpl(
            entityManager = entityManager,
            renderContext = renderContext,
            metadata = metadata,
        )
    }
}
