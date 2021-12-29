package com.linecorp.kotlinjdsl.test.time

import com.linecorp.kotlinjdsl.test.WithKotlinJdslAssertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.sql.Timestamp
import java.time.Duration
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

class TimeTest : WithKotlinJdslAssertions {
    companion object {
        private val utcZone = ZoneOffset.UTC
        private val seoulZone = ZoneId.of("Asia/Seoul")
    }

    @BeforeEach
    fun setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone(utcZone))
    }

    @AfterEach
    fun tearDown() {
        TimeZone.setDefault(null)
    }

    @Test
    fun of() {
        ZoneId.systemDefault()

        var time: Time = Time.of("2020-10-11")
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-10-11 00:00:00")

        time = Time.of("2020-10-11 12:50:59")
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-10-11 12:50:59")

        time = Time.of("2020-10-11", "yyyy-MM-dd")
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-10-11 00:00:00")

        time = Time.of("2020-10-11 12", "yyyy-MM-dd HH")
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-10-11 12:00:00")

        time = Time.of("2020-10-11 12:50:59", "yyyy-MM-dd HH:mm:ss")
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-10-11 12:50:59")

        time = Time.of("2020-10-11 12:50:59", "yyyy-MM-dd HH:mm:ss", seoulZone)
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-10-11 12:50:59")
    }

    @Test
    fun string() {
        val time = Time.of("2020-10-11 12:50:59")

        assertThat(time.toString("yyyyMMdd")).isEqualTo("20201011")
        assertThat(time.toString("yyyy-MM-dd")).isEqualTo("2020-10-11")
        assertThat(time.toString("yyyy.MM")).isEqualTo("2020.10")
        assertThat(time.toString("HH:mm:ss")).isEqualTo("12:50:59")
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-10-11 12:50:59")
        assertThat(time.toString("yyyy.MM.dd HH:mm")).isEqualTo("2020.10.11 12:50")

        assertThat(time.toString("ISO_8601")).isEqualTo("2020-10-11T12:50:59Z")
        assertThat(time.toString("ISO8601")).isEqualTo("2020-10-11T12:50:59Z")
        assertThat(time.toString("iso_8601")).isEqualTo("2020-10-11T12:50:59Z")
        assertThat(time.toString("iso8601")).isEqualTo("2020-10-11T12:50:59Z")
    }

    @Test
    fun `string - with zone id`() {
        val time = Time.of("2020-10-11 16:50:59")

        assertThat(time.toString("yyyyMMdd", seoulZone)).isEqualTo("20201012")
        assertThat(time.toString("yyyy-MM-dd", seoulZone)).isEqualTo("2020-10-12")
        assertThat(time.toString("yyyy.MM", seoulZone)).isEqualTo("2020.10")
        assertThat(time.toString("HH:mm:ss", seoulZone)).isEqualTo("01:50:59")
        assertThat(time.toString("yyyy-MM-dd HH:mm:ss", seoulZone)).isEqualTo("2020-10-12 01:50:59")
        assertThat(time.toString("yyyy.MM.dd HH:mm", seoulZone)).isEqualTo("2020.10.12 01:50")

        assertThat(time.toString("ISO_8601")).isEqualTo("2020-10-11T16:50:59Z")
        assertThat(time.toString("ISO8601")).isEqualTo("2020-10-11T16:50:59Z")
        assertThat(time.toString("iso_8601")).isEqualTo("2020-10-11T16:50:59Z")
        assertThat(time.toString("iso8601")).isEqualTo("2020-10-11T16:50:59Z")
    }

    @Test
    fun date() {
        assertThat(Time.of("2020-10-11 03:50:59").toDate()).isEqualTo(Date(1602388259000))

        assertThat(Time.of(Date(1602388259000))).isEqualTo(Time.of("2020-10-11 03:50:59"))
    }

    @Test
    fun timestamp() {
        assertThat(Time.of("2020-10-11 03:50:59").toTimestamp()).isEqualTo(Timestamp(1602388259000))

        assertThat(Time.of(Timestamp(1602388259000))).isEqualTo(Time.of("2020-10-11 03:50:59"))
    }

    @Test
    fun mocking() {
        Time.startMocking(Time.of("2020-01-01 10:00:00"))

        assertThat(Time.now().toString("yyyy-MM-dd HH:mm:ss")).isEqualTo("2020-01-01 10:00:00")

        Time.finishMocking()

        assertThat(Time.now().toString("yyyy-MM-dd HH:mm:ss")).isNotEqualTo("2020-01-01 10:00:00")
    }

    @Test
    fun withZoneId() {
        assertThat(Time.of("2020-10-02 00:00:00").withZoneId(seoulZone).toString()).isEqualTo("2020-10-02 09:00:00")

        assertThat(Time.of("2020-10-02 00:00:00").withZoneId(utcZone).toString()).isEqualTo("2020-10-02 00:00:00")
    }

    @Test
    fun eq_equality() {
        assertTrue { Time.of("2020-10-02 09:00:00", seoulZone) == Time.of("2020-10-02 00:00:00") }

        assertTrue { Time.of("2020-10-02 09:00:00", seoulZone) == Time.of("2020-10-02 00:00:00") }
    }

    @Test
    fun eq_identity() {
        val time = Time.of("2020-10-01 23:00:00", seoulZone)

        assertTrue { time === time }
    }

    @Test
    fun lt() {
        assertTrue { Time.of("2020-10-01 22:59:59", seoulZone) < Time.of("2020-10-02 00:00:00") }
    }

    @Test
    fun lte() {
        assertTrue { Time.of("2020-10-01 23:00:00", seoulZone) <= Time.of("2020-10-02 00:00:00") }
        assertTrue { Time.of("2020-10-01 22:59:59", seoulZone) <= Time.of("2020-10-02 00:00:00") }
    }

    @Test
    fun gt() {
        assertTrue { Time.of("2020-10-02 09:00:01", seoulZone) > Time.of("2020-10-02 00:00:00") }
    }

    @Test
    fun gte() {
        assertTrue { Time.of("2020-10-02 09:00:00", seoulZone) >= Time.of("2020-10-02 00:00:00") }
        assertTrue { Time.of("2020-10-02 09:00:01", seoulZone) >= Time.of("2020-10-02 00:00:00") }
    }

    @Test
    fun plus() {
        assertThat(Time.of("2020-10-01 23:00:00").plus(3, ChronoUnit.DAYS)).isEqualTo(Time.of("2020-10-04 23:00:00"))

        assertThat(Time.of("2020-10-04 23:00:00").plus(-3, ChronoUnit.DAYS)).isEqualTo(Time.of("2020-10-01 23:00:00"))

        assertThat(Time.of("2020-10-01 23:00:00") + Duration.ofDays(3)).isEqualTo(Time.of("2020-10-04 23:00:00"))

        assertThat(Time.of("2020-10-04 23:00:00") + Duration.ofDays(-3)).isEqualTo(Time.of("2020-10-01 23:00:00"))

        assertThat(Time.of("2020-10-01 23:00:00") + 3.years()).isEqualTo(Time.of("2023-10-01 23:00:00"))

        assertThat(Time.of("2020-10-01 23:00:00") + 3.months()).isEqualTo(Time.of("2021-01-01 23:00:00"))

        assertThat(Time.of("2020-10-29 01:00:00") + 3.days()).isEqualTo(Time.of("2020-11-01 01:00:00"))

        assertThat(Time.of("2020-10-01 23:00:00") + 3.hours()).isEqualTo(Time.of("2020-10-02 02:00:00"))

        assertThat(Time.of("2020-10-01 23:00:00") + 3.minutes()).isEqualTo(Time.of("2020-10-01 23:03:00"))

        assertThat(Time.of("2020-10-01 23:00:00") + 3.seconds()).isEqualTo(Time.of("2020-10-01 23:00:03"))

        assertThat(Time.of("2020-10-01 23:00:00") + 3.millis())
            .isEqualTo(Time.of("2020-10-01 23:00:00").plus(3, ChronoUnit.MILLIS))
    }

    @Test
    fun minus() {
        assertThat(Time.of("2020-10-01 01:00:00") - 3.years()).isEqualTo(Time.of("2017-10-01 01:00:00"))

        assertThat(Time.of("2020-02-01 01:00:00") - 3.months()).isEqualTo(Time.of("2019-11-01 01:00:00"))

        assertThat(Time.of("2020-11-01 01:00:00") - 3.days()).isEqualTo(Time.of("2020-10-29 01:00:00"))

        assertThat(Time.of("2020-11-01 01:00:00") - 3.hours()).isEqualTo(Time.of("2020-10-31 22:00:00"))

        assertThat(Time.of("2020-11-01 00:00:00") - 3.minutes()).isEqualTo(Time.of("2020-10-31 23:57:00"))

        assertThat(Time.of("2020-11-01 00:00:00") - 3.seconds()).isEqualTo(Time.of("2020-10-31 23:59:57"))

        assertThat(Time.of("2020-11-01 00:00:00") - 3.millis())
            .isEqualTo(Time.of("2020-11-01 00:00:00").plus(-3, ChronoUnit.MILLIS))
    }

    @Test
    fun truncatedTo() {
        assertThat(Time.of("2020-10-30 10:10:10").truncatedToDays())
            .isEqualTo(Time.of("2020-10-30 00:00:00"))

        assertThat(Time.of("2020-10-30 10:10:10").truncatedToHours())
            .isEqualTo(Time.of("2020-10-30 10:00:00"))

        assertThat(Time.of("2020-10-30 10:10:10").truncatedToMinutes())
            .isEqualTo(Time.of("2020-10-30 10:10:00"))

        assertThat((Time.of("2020-10-30 10:10:10") + 3.millis()).truncatedToSeconds())
            .isEqualTo(Time.of("2020-10-30 10:10:10"))
    }

    @Test
    fun endOfDay() {
        assertThat(
            Time.of("2020-10-30 10:10:10").endOfDay().toString("yyyy-MM-dd HH:mm:ss")
        ).isEqualTo("2020-10-30 23:59:59")
    }

    @Test
    fun rangeTo() {
        val default = Time.of("2020-10-31 00:00:00")..Time.of("2020-11-02 00:00:00")

        assertThat(default.map { it.toString("yyyy.MM.dd") }).containsOnly("2020.10.31", "2020.11.01", "2020.11.02")

        val years = Time.of("2020-10-02 00:00:00")..Time.of("2026-10-02 00:00:00") step 3.years()

        assertThat(years.map { it.toString("yyyy") }).containsOnly("2020", "2023", "2026")

        val months = Time.of("2020-10-02 00:00:00")..Time.of("2021-04-02 00:00:00") step 3.months()

        assertThat(months.map { it.toString("yyyy.MM") }).containsOnly("2020.10", "2021.01", "2021.04")

        val days = Time.of("2020-10-31 00:00:00")..Time.of("2020-11-06 00:00:00") step 3.days()

        assertThat(days.map { it.toString("yyyy.MM.dd") }).containsOnly("2020.10.31", "2020.11.03", "2020.11.06")

        val hours = Time.of("2020-10-31 21:00:00")..Time.of("2020-11-01 03:00:00") step 3.hours()

        assertThat(hours.map { it.toString("yyyy.MM.dd.HH") }).containsOnly(
            "2020.10.31.21",
            "2020.11.01.00",
            "2020.11.01.03"
        )

        val minutes = Time.of("2020-10-31 23:57:00")..Time.of("2020-11-01 00:03:00") step 3.minutes()

        assertThat(minutes.map { it.toString("yyyy.MM.dd.HH.mm") }).containsOnly(
            "2020.10.31.23.57",
            "2020.11.01.00.00",
            "2020.11.01.00.03"
        )

        val seconds = Time.of("2020-10-31 23:59:57")..Time.of("2020-11-01 00:00:03") step 3.seconds()

        assertThat(seconds.map { it.toString("yyyy.MM.dd.HH.mm.ss") }).containsOnly(
            "2020.10.31.23.59.57",
            "2020.11.01.00.00.00",
            "2020.11.01.00.00.03"
        )

        val from = Time.of("2020-10-31 23:59:59") + 997.millis()
        val to = Time.of("2020-11-01 00:00:00") + 3.millis()

        val milliseconds = from..to step 3.millis()

        assertThat(milliseconds.map { it.toString("yyyy.MM.dd.HH.mm.ss.SSS") }).containsOnly(
            "2020.10.31.23.59.59.997",
            "2020.11.01.00.00.00.000",
            "2020.11.01.00.00.00.003"
        )
    }

    @Test
    fun equalsAndHashcode() {
        val time1 = Time.of("2020-10-31 23:42:12.884", "yyyy-MM-dd HH:mm:ss.SSS", utcZone)
        val time2 = Time.of("2020-11-01 08:42:12.884", "yyyy-MM-dd HH:mm:ss.SSS", seoulZone)

        assertTrue { time1 == time2 }
        assertTrue { time1.hashCode() == time2.hashCode() }

        assertFalse {
            @Suppress("SENSELESS_COMPARISON")
            time1 == null
        }
    }
}