package com.test.app.config.gcp

import io.zeko.db.sql.CustomPart
import io.zeko.db.sql.Query
import io.zeko.db.sql.QueryBlock


/**
 * zeko Query 에 limit 은 limit + offset 이다.
 * BigQuery 는 offset 개념이 없어서 확장하도록 한다.
 */
fun Query.limits(limit: Int): Query {
    this.addExpressionAfter(CustomPart.WHERE, QueryBlock("LIMIT $limit"))
    return this
}

fun Query.using(block: String): Query {
    this.addExpressionAfter(CustomPart.JOIN, QueryBlock("USING ($block)"))
    return this
}

fun Query.unionDistinct(query: Query): Query {
    this.addExpressionAfter(CustomPart.WHERE, QueryBlock("UNION DISTINCT (", query.toSql(), ")"))
    return this
}

fun Query.intersectDistinct(query: Query): Query {
    this.addExpressionAfter(CustomPart.WHERE, QueryBlock("INTERSECT DISTINCT (", query.toSql(), ")"))
    return this
}


/**
 * io.zeko.db.sql.dsl.decrlarations.kt 내용을 가져왔다.
 * BigQuery는 파라미터 라이즈 문법이 달라서, 그냥 값으로 사용하도록 한다.
 * import io.zeko.db.sql.dsl.* 를 사용하지 말고 아래 를 사용하도록 한다.
 * String 타입은 따음표 로 감싸줬다.
 */
infix fun String.eq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.eq(this, "'$value'", true)
}

infix fun String.eq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.eq(this, value)
}

infix fun String.eq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.eq(this, value)
}

infix fun String.eq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.eq(this, value)
}

infix fun String.eq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.eq(this, value.toString(), true)
}

infix fun String.neq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.neq(this, value, true)
}

infix fun String.neq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.neq(this, value)
}

infix fun String.neq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.neq(this, value)
}

infix fun String.neq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.neq(this, value)
}

infix fun String.neq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.neq(this, value)
}

infix fun String.greater(value: String): QueryBlock {
    return io.zeko.db.sql.operators.greater(this, value)
}

infix fun String.greaterEq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this, value)
}

infix fun String.less(value: String): QueryBlock {
    return io.zeko.db.sql.operators.less(this, value)
}

infix fun String.lessEq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this, value)
}

infix fun String.greater(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.greater(this, value)
}

infix fun String.greaterEq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this, value)
}

infix fun String.less(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.less(this, value)
}

infix fun String.lessEq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this, value)
}

infix fun String.greater(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.greater(this, value)
}

infix fun String.greaterEq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this, value)
}

infix fun String.less(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.less(this, value)
}

infix fun String.lessEq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this, value)
}

infix fun String.greater(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.greater(this, value)
}

infix fun String.greaterEq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this, value)
}

infix fun String.less(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.less(this, value)
}

infix fun String.lessEq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this, value)
}

infix fun String.greater(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.greater(this, value)
}

infix fun String.greaterEq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this, value)
}

infix fun String.less(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.less(this, value)
}

infix fun String.lessEq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this, value)
}

infix fun QueryBlock.eq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.eq(this.toString(), value, true)
}

infix fun QueryBlock.eq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.eq(this.toString(), value)
}

infix fun QueryBlock.eq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.eq(this.toString(), value)
}

infix fun QueryBlock.eq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.eq(this.toString(), value)
}

infix fun QueryBlock.eq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.eq(this.toString(), value)
}

infix fun QueryBlock.neq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.neq(this.toString(), value, true)
}

infix fun QueryBlock.neq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.neq(this.toString(), value)
}

infix fun QueryBlock.neq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.neq(this.toString(), value)
}

infix fun QueryBlock.neq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.neq(this.toString(), value)
}

infix fun QueryBlock.neq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.neq(this.toString(), value)
}

infix fun QueryBlock.greater(value: String): QueryBlock {
    return io.zeko.db.sql.operators.greater(this.toString(), value)
}

infix fun QueryBlock.greaterEq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this.toString(), value)
}

infix fun QueryBlock.less(value: String): QueryBlock {
    return io.zeko.db.sql.operators.less(this.toString(), value)
}

infix fun QueryBlock.lessEq(value: String): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this.toString(), value)
}

infix fun QueryBlock.greater(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.greater(this.toString(), value)
}

infix fun QueryBlock.greaterEq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this.toString(), value)
}

