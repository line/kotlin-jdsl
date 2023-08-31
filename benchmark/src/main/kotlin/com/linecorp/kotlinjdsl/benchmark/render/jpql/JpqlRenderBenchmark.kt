@file:Suppress("unused")

package com.linecorp.kotlinjdsl.benchmark.render.jpql

import com.linecorp.kotlinjdsl.benchmark.sample.query.Queries
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import com.linecorp.kotlinjdsl.render.jpql.JpqlRendered
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderer
import java.util.concurrent.TimeUnit
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
class JpqlRenderBenchmark {
    private lateinit var sut: JpqlRenderer

    private lateinit var context: JpqlRenderContext

    private lateinit var query: JpqlQuery<*>

    @Param("0", "1", "2", "3", "4", "5")
    private var index = 0

    @Setup(Level.Trial)
    fun setUp() {
        sut = JpqlRenderer()
        context = JpqlRenderContext()
        query = Queries.get(index)
    }

    @Benchmark
    fun benchmark(): JpqlRendered {
        return sut.render(query, context)
    }
}
