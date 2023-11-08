package com.linecorp.kotlinjdsl.render.jpql.introspector.impl

import io.mockk.mockkClass
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import kotlin.reflect.KProperty0

class JakartaJpqlIntrospectorTest : WithAssertions {
    private val sut = JakartaJpqlIntrospector()

    companion object {
        private const val ENTITY_NAME_1 = "MyEntity1"
    }

    @Test
    fun `introspect(type) returns name of entity annotation, when entity annotation has name`() {
        // given
        val type = EntityClass1::class

        // when
        val actual = sut.introspect(type)

        // then
        assertThat(actual?.name).isEqualTo(ENTITY_NAME_1)
    }

    @Test
    fun `introspect(type) returns name of class, when entity annotation does not have name`() {
        // given
        val type = EntityClass2::class

        // when
        val actual = sut.introspect(type)

        // then
        assertThat(actual?.name).isEqualTo(EntityClass2::class.simpleName)
    }

    @Test
    fun `introspect(type) returns null, when there is no entity annotation`() {
        // given
        val type = NonEntityCLass1::class

        // when
        val actual = sut.introspect(type)

        // then
        assertThat(actual?.name).isNull()
    }

    @Test
    fun `introspect(property) returns property name, when property is KProperty1`() {
        // given
        val property = EntityClass1::property1

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("property1")
    }

    @Test
    fun `introspect(property) returns property name with prefix removed, when getter name starts with get`() {
        // given
        val property = EntityClass1::getProperty2

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("property2")
    }

    @Test
    fun `introspect(property) returns property name as is, when getter name starts with is`() {
        // given
        val property = EntityClass1::isProperty3

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("isProperty3")
    }

    @Test
    fun `introspect(property) returns property name as is, when getter name does not start with get or is`() {
        // given
        val property = EntityClass1::someProperty

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isEqualTo("someProperty")
    }

    @Test
    fun `introspect(property) returns null, when property is not KProperty1 or KFunction1`() {
        // given
        val property = mockkClass(KProperty0::class)

        // when
        val actual = sut.introspect(property)

        // then
        assertThat(actual?.name).isNull()
    }

    @jakarta.persistence.Entity(name = ENTITY_NAME_1)
    private open class EntityClass1 {
        val property1: Long = 0

        fun getProperty2(): Long = 100

        fun isProperty3(): Boolean = true

        fun someProperty(): String = "someProperty"
    }

    @jakarta.persistence.Entity
    private open class EntityClass2

    private open class NonEntityCLass1
}
