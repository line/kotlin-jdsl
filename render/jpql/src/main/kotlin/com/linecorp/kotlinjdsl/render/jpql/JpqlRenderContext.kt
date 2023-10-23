package com.linecorp.kotlinjdsl.render.jpql

import com.linecorp.kotlinjdsl.SinceJdsl
import com.linecorp.kotlinjdsl.clazz.ClassUtils
import com.linecorp.kotlinjdsl.render.RenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.CombinedJpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlIntrospectorModifier
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.impl.JakartaJpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.introspector.impl.JavaxJpqlIntrospector
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlRenderSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.JpqlSerializerModifier
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlAliasedExpressionSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlAndSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlAvgSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlBetweenSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlCaseValueSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlCaseWhenSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlCoalesceSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlCountSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlCustomExpressionSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlDeleteQuerySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlDerivedEntitySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlDivideSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlEntityPropertySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlEntitySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlEntityTreatSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlEntityTypeSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlEqualAllSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlEqualAnySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlEqualSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlExistsSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlExpressionParenthesesSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlExpressionSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlFunctionSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlGreaterThanAllSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlGreaterThanAnySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlGreaterThanOrEqualToAllSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlGreaterThanOrEqualToAnySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlGreaterThanOrEqualToSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlGreaterThanSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlInSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlInSubquerySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlInnerAssociationFetchJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlInnerAssociationJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlInnerFetchJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlInnerJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlIsEmptySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlIsNotEmptySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlIsNotNullSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlIsNullSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlJoinedEntitySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLeftAssociationFetchJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLeftAssociationJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLeftFetchJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLeftJoinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLengthSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLessThanAllSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLessThanAnySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLessThanOrEqualToAllSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLessThanOrEqualToAnySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLessThanOrEqualToSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLessThanSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLikeSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLiteralSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlLocateSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlMaxSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlMinSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlMinusSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNewSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotBetweenSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotEqualAllSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotEqualAnySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotEqualSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotExistsSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotInSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotInSubquerySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotLikeSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNotSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNullIfSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlNullSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlOrSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlParamSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlPathPropertySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlPathTreatSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlPathTypeSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlPlusSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlPredicateParenthesesSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlSelectQuerySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlSortSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlSubquerySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlSumSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlTimesSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlUpdateQuerySerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlUpperSerializer
import com.linecorp.kotlinjdsl.render.jpql.serializer.impl.JpqlValueSerializer

/**
 * RenderContext for rendering JPQL.
 */
@SinceJdsl("3.0.0")
class JpqlRenderContext private constructor(
    private val modules: Iterable<JpqlRenderModule>,
) : RenderContext {
    private val introspectors: MutableList<JpqlIntrospector> = mutableListOf()
    private val introspectorModifiers: MutableList<JpqlIntrospectorModifier> = mutableListOf()

    private val serializers: MutableList<JpqlSerializer<*>> = mutableListOf()
    private val serializerModifiers: MutableList<JpqlSerializerModifier> = mutableListOf()

    private val delegate: RenderContext

    @SinceJdsl("3.0.0")
    constructor() : this(listOf(DefaultModule()))

    init {
        modules.forEach { setup(it) }
        delegate = createRenderContext()
    }

    private fun setup(module: JpqlRenderModule) {
        val setupContext = object : JpqlRenderModule.SetupContext {
            override fun addSerializer(serializer: JpqlSerializer<*>) {
                serializers.add(serializer)
            }

            override fun addAllSerializer(vararg serializers: JpqlSerializer<*>) {
                this@JpqlRenderContext.serializers.addAll(serializers)
            }

            override fun addAllSerializer(serializers: Iterable<JpqlSerializer<*>>) {
                this@JpqlRenderContext.serializers.addAll(serializers)
            }

            override fun prependSerializerModifier(serializerModifier: JpqlSerializerModifier) {
                serializerModifiers.add(0, serializerModifier)
            }

            override fun appendSerializerModifier(serializerModifier: JpqlSerializerModifier) {
                serializerModifiers.add(serializerModifier)
            }

            override fun prependIntrospector(introspector: JpqlIntrospector) {
                introspectors.add(0, introspector)
            }

            override fun appendIntrospector(introspector: JpqlIntrospector) {
                introspectors.add(introspector)
            }

            override fun prependIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier) {
                introspectorModifiers.add(0, introspectorModifier)
            }

            override fun appendIntrospectorModifier(introspectorModifier: JpqlIntrospectorModifier) {
                introspectorModifiers.add(introspectorModifier)
            }
        }

        module.setupModule(setupContext)
    }

    private fun createRenderContext(): RenderContext {
        val renderIntrospector = createRenderIntrospector()
        val renderSerializer = createRenderSerializer()

        return renderIntrospector + renderSerializer
    }

    private fun createRenderIntrospector(): JpqlRenderIntrospector {
        var introspector = introspectors.reduce { acc, introspector ->
            CombinedJpqlIntrospector(acc, introspector)
        }

        introspectorModifiers.forEach {
            introspector = it.modifyIntrospector(introspector)
        }

        return JpqlRenderIntrospector(introspector)
    }

    private fun createRenderSerializer(): JpqlRenderSerializer {
        val serializers = this.serializers.toMutableList()

        serializers.forEach { serializer ->
            serializerModifiers.forEach { modifier ->
                val modifiedSerializer = modifier.modifySerializer(serializer)

                serializers.add(modifiedSerializer)
            }
        }

        return JpqlRenderSerializer(serializers)
    }

    /**
     * Returns a new [JpqlRenderContext] with the module.
     */
    @SinceJdsl("3.0.0")
    fun registerModule(module: JpqlRenderModule): JpqlRenderContext {
        return JpqlRenderContext(this.modules + module)
    }

    /**
     * Returns a new [JpqlRenderContext] with the module.
     */
    @SinceJdsl("3.0.0")
    fun registerModules(vararg modules: JpqlRenderModule): JpqlRenderContext {
        return JpqlRenderContext(this.modules.toList() + modules.toList())
    }

    /**
     * Returns a new [JpqlRenderContext] with the module.
     */
    @SinceJdsl("3.0.0")
    fun registerModules(modules: Iterable<JpqlRenderModule>): JpqlRenderContext {
        return JpqlRenderContext(this.modules.toList() + modules.toList())
    }

    override fun <E : RenderContext.Element> get(key: RenderContext.Key<E>): E? {
        return delegate[key]
    }

    override fun <R> fold(initial: R, operation: (R, RenderContext.Element) -> R): R {
        return delegate.fold(initial, operation)
    }

    override fun minusKey(key: RenderContext.Key<*>): RenderContext {
        return delegate.minusKey(key)
    }
}

