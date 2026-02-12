package com.test.app.common.support

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*
import java.util.regex.Pattern
import kotlin.math.floor

object Utils {

    fun inList(list: List<String>): String = list.joinToString("','", "['", "']" )

    /**
     * 백분위 수 계산, 소수점 한자리 까지.
     */
    fun percentile(value: Double?, total: Double?): Double {
        if (value == null || value == 0.0 || total == null || total == 0.0)
            return 0.0

        return floor(value / total * 1000) / 10.0
    }

    fun nvl(value: Double?, defaultValue: Double = 0.0): Double = value ?: defaultValue

    /**
     * 전일자 ETL 완료 시간이 9시 이다.
     * 기준 일자는 9시간 전으로 한다.
     */
    fun stdDate(): String {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDateTime.now().minusHours(9).format(formatter)
    }

    private val pattern: Pattern = Pattern.compile("(^[가-힣]+)$") // 한글만 (영어, 숫자 포함 이름 제외)

    fun maskingName(name: String?): String? {
        if (name == null || !pattern.matcher(name).find()) {
            return name
        }

        val length: Int = name.length
        val middleMask = if (length > 2) {
            name.substring(1, length - 1)
        } else { // 이름 외자
            name.substring(1, length)
        }

        val mask: String = "*".repeat(middleMask.length)

        return if (length > 2) {
            name.substring(0, 1) + mask + name.substring(length - 1, length)
        } else { // 이름 외자
            name.substring(0, 1) + mask
        }
    }

    fun getLast10Weeks(): List<String> {
        val currentDate = LocalDate.now()
        val weekFields = WeekFields.of(Locale.getDefault())

        return (1..10).map { i -> // 1부터 시작해서 오늘 포함 안 함
            val pastDate = currentDate.minusWeeks(i.toLong()) // i주 전 날짜 가져오기
            val month = pastDate.monthValue
            val weekOfMonth = pastDate.get(weekFields.weekOfMonth())
            "${month}월 ${weekOfMonth}주"
        }.reversed()
    }

}
