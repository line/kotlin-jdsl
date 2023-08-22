# JPQL with Kotlin JDSL

Kotlin JDSL을 이용하면 쉽게 JPQL 쿼리를 만들고 실행시킬 수 있습니다.

## Requirements <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

Kotlin JDSL을 실행시키기 위해선 Java 8 (혹은 그 이상) 그리고 Kotlin 1.9 (혹은 그 이상) 버전이 요구됩니다.

## The artifacts <a href="#setting-up-with-a-build-system" id="setting-up-with-a-build-system"></a>

Kotlin JDSL은 여러가지 artifact를 제공합니다. 가장 기본이 되는 jpql-dsl과 jpql-render이 있으며, 이 두 개의 artifact를 포함하고 현재 사용하고 있는 라이브러리로 쿼리를 실행
시키기 위한 support artifact를 추가하는 것으로 사용자는 현재 사용중이던 라이브러리에서 Kotlin JDSL을 이용해 쉽게 쿼리를 만들고 실행시킬 수 있습니다.

### Basic

<table><thead><tr><th width="251">Artifact ID</th><th>Description</th></tr></thead><tbody><tr><td>jpql-dsl</td><td>Building the JPQL query using DSL.</td></tr><tr><td>jpql-render</td><td>Rendering the JPQL query from DSL as a string.</td></tr></tbody></table>

### Supports

<table><thead><tr><th width="251">Artifact ID</th><th>Description</th></tr></thead><tbody><tr><td>eclipselink-support</td><td>Support to help you use EclipseLink and Kotlin JDSL together.</td></tr><tr><td>eclipselink-javax-support</td><td>Support to help you use EclipseLink and Kotlin JDSL together for javax.</td></tr><tr><td>hibernate-support</td><td>Support to help you use Hibernate and Kotlin JDSL together.</td></tr><tr><td>hibernate-javax-support</td><td>Support to help you use Hibernate and Kotlin JDSL together for javax.</td></tr><tr><td>spring-batch-support</td><td>Support to help you use Spring Batch and Kotlin JDSL together.</td></tr><tr><td>spring-data-jpa-support</td><td>Support to help you use Spring Data Jpa and Kotlin JDSL together.</td></tr><tr><td>spring-data-jpa-javax-support</td><td>Support to help you use Spring Data Jpa 2.X and Kotlin JDSL together.</td></tr></tbody></table>

## Maven Central configuration

모든 JAR 파일들은 [Maven Central Repository](https://central.sonatype.com/search?q=g%3Acom.linecorp.kotlin-jdsl)
에 `com.linecorp.kotlin-jdsl` group ID로 업로드 되어 있기 때문에 빌드 툴을 이용하여 쉽게 가져올 수 있습니다.

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

## JPQL queries with DSL

jpql 함수를 통해서 Kotlin JDSL이 제공하는 DSL 함수를 사용할 수
있습니다. [select](statements.md#select-statement), [update](statements.md#update-statement), [deleteFrom](statements.md#delete-statement)
함수들을 이용하면 select, update, delete statement를 생성할 수 있습니다.

이렇게 만들어진 쿼리를 실행시키기 위해서는 RenderContext가 필요합니다. Kotlin JDSL에서는 JpqlRenderContext를 JPQL의 기본 RenderContext로 제공하고 있으며, 이것을
이용해서 쿼리를 실행시킬 수 있습니다.

github의 [examples](https://github.com/line/kotlin-jdsl/tree/main/example)을 통해서 더 많은 예제들을 볼 수 있습니다.

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

Kotlin JDSL을 이용하면 나만의 DSL을 만들 수 있습니다. Jpql 클래스를 상속해서 DSL 함수를 추가하는 것으로 나만의 DSL이 만들어집니다. 또 JpqlSerializer 인터페이스를 구현해서 나만의
Query Model 클래스를 만들어 쿼리를 빌드하고 실행할 수 있습니다.

더 자세한 내용이 궁금하시면 [여기](customizing.md)를 참고해주세요.
