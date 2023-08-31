@file:Suppress("unused")

package com.linecorp.kotlinjdsl.benchmark.render.jpql.introspector

import com.linecorp.kotlinjdsl.benchmark.sample.entity.Entities
import com.linecorp.kotlinjdsl.render.jpql.introspector.JpqlEntityDescription
import com.linecorp.kotlinjdsl.render.jpql.introspector.impl.JakartaJpqlIntrospector
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass
import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.BenchmarkMode
import org.openjdk.jmh.annotations.Level
import org.openjdk.jmh.annotations.Mode
import org.openjdk.jmh.annotations.OutputTimeUnit
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.Setup
import org.openjdk.jmh.annotations.State

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
class JakartaJpqlIntrospectorBenchmark {
    private lateinit var sut: JakartaJpqlIntrospector

    private lateinit var entityClass: KClass<*>

    @Param("0", "1", "2", "3", "4")
    private var index = 0

    @Setup(Level.Trial)
    fun setUp() {
        sut = JakartaJpqlIntrospector()
        entityClass = Entities.getClass(index)
    }

    @Benchmark
    fun benchmark(): JpqlEntityDescription? {
        return sut.introspect(entityClass)
    }
}
