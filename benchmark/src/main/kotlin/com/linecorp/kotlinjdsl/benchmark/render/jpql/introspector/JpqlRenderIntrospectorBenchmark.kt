@file:Suppress("unused")

package com.linecorp.kotlinjdsl.benchmark.render.jpql.introspector

import com.linecorp.kotlinjdsl.benchmark.sample.entity.Entities
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlRenderIntrospector
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
class JpqlRenderIntrospectorBenchmark {
    private lateinit var sut: JpqlRenderIntrospector

    private lateinit var entityClass: KClass<*>

    @Param("0", "1", "2", "3", "4")
    private var index = 0

    @Setup(Level.Trial)
    fun setUp() {
        sut = JpqlRenderContext().getValue(JpqlRenderIntrospector)
        entityClass = Entities.getClass(index)
    }

    @Benchmark
    fun benchmark(): JpqlEntityDescription? {
        return sut.introspect(entityClass)
    }
}