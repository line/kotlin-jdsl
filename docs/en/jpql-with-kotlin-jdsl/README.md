# JPQL with Kotlin JDSL

You can build and execute the JPQL query using the Kotlin JDSL.

## Requirements <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

Java 8 (or later) and Kotlin 1.9 (or later) are required to build and run an application with Kotlin JDSL.

## Choosing the artifacts <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

<table><thead><tr><th width="251">Artifact ID</th><th>Description</th></tr></thead><tbody><tr><td>jpql-dsl</td><td>Building the JPQL query through DSL.</td></tr><tr><td>jpql-render</td><td>Rendering the JPQL query from DSL as a string.</td></tr><tr><td>eclipse-support</td><td>Support to help you use Eclipse and Kotlin JDSL together.</td></tr><tr><td>hibernate-support</td><td>Support to help you use Eclipse and Kotlin JDSL together.</td></tr><tr><td>spring-batch-support</td><td>Support to help you use Spring Batch and Kotlin JDSL together.</td></tr><tr><td>spring-data-jpa-support</td><td>Support to help you use Spring Data Jpa and Kotlin JDSL together.</td></tr><tr><td>spring-data-jpa-javax-support</td><td>Support to help you use Spring Data Jpa 2.X and Kotlin JDSL together.</td></tr></tbody></table>

## Setting up with a build system <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

All Kotlin JDSL JARs are available in [Maven Central Repository](https://central.sonatype.com/search?q=g%3Acom.linecorp.kotlin-jdsl) under group ID `com.linecorp.kotlin-jdsl` so that you can fetch them easily using your favorite build tool.

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
    implementation("com.linecorp.kotlin-jdsl:eclipse-support:3.0.0")
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
    implementation 'com.linecorp.kotlin-jdsl:eclipse-jpql:3.0.0'
    implementation 'org.eclipse.persistence:org.eclipse.persistence.jpa:x.y.z'
    // or
    implementation 'com.linecorp.kotlin-jdsl:hibernate-jpql:3.0.0'
    implementation 'org.hibernate:hibernate-core:x.y.z'
    // or
    implementation 'com.linecorp.kotlin-jdsl:spring-data-jpql:3.0.0'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa:3.y.z'
    // or
    implementation 'com.linecorp.kotlin-jdsl:spring-data-jpql-javax:3.0.0'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa:2.y.z'
}
```
{% endtab %}

{% tab title="Maven" %}
<pre class="language-markup"><code class="lang-markup">&#x3C;project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://maven.apache.org/POM/4.0.0"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
         
    &#x3C;repositories>
        &#x3C;repository>
            &#x3C;id>mavenCentral&#x3C;/id>
            &#x3C;url>https://repo1.maven.org/maven2/&#x3C;/url>
        &#x3C;/repository>
    &#x3C;/repositories>
    
    &#x3C;dependencies>
        &#x3C;dependency>
            &#x3C;groupId>com.linecorp.kotlin-jdsl&#x3C;/groupId>
            &#x3C;artifactId>eclipse-jpql&#x3C;/artifactId>
            &#x3C;version>3.0.0&#x3C;/version>
        &#x3C;/dependency>
        &#x3C;dependency>
            &#x3C;groupId>org.eclipse.persistence&#x3C;/groupId>
            &#x3C;artifactId>org.eclipse.persistence.jpa&#x3C;/artifactId>
            &#x3C;version>x.y.z&#x3C;/version>
        &#x3C;/dependency>
<strong>        &#x3C;!-- or -->
</strong>        &#x3C;dependency>
            &#x3C;groupId>com.linecorp.kotlin-jdsl&#x3C;/groupId>
            &#x3C;artifactId>hibernate-jpql&#x3C;/artifactId>
            &#x3C;version>3.0.0&#x3C;/version>
        &#x3C;/dependency>
        &#x3C;dependency>
            &#x3C;groupId>org.hibernate&#x3C;/groupId>
            &#x3C;artifactId>hibernate-core&#x3C;/artifactId>
            &#x3C;version>x.y.z&#x3C;/version>
        &#x3C;/dependency>
        &#x3C;!-- or -->
        &#x3C;dependency>
            &#x3C;groupId>com.linecorp.kotlin-jdsl&#x3C;/groupId>
            &#x3C;artifactId>spring-data-jpql&#x3C;/artifactId>
            &#x3C;version>3.0.0&#x3C;/version>
        &#x3C;/dependency>
        &#x3C;dependency>
            &#x3C;groupId>org.springframework.boot&#x3C;/groupId>
            &#x3C;artifactId>spring-boot-starter-data-jpa&#x3C;/artifactId>
            &#x3C;version>3.y.z&#x3C;/version>
        &#x3C;/dependency>
        &#x3C;!-- or -->
        &#x3C;dependency>
            &#x3C;groupId>com.linecorp.kotlin-jdsl&#x3C;/groupId>
            &#x3C;artifactId>spring-data-jpql-javax&#x3C;/artifactId>
            &#x3C;version>3.0.0&#x3C;/version>
        &#x3C;/dependency>
        &#x3C;dependency>
            &#x3C;groupId>org.springframework.boot&#x3C;/groupId>
            &#x3C;artifactId>spring-boot-starter-data-jpa&#x3C;/artifactId>
            &#x3C;version>2.y.z&#x3C;/version>
        &#x3C;/dependency>
    &#x3C;/dependencies>

&#x3C;/project>
</code></pre>
{% endtab %}
{% endtabs %}

## Building the JPQL query with DSL

The `jpql` function helps you easily build the JPQL query. In a block of the jpql function, you can use functions provided by Kotlin JDSL.&#x20;

You may want to create your own DSL function. You can create and use it by creating your own DSL and passing it to the `jpql` function. See [more](customizing.md) for more details.

```kotlin
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
```

## Initializing a context to execute the query

To execute the created query, you need to prepare a context that can render this query. Kotlin JDSL provides a default context, `JpqlRenderContext`. By initializing the `JpqlRenderContext`, you can prepare a context that can render the created query.&#x20;

You may want to render the objects provided by the DSL you created. In this case, you can create your own context by creating the Module and registering it with the `JpqlRenderContext`. See [more](customizing.md) for more details.

```kotlin
val context = JpqlRenderContext()
```

## Executing the query

Kotlin JDSL provides the extension function for executing the query in the libraries you use. You can execute the query by passing it and context to the extension function.

```kotlin
val jpaQuery: Query = entityManager.createQuery(query, context)
```

## Example

You can see many [examples](https://github.com/line/kotlin-jdsl/tree/main/example) on github.
