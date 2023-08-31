package com.linecorp.kotlinjdsl.support.spring.batch.autoconfigure

import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderModule
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm.KotlinJdslQueryProviderFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
@ConditionalOnClass(JpqlRenderContext::class)
open class KotlinJdslAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    open fun jpqlRenderContext(serializers: List<JpqlSerializer<*>>): JpqlRenderContext {
        val userDefinedSerializers = object : JpqlRenderModule {
            override fun setupModule(context: JpqlRenderModule.SetupContext) {
                context.addAllSerializer(serializers.reversed())
            }
        }

        return JpqlRenderContext().registerModules(userDefinedSerializers)
    }

    @Bean
    open fun kotlinJdslQueryProviderFactory(renderContexts: List<RenderContext>): KotlinJdslQueryProviderFactory {
        val renderContext = renderContexts.reversed().reduce { acc, renderContext -> acc + renderContext }

        return KotlinJdslQueryProviderFactory(renderContext)
    }
}
