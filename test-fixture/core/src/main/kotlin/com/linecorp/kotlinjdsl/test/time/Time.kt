package com.linecorp.kotlinjdsl.test.time

import java.sql.Timestamp
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalUnit
import java.util.*

class Time internal constructor(
    private var time: ZonedDateTime
) : Comparable<Time> {

    private constructor(time: ZonedDateTime, zoneId: ZoneId) : this(time.withZoneSameInstant(zoneId))

    companion object {
        private val formatterMap = mutableMapOf(
            "ISO_8601" to DateTimeFormatter.ISO_INSTANT,
            "ISO8601" to DateTimeFormatter.ISO_INSTANT,
            "iso_8601" to DateTimeFormatter.ISO_INSTANT,
            "iso8601" to DateTimeFormatter.ISO_INSTANT,
        )

        private val mockedNow: ThreadLocal<ZonedDateTime> = ThreadLocal()

        fun startMocking(time: Time) {
            mockedNow.set(time.toZonedDateTime())
        }

        fun finishMocking() {
            mockedNow.remove()
        }

        fun now(): Time =
            mockedNow.get()?.let { Time(it) } ?: Time(ZonedDateTime.now())

        fun of(timestamp: Timestamp): Time {
            return Time(ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.systemDefault()))
        }

        fun of(zonedDateTime: ZonedDateTime): Time {
            return Time(zonedDateTime)
        }

        fun of(time: String): Time {
            return try {
                Time(
                    ZonedDateTime.of(
                        LocalDate.parse(time, getFormatter("yyyy-MM-dd")),
                        LocalTime.MIN,
                        ZoneId.systemDefault()
                    )
                )
            } catch (e: Exception) {
                Time(
                    ZonedDateTime.of(
                        LocalDateTime.parse(time, getFormatter("yyyy-MM-dd HH:mm:ss")),
                        ZoneId.systemDefault()
                    )
                )
            }
        }

        fun of(time: String, format: String): Time {
            return of(time, getFormatter(format), ZoneId.systemDefault())
        }

        fun of(time: String, zoneId: ZoneId): Time {
            return try {
                Time(ZonedDateTime.of(LocalDate.parse(time, getFormatter("yyyy-MM-dd")), LocalTime.MIN, zoneId), zoneId)
            } catch (e: Exception) {
                Time(ZonedDateTime.of(LocalDateTime.parse(time, getFormatter("yyyy-MM-dd HH:mm:ss")), zoneId), zoneId)
            }
        }

        fun of(time: String, format: String, zoneId: ZoneId): Time {
            return of(time, getFormatter(format), zoneId)
        }

        private fun of(time: String, formatter: DateTimeFormatter, zoneId: ZoneId): Time {
            return Time(ZonedDateTime.of(LocalDateTime.parse(time, formatter), zoneId), zoneId)
        }

        fun of(date: Date): Time {
            return Time(ZonedDateTime.ofInstant(date.toInstant(), TimeZone.getDefault().toZoneId()))
        }

        private fun getFormatter(format: String): DateTimeFormatter {
            return formatterMap.computeIfAbsent(format) {
                DateTimeFormatterBuilder().appendPattern(format)
                    .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                    .toFormatter()
            }
        }
    }

    fun withZoneId(zoneId: ZoneId): Time {
        return of(time.withZoneSameInstant(zoneId))
    }

    fun toTimestamp(): Timestamp {
        return Timestamp.from(time.toInstant())
    }

    fun toZonedDateTime(): ZonedDateTime {
        return time
    }

    fun toDate(): Date {
        return Date.from(time.toInstant())
    }

    operator fun plus(duration: Duration): Time =
        Time(time.plus(duration))

    operator fun plus(step: TimeStep): Time =
        Time(step.plus(time))

    fun plus(amount: Long, unit: TemporalUnit): Time =
        Time(time.plus(amount, unit))

    operator fun minus(step: TimeStep): Time =
        Time(step.minus(time))

    operator fun minus(other: Time): Duration {
        return Duration.between(time, other.toZonedDateTime())
    }

    fun truncatedToDays() =
        of(time.truncatedTo(ChronoUnit.DAYS))

    fun truncatedToHours() =
        of(time.truncatedTo(ChronoUnit.HOURS))

    fun truncatedToMinutes() =
        of(time.truncatedTo(ChronoUnit.MINUTES))

    fun truncatedToSeconds() =
        of(time.truncatedTo(ChronoUnit.SECONDS))

    fun endOfDay() =
        of(time.with(LocalTime.MAX))

    operator fun rangeTo(other: Time): TimeRange =
        TimeRange(this, other.withZoneId(time.zone))

    fun toString(format: String, zoneId: ZoneId): String {
        return toString(getFormatter(format), zoneId)
    }

    fun toString(format: String): String {
        return toString(getFormatter(format), time.zone)
    }

    private fun toString(formatter: DateTimeFormatter, zoneId: ZoneId): String {
        return time.format(formatter.withZone(zoneId))
    }

    override fun compareTo(other: Time): Int {
        return time.toInstant().compareTo(other.time.toInstant())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }

        if (other == null || other !is Time) {
            return false
        }

        return time.toInstant() == other.time.toInstant()
    }

    override fun hashCode(): Int {
        return time.toInstant().hashCode()
    }

    override fun toString(): String {
        return toString(getFormatter("yyyy-MM-dd HH:mm:ss"), time.zone)
    }
}