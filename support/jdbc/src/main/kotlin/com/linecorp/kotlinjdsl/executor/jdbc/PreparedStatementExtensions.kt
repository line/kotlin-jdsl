package com.linecorp.kotlinjdsl.executor.jdbc

import java.sql.PreparedStatement
import java.sql.Types

internal fun PreparedStatement.setNull(paramIndex: Int) {
    val databaseMetaData = this.connection.metaData
    val jdbcDriverName = databaseMetaData.driverName
    val databaseProductName = databaseMetaData.databaseProductName

    if (databaseProductName.startsWith("Informix")
        || jdbcDriverName.startsWith("Microsoft") && jdbcDriverName.contains("SQL Server")
    ) {
        // "Microsoft SQL Server JDBC Driver 3.0" versus "Microsoft JDBC Driver 4.0 for SQL Server"
        setObject(paramIndex, null)
    } else if (databaseProductName.startsWith("DB2")
        || jdbcDriverName.startsWith("jConnect")
        || jdbcDriverName.startsWith("SQLServer")
        || jdbcDriverName.startsWith("Apache Derby")
    ) {
        setNull(paramIndex, Types.VARCHAR)
    } else {
        setNull(paramIndex, Types.NULL)
    }
}
