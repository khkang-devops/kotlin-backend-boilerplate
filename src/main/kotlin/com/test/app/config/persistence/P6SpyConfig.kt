package com.test.app.config.persistence

import com.github.vertical_blank.sqlformatter.SqlFormatter
import com.p6spy.engine.logging.Category
import com.p6spy.engine.logging.P6LogOptions
import com.p6spy.engine.spy.P6SpyOptions
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Profile(value = ["dev", "prd"])
@Configuration
class P6SpyConfig {

    @PostConstruct
    fun init() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6SpyLineFormat::class.java.name
    }
}

@Profile(value = ["local"])
@Configuration
class P6SpyConfigLocal {

    @PostConstruct
    fun init() {
        P6SpyOptions.getActiveInstance().logMessageFormat = P6SpyPrettyFormat::class.java.name
    }
}

class P6SpyLineFormat : MessageFormattingStrategy {

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?
    ): String =
        if (elapsed == 0L && Category.BATCH.name.equals(category)) {
            "[$category] | 0 ms"
        } else {
            "[$category] | $elapsed ms $sql"
        }
}

class P6SpyPrettyFormat : MessageFormattingStrategy {

    override fun formatMessage(
        connectionId: Int,
        now: String?,
        elapsed: Long,
        category: String?,
        prepared: String?,
        sql: String?,
        url: String?
    ): String = run {
        if (elapsed == 0L && Category.BATCH.name.equals(category)) {
            // add batch 모드 여서 그런지, 2번 로그가 기독되어 예외 처리 한다.
            "[$category] | 0 ms | $connectionId"
        } else {
            "[$category] | $elapsed ms |\n${formatSql(category, sql)}"
        }
    }

    private fun formatSql(category: String?, sql: String?): String {
        val trim = sql.orEmpty().trim()
        if (Category.STATEMENT.name.equals(category) || Category.BATCH.name.equals(category)) {
            return highlight(SqlFormatter.format(trim))
        }
        return trim
    }

    // hibernate Formatter -> SqlFormatter 사용으로 인한 highlight 기능 구현
    private fun highlight(sql: String): String = sql
        // 문자열
        .replace("(?<=')(.*?)(?=')".toRegex()) {
            "\u001B[36m${it.value}\u001B[0m"
        }

        // DML
        .replace("(?i)\\bSELECT\\b".toRegex(), "\u001B[34mSELECT\u001B[0m")
        .replace("(?i)\\bINSERT\\b".toRegex(), "\u001B[34mINSERT\u001B[0m")
        .replace("(?i)\\bINTO\\b".toRegex(), "\u001B[34mINTO\u001B[0m")
        .replace("(?i)\\bVALUES\\b".toRegex(), "\u001B[34mVALUES\u001B[0m")
        .replace("(?i)\\bUPDATE\\b".toRegex(), "\u001B[34mUPDATE\u001B[0m")
        .replace("(?i)\\bSET\\b".toRegex(), "\u001B[34mSET\u001B[0m")
        .replace("(?i)\\bDELETE\\b".toRegex(), "\u001B[34mDELETE\u001B[0m")

        // 조건절
        .replace("(?i)\\bFROM\\b".toRegex(), "\u001B[32mFROM\u001B[0m")
        .replace("(?i)\\bWHERE\\b".toRegex(), "\u001B[32mWHERE\u001B[0m")
        .replace("(?i)\\bAND\\b".toRegex(), "\u001B[32mAND\u001B[0m")
        .replace("(?i)\\bOR\\b".toRegex(), "\u001B[32mOR\u001B[0m")
        .replace("(?i)\\bNOT\\b".toRegex(), "\u001B[32mNOT\u001B[0m")
        .replace("(?i)\\bIN\\b".toRegex(), "\u001B[32mIN\u001B[0m")
        .replace("(?i)\\bIS\\b".toRegex(), "\u001B[32mIS\u001B[0m")
        .replace("(?i)\\bNULL\\b".toRegex(), "\u001B[32mNULL\u001B[0m")

        // JOIN
        .replace("(?i)\\bJOIN\\b".toRegex(), "\u001B[35mJOIN\u001B[0m")
        .replace("(?i)\\bLEFT JOIN\\b".toRegex(), "\u001B[35mLEFT JOIN\u001B[0m")
        .replace("(?i)\\bRIGHT JOIN\\b".toRegex(), "\u001B[35mRIGHT JOIN\u001B[0m")
        .replace("(?i)\\bINNER JOIN\\b".toRegex(), "\u001B[35mINNER JOIN\u001B[0m")
        .replace("(?i)\\bON\\b".toRegex(), "\u001B[35mON\u001B[0m")

        // GROUP / ORDER
        .replace("(?i)\\bGROUP BY\\b".toRegex(), "\u001B[33mGROUP BY\u001B[0m")
        .replace("(?i)\\bORDER BY\\b".toRegex(), "\u001B[33mORDER BY\u001B[0m")
        .replace("(?i)\\bLIMIT\\b".toRegex(), "\u001B[33mLIMIT\u001B[0m")
        .replace("(?i)\\bOFFSET\\b".toRegex(), "\u001B[33mOFFSET\u001B[0m")

