package com.linecorp.kotlinjdsl.support.spring.batch.javax.autoconfigure

import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderModule
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.support.spring.batch.javax.item.database.orm.KotlinJdslQueryProviderFactory
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
@ConditionalOnClass(JpqlRenderContext::class)
open class KotlinJdslAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
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

        return JpqlRenderContext().registerModules(userDefinedModule)
    }

    @Bean
    open fun kotlinJdslQueryProviderFactory(renderContexts: List<RenderContext>): KotlinJdslQueryProviderFactory {
        val renderContext = renderContexts.reversed().reduce { acc, renderContext -> acc + renderContext }

        return KotlinJdslQueryProviderFactory(renderContext)
    }
}
