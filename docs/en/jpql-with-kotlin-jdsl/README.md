# JPQL with Kotlin JDSL

## Requirements

Java 8 (or later) and Kotlin 1.9 (or later) are required to build and run an application with Kotlin JDSL.

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

To get access to snapshots of Kotlin JDSL, you need to declare the OSS snapshot repository:

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

Every Kotlin JDSL application requires at least the following dependencies:

- jpql-dsl: contains a DSL to build a JPQL query.
- jpql-render: renders the JPQL query created with the DSL as String.

{% tabs %}

{% tab title="Gradle (Kotlin)" %}

```kotlin
dependencies {
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.0.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.0.0")
}
```

{% endtab %}

{% tab title="Gradle (Groovy)" %}

```groovy
dependencies {
    implementation 'com.linecorp.kotlin-jdsl:jpql-dsl:3.0.0'
    implementation 'com.linecorp.kotlin-jdsl:jpql-render:3.0.0'
}
```

{% endtab %}

{% tab title="Maven" %}

```xml

<dependencies>
    <dependency>
        <groupId>com.linecorp.kotlin-jdsl</groupId>
        <artifactId>jpql-dsl</artifactId>
        <version>3.0.0</version>
    </dependency>
    <dependency>
        <groupId>com.linecorp.kotlin-jdsl</groupId>
        <artifactId>jpql-render</artifactId>
        <version>3.0.0</version>
    </dependency>
</dependencies>
```

{% endtab %}

{% endtabs %}

### Support dependencies

Adding the Support dependency allows you to execute the query you created with the DSL.
For each JPA provider, Kotlin JDSL provides the following dependencies:

- hibernate-support: helps you execute the query with Hibernate.
- eclipselink-support: helps you execute the query with EclipseLink.
- spring-batch-support: helps you execute the query with Spring Batch.
- spring-data-jpa-support: helps you execute the query with Spring Data JPA.

#### Javax

For the javax, Kotlin JDSL also provides the following dependencies:

- hibernate-javax-support: helps you execute the query with Hibernate.
- eclipselink-javax-support: helps you execute the query with EclipseLink.
- spring-batch-javax-support: helps you execute the query with Spring Batch.
- spring-data-jpa-javax-support: helps you execute the query with Spring Data JPA.

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

Similarly, Kotlin JDSL provides functions for all the other statements: [update statement](statements.md#update-statement), [delete statement](statements.md#delete-statement).
You can also see more [examples](https://github.com/line/kotlin-jdsl/tree/main/example) on GitHub.

In addition, you can also create your own [custom DSL](custom-dsl.md)

## Execute the query

After building the query, you can use `RenderContext` to execute the query.
For example, you can use `JpqlRenderContext` to execute the query:

```kotlin
val context = JpqlRenderContext()

val jpaQuery: Query = entityManager.createQuery(query, context)

val result = jpaQuery.resultList
```

`RenderContext` has elements for rendering the query as String.
Kotlin JDSL provides `JpqlRenderContext` as the default `RenderContext` for the JPQL.

Creating `RenderContext` is expensive, so the Kotlin JDSL recommends creating it once and reusing it.
Since `RenderContext` is immutable, you can access `RenderContext` from multiple threads.
