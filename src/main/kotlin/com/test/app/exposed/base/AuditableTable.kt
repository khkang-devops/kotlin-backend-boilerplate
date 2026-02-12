package com.test.app.exposed.base

import com.test.app.config.security.auth.UserPrincipalHolder
import org.jetbrains.exposed.v1.core.Op
import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.statements.InsertStatement
import org.jetbrains.exposed.v1.core.statements.UpdateBuilder
import org.jetbrains.exposed.v1.core.statements.UpdateStatement
import org.jetbrains.exposed.v1.javatime.datetime
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.update
import java.time.LocalDateTime

open class AuditableTable(name: String) : Table(name) {
    val createDt = datetime("create_dt")
    val modifyDt = datetime("modify_dt").nullable()
    val createId = varchar("create_id", 50)
    val modifyId = varchar("modify_id", 50).nullable()
}

inline fun <T : AuditableTable> T.insertWithAudit(
    crossinline body: T.(UpdateBuilder<*>) -> Unit
): InsertStatement<Number> = this.insert {
    it[createDt] = LocalDateTime.now()
    it[this.createId] = UserPrincipalHolder.getUsername()
    this.body(it)
}

inline fun <T : AuditableTable> T.updateWithAudit(
    noinline where: () -> Op<Boolean>,
    crossinline body: T.(UpdateStatement) -> Unit
): Int = this.update(where = where, limit = null) {
    it[modifyDt] = LocalDateTime.now()
    it[this.modifyId] = UserPrincipalHolder.getUsername()
    body(it)
}