        // DDL
        .replace("(?i)\\bCREATE\\b".toRegex(), "\u001B[31mCREATE\u001B[0m")
        .replace("(?i)\\bALTER\\b".toRegex(), "\u001B[31mALTER\u001B[0m")
        .replace("(?i)\\bDROP\\b".toRegex(), "\u001B[31mDROP\u001B[0m")
        .replace("(?i)\\bTABLE\\b".toRegex(), "\u001B[31mTABLE\u001B[0m")
        .replace("(?i)\\bINDEX\\b".toRegex(), "\u001B[31mINDEX\u001B[0m")
        .replace("(?i)\\bCONSTRAINT\\b".toRegex(), "\u001B[31mCONSTRAINT\u001B[0m")

        // 기타
        .replace("(?i)\\bAS\\b".toRegex(), "\u001B[36mAS\u001B[0m")
        .replace("(?i)\\bDISTINCT\\b".toRegex(), "\u001B[36mDISTINCT\u001B[0m")

        .replace("(?i)\\bCASE\\b".toRegex(), "\u001B[32mCASE\u001B[0m")
        .replace("(?i)\\bWHEN\\b".toRegex(), "\u001B[32mWHEN\u001B[0m")
        .replace("(?i)\\bTHEN\\b".toRegex(), "\u001B[32mTHEN\u001B[0m")
        .replace("(?i)\\bELSE\\b".toRegex(), "\u001B[32mELSE\u001B[0m")
        .replace("(?i)\\bEND\\b".toRegex(), "\u001B[32mEND\u001B[0m")

        .replace("(?i)\\bLIKE\\b".toRegex(), "\u001B[32mLIKE\u001B[0m")
        .replace("(?i)\\bILIKE\\b".toRegex(), "\u001B[32mILIKE\u001B[0m")
        .replace("(?i)\\bBETWEEN\\b".toRegex(), "\u001B[32mBETWEEN\u001B[0m")
        .replace("(?i)\\bEXISTS\\b".toRegex(), "\u001B[35mEXISTS\u001B[0m")
        .replace("(?i)\\bANY\\b".toRegex(), "\u001B[35mANY\u001B[0m")
        .replace("(?i)\\bALL\\b".toRegex(), "\u001B[35mALL\u001B[0m")

        .replace("(?i)\\bCAST\\b".toRegex(), "\u001B[36mCAST\u001B[0m")
        .replace("(?i)\\bCOALESCE\\b".toRegex(), "\u001B[36mCOALESCE\u001B[0m")
        .replace("(?i)\\bCONCAT\\b".toRegex(), "\u001B[36mCONCAT\u001B[0m")
        .replace("(?i)\\bTRIM\\b".toRegex(), "\u001B[36mTRIM\u001B[0m")
        .replace("(?i)\\bUPPER\\b".toRegex(), "\u001B[36mUPPER\u001B[0m")
        .replace("(?i)\\bLOWER\\b".toRegex(), "\u001B[36mLOWER\u001B[0m")

        .replace("(?i)\\bNOW\\b".toRegex(), "\u001B[36mNOW\u001B[0m")
        .replace("(?i)\\bCURRENT_DATE\\b".toRegex(), "\u001B[36mCURRENT_DATE\u001B[0m")
        .replace("(?i)\\bCURRENT_TIMESTAMP\\b".toRegex(), "\u001B[36mCURRENT_TIMESTAMP\u001B[0m")

        .replace("(?i)\\bCOUNT\\b".toRegex(), "\u001B[36mCOUNT\u001B[0m")
        .replace("(?i)\\bSUM\\b".toRegex(), "\u001B[36mSUM\u001B[0m")
        .replace("(?i)\\bAVG\\b".toRegex(), "\u001B[36mAVG\u001B[0m")
        .replace("(?i)\\bMIN\\b".toRegex(), "\u001B[36mMIN\u001B[0m")
        .replace("(?i)\\bMAX\\b".toRegex(), "\u001B[36mMAX\u001B[0m")
}


@Configuration
class P6LogOptionsConfig(
    @Value("\${decorator.datasource.p6spy.enable-logging:false}")
    private val loggingEnable: Boolean,
) {

    @PostConstruct
    fun init() {

        if (!loggingEnable)
            return

        /**
         * ref. https://p6spy.readthedocs.io/en/latest/configandusage.html
         * 일부 값들은 properties 로 빼도 될꺼 같은데, 운영기 에서는 로깅을 꺼야 할꺼니... 구찮타..
         */
        val excludeCategories = listOf(
            "info", "debug", "result", "resultset",
        ).joinToString(",")
        val excludeFilter = listOf("").joinToString(",")

        P6LogOptions.getActiveInstance().let {
            it.excludecategories = excludeCategories
            it.filter = true
            it.exclude = excludeFilter
        }
    }
}
