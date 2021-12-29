package com.linecorp.kotlinjdsl.test.time

import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class TimeRange(
    override val start: Time,
    override val endInclusive: Time,
    private val step: TimeStep = 1.days()
) : Iterable<Time>, ClosedRange<Time> {
    infix fun step(step: TimeStep) = TimeRange(start, endInclusive, step)

    override fun iterator(): Iterator<Time> = TimeRangeIterator(start, endInclusive, step)
}

class TimeRangeIterator(
    start: Time,
    private val end: Time,
    private val step: TimeStep = 1.days()
) : Iterator<Time> {
    private var current = start

    override fun hasNext(): Boolean = current <= end

    override fun next(): Time {
        val next = current
        current += step
        return next
    }
}

interface TimeStep {
    fun plus(time: ZonedDateTime): ZonedDateTime

    fun minus(time: ZonedDateTime): ZonedDateTime
}

data class YearStep(val step: Long) : TimeStep {
    override fun plus(time: ZonedDateTime): ZonedDateTime = time.plus(step, ChronoUnit.YEARS)
    override fun minus(time: ZonedDateTime): ZonedDateTime = time.minus(step, ChronoUnit.YEARS)
}

data class MonthStep(val step: Long) : TimeStep {
    override fun plus(time: ZonedDateTime): ZonedDateTime = time.plus(step, ChronoUnit.MONTHS)
    override fun minus(time: ZonedDateTime): ZonedDateTime = time.minus(step, ChronoUnit.MONTHS)
}

data class DayStep(val step: Long) : TimeStep {
    override fun plus(time: ZonedDateTime): ZonedDateTime = time.plus(step, ChronoUnit.DAYS)
    override fun minus(time: ZonedDateTime): ZonedDateTime = time.minus(step, ChronoUnit.DAYS)
}

data class HourStep(val step: Long) : TimeStep {
    override fun plus(time: ZonedDateTime): ZonedDateTime = time.plus(step, ChronoUnit.HOURS)
    override fun minus(time: ZonedDateTime): ZonedDateTime = time.minus(step, ChronoUnit.HOURS)
}

data class MinuteStep(val step: Long) : TimeStep {
    override fun plus(time: ZonedDateTime): ZonedDateTime = time.plus(step, ChronoUnit.MINUTES)
    override fun minus(time: ZonedDateTime): ZonedDateTime = time.minus(step, ChronoUnit.MINUTES)
}

data class SecondStep(val step: Long) : TimeStep {
    override fun plus(time: ZonedDateTime): ZonedDateTime = time.plus(step, ChronoUnit.SECONDS)
    override fun minus(time: ZonedDateTime): ZonedDateTime = time.minus(step, ChronoUnit.SECONDS)
}

data class MillisecondStep(val step: Long) : TimeStep {
    override fun plus(time: ZonedDateTime): ZonedDateTime = time.plus(step, ChronoUnit.MILLIS)
    override fun minus(time: ZonedDateTime): ZonedDateTime = time.minus(step, ChronoUnit.MILLIS)
}

fun Int.years() = this.toLong().years()
fun Int.months() = this.toLong().months()
fun Int.days() = this.toLong().days()
fun Int.hours() = this.toLong().hours()
fun Int.minutes() = this.toLong().minutes()
fun Int.seconds() = this.toLong().seconds()
fun Int.millis() = this.toLong().millis()

fun Long.years() = YearStep(this)
fun Long.months() = MonthStep(this)
fun Long.days() = DayStep(this)
fun Long.hours() = HourStep(this)
fun Long.minutes() = MinuteStep(this)
fun Long.seconds() = SecondStep(this)
fun Long.millis() = MillisecondStep(this)