private class DefaultModule : JpqlRenderModule {
    private val isJavaxPresent = ClassUtils.isPresent("javax.persistence.Entity")
    private val isJakartaPresent = ClassUtils.isPresent("jakarta.persistence.Entity")

    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        if (isJavaxPresent) {
            context.appendIntrospector(JavaxJpqlIntrospector())
        }

        if (isJakartaPresent) {
            context.appendIntrospector(JakartaJpqlIntrospector())
        }

        context.addAllSerializer(
            JpqlAliasedExpressionSerializer(),
            JpqlAndSerializer(),
            JpqlAvgSerializer(),
            JpqlBetweenSerializer(),
            JpqlCaseValueSerializer(),
            JpqlCaseWhenSerializer(),
            JpqlCoalesceSerializer(),
            JpqlCountSerializer(),
            JpqlCustomExpressionSerializer(),
            JpqlDeleteQuerySerializer(),
            JpqlDerivedEntitySerializer(),
            JpqlDivideSerializer(),
            JpqlEntityPropertySerializer(),
            JpqlEntitySerializer(),
            JpqlEntityTreatSerializer(),
            JpqlEntityTypeSerializer(),
            JpqlEqualAllSerializer(),
            JpqlEqualAnySerializer(),
            JpqlEqualSerializer(),
            JpqlExistsSerializer(),
            JpqlExpressionParenthesesSerializer(),
            JpqlExpressionSerializer(),
            JpqlFunctionSerializer(),
            JpqlGreaterThanAllSerializer(),
            JpqlGreaterThanAnySerializer(),
            JpqlGreaterThanOrEqualToAllSerializer(),
            JpqlGreaterThanOrEqualToAnySerializer(),
            JpqlGreaterThanOrEqualToSerializer(),
            JpqlGreaterThanSerializer(),
            JpqlInnerAssociationFetchJoinSerializer(),
            JpqlInnerAssociationJoinSerializer(),
            JpqlInnerFetchJoinSerializer(),
            JpqlInnerJoinSerializer(),
            JpqlInSerializer(),
            JpqlInSubquerySerializer(),
            JpqlIsEmptySerializer(),
            JpqlIsNotEmptySerializer(),
            JpqlIsNotNullSerializer(),
            JpqlIsNullSerializer(),
            JpqlJoinedEntitySerializer(),
            JpqlLeftAssociationFetchJoinSerializer(),
            JpqlLeftAssociationJoinSerializer(),
            JpqlLeftFetchJoinSerializer(),
            JpqlLeftJoinSerializer(),
            JpqlLengthSerializer(),
            JpqlLessThanAllSerializer(),
            JpqlLessThanAnySerializer(),
            JpqlLessThanOrEqualToAllSerializer(),
            JpqlLessThanOrEqualToAnySerializer(),
            JpqlLessThanOrEqualToSerializer(),
            JpqlLessThanSerializer(),
            JpqlLikeSerializer(),
            JpqlLiteralSerializer(),
            JpqlLocateSerializer(),
            JpqlMaxSerializer(),
            JpqlMinSerializer(),
            JpqlMinusSerializer(),
            JpqlNewSerializer(),
            JpqlNotBetweenSerializer(),
            JpqlNotEqualAllSerializer(),
            JpqlNotEqualAnySerializer(),
            JpqlNotEqualSerializer(),
            JpqlNotExistsSerializer(),
            JpqlNotInSerializer(),
            JpqlNotInSubquerySerializer(),
            JpqlNotLikeSerializer(),
            JpqlNotSerializer(),
            JpqlNullIfSerializer(),
            JpqlNullSerializer(),
            JpqlOrSerializer(),
            JpqlParamSerializer(),
            JpqlPathPropertySerializer(),
            JpqlPathTreatSerializer(),
            JpqlPathTypeSerializer(),
            JpqlPlusSerializer(),
            JpqlPredicateParenthesesSerializer(),
            JpqlSelectQuerySerializer(),
            JpqlSortSerializer(),
            JpqlSubquerySerializer(),
            JpqlSumSerializer(),
            JpqlTimesSerializer(),
            JpqlUpdateQuerySerializer(),
            JpqlUpperSerializer(),
            JpqlValueSerializer(),
        )
    }
}