infix fun QueryBlock.less(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.less(this.toString(), value)
}

infix fun QueryBlock.lessEq(value: Int): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this.toString(), value)
}

infix fun QueryBlock.greater(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.greater(this.toString(), value)
}

infix fun QueryBlock.greaterEq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this.toString(), value)
}

infix fun QueryBlock.less(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.less(this.toString(), value)
}

infix fun QueryBlock.lessEq(value: Long): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this.toString(), value)
}

infix fun QueryBlock.greater(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.greater(this.toString(), value)
}

infix fun QueryBlock.greaterEq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this.toString(), value)
}

infix fun QueryBlock.less(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.less(this.toString(), value)
}

infix fun QueryBlock.lessEq(value: Double): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this.toString(), value)
}

infix fun QueryBlock.greater(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.greater(this.toString(), value)
}

infix fun QueryBlock.greaterEq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.greaterEq(this.toString(), value)
}

infix fun QueryBlock.less(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.less(this.toString(), value)
}

infix fun QueryBlock.lessEq(value: Any): QueryBlock {
    return io.zeko.db.sql.operators.lessEq(this.toString(), value)
}

infix fun String.like(value: String): QueryBlock {
    return io.zeko.db.sql.operators.like(this, value, true)
}

infix fun String.notLike(value: String): QueryBlock {
    return io.zeko.db.sql.operators.notLike(this, value, true)
}

infix fun String.regexp(value: String): QueryBlock {
    return io.zeko.db.sql.operators.regexp(this, value)
}

infix fun String.notRegexp(value: String): QueryBlock {
    return io.zeko.db.sql.operators.notRegexp(this, value)
}

infix fun String.match(value: String): QueryBlock {
    return io.zeko.db.sql.operators.match(this, value)
}

infix fun List<String>.match(value: String): QueryBlock {
    return io.zeko.db.sql.operators.match(this, value)
}

infix fun String.isNotNull(value: Boolean): QueryBlock {
    if (value) {
        return io.zeko.db.sql.operators.isNotNull(this)
    }
    return QueryBlock("", "")
}

infix fun String.isNull(value: Boolean): QueryBlock {
    if (value) {
        return io.zeko.db.sql.operators.isNull(this)
    }
    return QueryBlock("", "")
}

infix fun String.inList(values: String): QueryBlock {
    return io.zeko.db.sql.operators.inList(this, values)
}

infix fun String.inList(values: List<Any>): QueryBlock {
    return io.zeko.db.sql.operators.inList(this, values, true)
}

infix fun String.inList(values: Array<*>): QueryBlock {
    return io.zeko.db.sql.operators.inList(this, values)
}

infix fun String.inList(valueSize: Int): QueryBlock {
    return io.zeko.db.sql.operators.inList(this, valueSize)
}

infix fun String.notInList(values: String): QueryBlock {
    return io.zeko.db.sql.operators.notInList(this, values)
}

infix fun String.notInList(values: List<Any>): QueryBlock {
    return io.zeko.db.sql.operators.notInList(this, values, true)
}

infix fun String.notInList(values: Array<*>): QueryBlock {
    return io.zeko.db.sql.operators.notInList(this, values)
}

infix fun String.notInList(valueSize: Int): QueryBlock {
    return io.zeko.db.sql.operators.notInList(this, valueSize)
}

infix fun QueryBlock.and(value: QueryBlock): QueryBlock {
    return QueryBlock(this.toString(), " AND ", value.toString())
}

infix fun QueryBlock.or(value: QueryBlock): QueryBlock {
    return QueryBlock(this.toString(), " OR ", value.toString())
}

infix fun String.between(values: Pair<*, *>): QueryBlock {
    val value1 = values.first
    if (value1 is String) {
        val value2 = values.second.toString()
        return io.zeko.db.sql.operators.between(this, "'$value1'", "'$value2'", true)
    } else if (value1 is Int) {
        val value2 = values.second as Int
        return io.zeko.db.sql.operators.between(this, value1, value2)
    } else if (value1 is Long) {
        val value2 = values.second as Long
        return io.zeko.db.sql.operators.between(this, value1, value2)
    } else if (value1 is Double) {
        val value2 = values.second as Double
        return io.zeko.db.sql.operators.between(this, value1, value2)
    } else {
        val value2 = values.second
        return io.zeko.db.sql.operators.between(this, value1.toString(), value2.toString(), true)
    }
}



