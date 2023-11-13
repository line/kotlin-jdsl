package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import io.mockk.mockkClass
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty0

class KotlinStyleJpqlPropertyIntrospectorTest : WithAssertions {
    private val sut = KotlinStyleJpqlPropertyIntrospector()

    @Test
    fun `introspect() returns the property name, when the property is KProperty1`() {
        // given
        val property = EntityClass1::property1

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("property1")
    }

    @Test
    fun `introspect() returns the property name without prefix, when the getter name starts with get`() {
        // given
        val property = EntityClass1::getProperty2

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("property2")
    }

    @Test
    fun `introspect() returns the property name with is, when the getter name starts with is`() {
        // given
        val property = EntityClass1::isProperty3

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("isProperty3")
    }

    @Test
    fun `introspect() returns the getter name, when the getter name does not start with get or is`() {
        // given
        val property = EntityClass1::someProperty

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("someProperty")
    }

    @Test
    fun `introspect() returns null, when the property is not KProperty1 or KFunction1`() {
        // given
        val property = mockkClass(KProperty0::class)

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isNull()
    }

    private class EntityClass1 {
        val property1: Long = 0

        fun getProperty2(): Long = 100

        fun isProperty3(): Boolean = true

        fun someProperty(): String = "someProperty"
    }
}
