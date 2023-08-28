package com.linecorp.kotlinjdsl.clazz

import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test

class SuperClassDepthComparatorTest : WithAssertions {
    private val sut = SuperClassDepthComparator(TargetType::class.java)

    private val superSuperType: Class<SuperSuperType> = SuperSuperType::class.java
    private val superType: Class<SuperType> = SuperType::class.java
    private val targetType: Class<TargetType> = TargetType::class.java
    private val subType: Class<SubType> = SubType::class.java

    @Test
    fun `WHEN sort using this Comparator, THEN it's sorted in the order in which the the super relationship is close`() {
        // given
        val supers = listOf(superSuperType, superType, targetType)

        // when
        val actual = supers.sortedWith(sut)

        // then
        assertThat(actual).isEqualTo(listOf(targetType, superType, superSuperType))
    }

    @Test
    fun `WHEN compare super class to non-super classes, THEN it determines that non-super class is bigger then super class`() {
        // when
        val actual = sut.compare(subType, superType)

        // then
        assertThat(actual).isPositive()
    }

    private open class SuperSuperType
    private open class SuperType : SuperSuperType()
    private open class TargetType : SuperType()
    private open class SubType : TargetType()
}
