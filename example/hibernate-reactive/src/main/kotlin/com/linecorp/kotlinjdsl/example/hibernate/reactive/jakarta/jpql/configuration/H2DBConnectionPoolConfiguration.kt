package com.linecorp.kotlinjdsl.example.hibernate.reactive.jakarta.jpql.configuration

import io.vertx.sqlclient.SqlConnectOptions
import org.hibernate.internal.util.config.ConfigurationHelper
import org.hibernate.reactive.pool.impl.DefaultSqlClientPoolConfiguration
import org.hibernate.reactive.provider.Settings
import java.net.URI

class H2DBConnectionPoolConfiguration : DefaultSqlClientPoolConfiguration() {
    private lateinit var user: String
    private lateinit var password: String

    override fun configure(configuration: MutableMap<Any?, Any?>) {
        user = ConfigurationHelper.getString(Settings.JAKARTA_JDBC_USER, configuration)
        password = ConfigurationHelper.getString(Settings.JAKARTA_JDBC_PASSWORD, configuration)

        super.configure(configuration)
    }

    override fun connectOptions(uri: URI): SqlConnectOptions {
        if (uri.scheme != "h2") {
            return super.connectOptions(uri)
        }

        return SqlConnectOptions()
            .setHost("jdbc:$uri")
            .setUser(user)
            .setPassword(password)
    }
}
