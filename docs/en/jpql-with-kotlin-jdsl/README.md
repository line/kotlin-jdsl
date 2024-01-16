# JPQL with Kotlin JDSL

## Requirements

At least Java 8 and Kotlin 1.9 are required to build and run an application with Kotlin JDSL.

## Configure the repositories

Before adding Kotlin JDSL dependencies, you need to configure the repositories for this project:

### Release

Releases of Kotlin JDSL are available in the [Maven central repository](https://central.sonatype.com/search?q=g%3Acom.linecorp.kotlin-jdsl).
You can declare this repository in your build script as follows:

{% tabs %}

{% tab title="Gradle (Kotlin)" %}

```kotlin
repositories {
    mavenCentral()
}
```

{% endtab %}

{% tab title="Gradle (Groovy)" %}

```groovy
repositories {
    mavenCentral()
}
```

{% endtab %}

{% tab title="Maven" %}
{% hint style="info" %}
You don't need to add the Maven central repository in the pom.xml file since your project inherits the central repository from [Super POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html#super-pom).
{% endhint %}
{% endtab %}

{% endtabs %}

### Snapshot

Declare the OSS snapshot repository to get access to snapshots of Kotlin JDSL:

{% tabs %}

{% tab title="Gradle (Kotlin)" %}

```kotlin
repositories {
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
}
```

{% endtab %}

{% tab title="Gradle (Groovy)" %}

```groovy
repositories {
    maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
}
```

{% endtab %}

{% tab title="Maven" %}

```xml
<repositories>
    <repository>
        <id>oss.sonatype.org-snapshot</id>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    </repository>
</repositories>
```

{% endtab %}

{% endtabs %}

## Add Kotlin JDSL dependencies

### Core dependencies

The following dependencies are the minimum requirement for all Kotlin JDSL applications:

- jpql-dsl: Contain a DSL to build a JPQL query
- jpql-render: Render the JPQL query created with the DSL as String

{% tabs %}

{% tab title="Gradle (Kotlin)" %}

```kotlin
dependencies {
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.3.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.3.0")
}
```

{% endtab %}

{% tab title="Gradle (Groovy)" %}

```groovy
dependencies {
    implementation 'com.linecorp.kotlin-jdsl:jpql-dsl:3.3.0'
    implementation 'com.linecorp.kotlin-jdsl:jpql-render:3.3.0'
}
```

{% endtab %}

{% tab title="Maven" %}

```xml

<dependencies>
    <dependency>
        <groupId>com.linecorp.kotlin-jdsl</groupId>
        <artifactId>jpql-dsl</artifactId>
        <version>3.3.0</version>
    </dependency>
    <dependency>
        <groupId>com.linecorp.kotlin-jdsl</groupId>
        <artifactId>jpql-render</artifactId>
        <version>3.3.0</version>
    </dependency>
</dependencies>
```

{% endtab %}

{% endtabs %}

### Support dependencies

Add the Support dependencies to execute the query created with the DSL.
For each JPA provider, Kotlin JDSL provides the following dependencies:

- hibernate-support: Assist to execute the query with Hibernate
- eclipselink-support: Assist to execute the query with EclipseLink
- spring-batch-support: Assist to execute the query with Spring Batch
- spring-data-jpa-support: Assist to execute the query with Spring Data JPA
- hibernate-reactive-support: Assist to execute the query with Hibernate Reactive

#### Javax

For the javax, Kotlin JDSL also provides the following dependencies:

- hibernate-javax-support: Assist to execute the query with Hibernate
- eclipselink-javax-support: Assist to execute the query with EclipseLink
- spring-batch-javax-support: Assist to execute the query with Spring Batch.
- spring-data-jpa-javax-support: Assist to execute the query with Spring Data JPA.
- hibernate-reactive-javax-support: Assist to execute the query with Hibernate Reactive

## Build a query

You can call `select()` in `jpql()` to build a [select statement](statements.md#select-statement):

```kotlin
val query = jpql {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class)
    )
}
```

Similarly, Kotlin JDSL provides functions for all the other statements: [update statement](statements.md#update-statement), and [delete statement](statements.md#delete-statement).
You can also see more [examples](https://github.com/line/kotlin-jdsl/tree/main/example) on GitHub.

In addition, you can also create your own [custom DSL](custom-dsl.md)

## Execute the query

After building the query, you can use `RenderContext` to execute the query.
For example, you can use `JpqlRenderContext` to execute the query:

```kotlin
val context = JpqlRenderContext()

val renderer = JpqlRenderer()

val rendered = renderer.render(query, context)

val jpaQuery: Query = entityManager.createQuery(rendered.query).apply {
    rendered.params.forEach { (name, value) ->
        setParameter(name, value)
    }
}

val result = jpaQuery.resultList
```

`RenderContext` has elements for rendering the query as String.
Kotlin JDSL provides `JpqlRenderContext` as the default `RenderContext` for the JPQL.

`JpqlRenderer` renders the query as String using the `RenderContext`.
This returns `JpqlRendered`, which has the `query` rendered as String and the `parameters` contained in the query.
This has no state, so you can reuse the object of this and access it from multiple threads.

{% hint style="info" %}
Creating `RenderContext` is expensive, so the Kotlin JDSL recommends creating it once and reusing it afterward.
Since `RenderContext` is immutable, you can access `RenderContext` from multiple threads.
{% endhint %}

{% hint style="info" %}
[Kotlin JDSL Support](#support-dependencies) provides extension functions for `EntityManager` to simplify the above process.
Using them, you can execute queries as simple as:

```kotlin
val context = JpqlRenderContext()

val jpaQuery: Query = entityManager.createQuery(query, context)

val result = jpaQuery.resultList
```
{% endhint %}
