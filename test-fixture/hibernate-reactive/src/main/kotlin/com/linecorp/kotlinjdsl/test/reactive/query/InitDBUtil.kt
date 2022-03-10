package com.linecorp.kotlinjdsl.test.reactive.query

import com.linecorp.kotlinjdsl.test.reactive.StageSessionFactoryExtension
import org.hibernate.cfg.AvailableSettings
import org.hibernate.jpa.HibernatePersistenceProvider
import org.hibernate.jpa.boot.internal.PersistenceXmlParser
import org.hibernate.jpa.boot.spi.Bootstrap
import org.hibernate.reactive.stage.Stage
import javax.persistence.Persistence


fun initFactory(): Stage.SessionFactory {
    val persistenceUnitName = "order"
    val unit =
        PersistenceXmlParser.locatePersistenceUnits(emptyMap<String, String>()).first { it.name == persistenceUnitName }
    unit.providerClassName = HibernatePersistenceProvider::class.qualifiedName

    // hibernate-reactive does not support h2 db officially.
    // So, we initialize the table by using the schema generation function of h2 db in synchronous method existing in hibernate-core.
    // The reason is that the create-drop function is not a core function of our library.
    Bootstrap.getEntityManagerFactoryBuilder(
        unit,
        mapOf<Any, Any>(AvailableSettings.HBM2DDL_AUTO to "create-drop"),
        (StageSessionFactoryExtension::class as Any).javaClass.classLoader
    ).build()
    return Persistence.createEntityManagerFactory(persistenceUnitName)
        .unwrap(Stage.SessionFactory::class.java)
}
