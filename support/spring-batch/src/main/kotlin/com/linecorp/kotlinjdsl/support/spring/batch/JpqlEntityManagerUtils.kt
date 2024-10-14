package com.linecorp.kotlinjdsl.support.spring.batch

import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderedParams
import jakarta.persistence.EntityManager
import jakarta.persistence.Query
import jakarta.persistence.TypedQuery
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

internal object JpqlEntityManagerUtils {
    fun <T : Any> createQuery(
        entityManager: EntityManager,
        query: JpqlQuery<*>,
        queryParams: Map<String, Any?>,
        returnType: KClass<T>,
        context: RenderContext,
    ): TypedQuery<T> {
        val rendered = JpqlRendererHolder.get().render(query, queryParams, context)

        return createQuery(entityManager, rendered, returnType)
    }

    private fun <T : Any> createQuery(
        entityManager: EntityManager,
        rendered: JpqlRendered,
        resultClass: KClass<T>,
    ): TypedQuery<T> {
        return entityManager.createQuery(rendered.query, resultClass.java).apply {
            setParams(this, rendered.params)
        }
    }

    private fun setParams(query: Query, params: JpqlRenderedParams) {
        val parameterNameSet = query.parameters.map { it.name }.toHashSet()

        params.forEach { (name, value) ->
            if (parameterNameSet.contains(name)) {
                query.setParameter(name, value)
            } else if (log.isDebugEnabled) {
                log.debug(
                    "No parameter named '$name' in query " +
                        "with named parameters [${parameterNameSet.joinToString()}], " +
                        "parameter binding skipped",
                )
            }
        }
    }
}

private val log = LoggerFactory.getLogger(JpqlEntityManagerUtils::class.java)
