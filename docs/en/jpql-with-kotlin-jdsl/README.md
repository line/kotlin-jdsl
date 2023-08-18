# JPQL with Kotlin JDSL

With Kotlin JDSL, you can easily build and execute JPQL queries.

## Requirements <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

Java 8 (or later) and Kotlin 1.9 (or later) are required to build and run an application with Kotlin JDSL.

## Choosing the artifacts <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

Kotlin JDSL provides several artifacts. The most basic of these are jpql-dsl and jpql-render. By using these two artifacts as a base and adding a support artifact for your favorite libraries, you can easily build and execute queries.

### Basic

<table><thead><tr><th width="251">Artifact ID</th><th>Description</th></tr></thead><tbody><tr><td>jpql-dsl</td><td>Building the JPQL query through DSL.</td></tr><tr><td>jpql-render</td><td>Rendering the JPQL query from DSL as a string.</td></tr></tbody></table>

### Supports

<table><thead><tr><th width="251">Artifact ID</th><th>Description</th></tr></thead><tbody><tr><td>eclipselink-support</td><td>Support to help you use EclipseLink and Kotlin JDSL together.</td></tr><tr><td>eclipselink-javax-support</td><td>Support to help you use EclipseLink and Kotlin JDSL together for javax.</td></tr><tr><td>hibernate-support</td><td>Support to help you use Hibernate and Kotlin JDSL together.</td></tr><tr><td>hibernate-javax-support</td><td>Support to help you use Hibernate and Kotlin JDSL together for javax.</td></tr><tr><td>spring-batch-support</td><td>Support to help you use Spring Batch and Kotlin JDSL together.</td></tr><tr><td>spring-data-jpa-support</td><td>Support to help you use Spring Data Jpa and Kotlin JDSL together.</td></tr><tr><td>spring-data-jpa-javax-support</td><td>Support to help you use Spring Data Jpa 2.X and Kotlin JDSL together.</td></tr></tbody></table>

## Setting up with your build system <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

All JARs of Kotlin JDSL are available in [Maven Central Repository](https://central.sonatype.com/search?q=g%3Acom.linecorp.kotlin-jdsl) under group ID `com.linecorp.kotlin-jdsl` so that you can fetch them easily using your favorite build tool.

{% tabs %}
{% tab title="Gradle (Kotlin)" %}
```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.0.0")
    implementation("com.linecorp.kotlin-jdsl:jpql-render:3.0.0")
    
    implementation("org.eclipse.persistence:org.eclipse.persistence.jpa:x.y.z")
    implementation("com.linecorp.kotlin-jdsl:eclipselink-support:3.0.0")
    // or
    implementation("org.hibernate:hibernate-core:x.y.z")
    implementation("com.linecorp.kotlin-jdsl:hibernate-support:3.0.0")
    // or
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.y.z")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.0.0")
    // or
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.z")
    implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-javax-support:3.0.0")
}
```
{% endtab %}

{% tab title="Gradle (Groovy)" %}
```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.linecorp.kotlin-jdsl:jpql-dsl:3.0.0'
    implementation 'com.linecorp.kotlin-jdsl:jpql-render:3.0.0'

    implementation 'org.eclipse.persistence:org.eclipse.persistence.jpa:x.y.z'
    implementation 'com.linecorp.kotlin-jdsl:eclipselink-support:3.0.0'
    // or
    implementation 'org.hibernate:hibernate-core:x.y.z'
    implementation 'com.linecorp.kotlin-jdsl:hibernate-support:3.0.0'
    // or
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa:3.y.z'
    implementation 'com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.0.0'
    // or
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa:2.7.z'
    implementation 'com.linecorp.kotlin-jdsl:spring-data-jpa-javax-support:3.0.0'
}
```
{% endtab %}

{% tab title="Maven" %}
```markup
<project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
         
    <repositories>
        <repository>
            <id>mavenCentral</id>
            <url>https://repo1.maven.org/maven2/</url>
        </repository>
    </repositories>
    
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

        <dependency>
            <groupId>org.eclipse.persistence</groupId>
            <artifactId>org.eclipse.persistence.jpa</artifactId>
            <version>x.y.z</version>
        </dependency>
        <dependency>
            <groupId>com.linecorp.kotlin-jdsl</groupId>
            <artifactId>eclipselink-support</artifactId>
            <version>3.0.0</version>
        </dependency>
        // or
        <dependency>
            <groupId>org.hibernate</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>x.y.z</version>
        </dependency>
        <dependency>
            <groupId>com.linecorp.kotlin-jdsl</groupId>
            <artifactId>hibernate-support</artifactId>
            <version>3.0.0</version>
        </dependency>
        // or
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>3.y.z</version>
        </dependency>
        <dependency>
            <groupId>com.linecorp.kotlin-jdsl</groupId>
            <artifactId>spring-data-jpa-support</artifactId>
            <version>3.0.0</version>
        </dependency>
        // or
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
            <version>2.7.z</version>
        </dependency>
        <dependency>
            <groupId>com.linecorp.kotlin-jdsl</groupId>
            <artifactId>spring-data-jpa-javax-support</artifactId>
            <version>3.0.0</version>
        </dependency>
    </dependencies>

</project>
```
{% endtab %}
{% endtabs %}

## Building JPQL queries with DSL

The jpql function allows you to build JPQL queries using DSL functions. You can create a select statement, update statement, and delete statement using the [select](statements.md#select-statement), [update](statements.md#update-statement), and [deleteFrom](statements.md#delete-statement) functions.

To execute the created queries, there will be RenderContext. Kotlin JDSL provides JpqlRenderContext as the default RenderContext for JPQL, so you can use it to execute the queries.

You can see more [examples](https://github.com/line/kotlin-jdsl/tree/main/example) on github.

```kotlin
val context = JpqlRenderContext()

val query = jpql {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class),
        join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
    ).groupBy(
        path(Author::authorId),
    ).orderBy(
        count(Author::authorId).desc(),
    )
}

val jpaQuery: Query = entityManager.createQuery(query, context)

val result = jpaQuery.resultList
```

### Customizing

Kotlin JDSL can help you create your own DSL. To create your own DSL, you can inherit from Jpql class to add DSL functions, and you can build and execute queries through your own Query Model class by implementing JpqlSerializer interface.&#x20;

See [more](customizing.md) for more details.
