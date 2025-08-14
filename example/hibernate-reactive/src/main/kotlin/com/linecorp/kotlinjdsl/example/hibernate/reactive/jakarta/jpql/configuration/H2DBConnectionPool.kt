package com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.configuration

import io.vertx.core.Vertx
import io.vertx.jdbcclient.JDBCConnectOptions
import io.vertx.jdbcclient.JDBCPool
import io.vertx.sqlclient.Pool
import io.vertx.sqlclient.PoolOptions
import io.vertx.sqlclient.SqlConnectOptions
import org.hibernate.reactive.pool.impl.DefaultSqlClientPool
import java.net.URI

class H2DBConnectionPool : DefaultSqlClientPool() {
    override fun <T : SqlConnectOptions> createPool(
        uri: URI,
        connectOptions: T,
        poolOptions: PoolOptions,
        vertx: Vertx,
    ): Pool {
        return JDBCPool.pool(
            vertx,
            JDBCConnectOptions()
                .setJdbcUrl(connectOptions.host)
                .setUser(connectOptions.user)
                .setPassword(connectOptions.password)
                .setDatabase(connectOptions.database),
            poolOptions,
        )
    }
}
