# JPQL with Kotlin JDSL

## Requirements

Kotlin JDSL은 Java 8 (혹은 그 이상) and Kotlin 1.9 (혹은 그 이상)이 요구됩니다.

## Configure the repositories

Kotlin JDSL 디펜던시를 추가하기 전에 레포지토리를 설정해야 합니다:

### Release

Kotlin JDSL 릴리즈 버전은 모두 [Maven central repository](https://central.sonatype.com/search?q=g%3Acom.linecorp.kotlin-jdsl)에
올라가게 되어 maven 레포지토리 설정이 필요합니다:

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
pom.xml에는 maven 레포지토리 설정이 불필요합니다. 왜냐하면 모든 maven
프로젝트는 [Super POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html#super-pom)을 상속하기 때문입니다.
{% endhint %}
{% endtab %}

{% endtabs %}

### Snapshot

Kotlin JDSL의 스냅샷 버전은 모두 OSS snapshot 레포지토리에 올라가게 됩니다.

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

Kotlin JDSL을 실행시키기 위해서는 아래 디펜던시들을 포함하고 있어야 합니다.

- jpql-dsl: JPQL 쿼리를 만들기 위한 DSL 라이브러리
- jpql-render: DSL로 만들어진 쿼리를 String으로 랜더링하기 위한 라이브러리

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

Support 디펜던시를 추가하는 것으로 DSL을 통해 만들어진 쿼리를 사용하던 JPA 라이브러리에서 쉽게 실행시킬 수 있습니다. Kotlin JDSL 아래 디펜던시들을 제공합니다:

- hibernate-support: Hibernate를 통해 쿼리를 실행시키게 도와주는 라이브러리
- eclipselink-support: EclipseLink를 통해 쿼리를 실행시키게 도와주는 라이브러리
- spring-batch-support: Spring Batch를 통해 쿼리를 실행시키게 도와주는 라이브러리
- spring-data-jpa-support: Spring Data JPA를 통해 쿼리를 실행시키게 도와주는 라이브러리

#### Javax

Javax 패키지를 위해서는 아래의 디펜던시들을 제공합니다:

- hibernate-javax-support: Hibernate를 통해 쿼리를 실행시키게 도와주는 라이브러리
- eclipselink-javax-support: EclipseLink를 통해 쿼리를 실행시키게 도와주는 라이브러리
- spring-batch-javax-support: Spring Batch를 통해 쿼리를 실행시키게 도와주는 라이브러리
- spring-data-jpa-javax-support: Spring Data JPA를 통해 쿼리를 실행시키게 도와주는 라이브러리

## Build a query

jpql()

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

Similarly, Kotlin JDSL provides functions for all the other
statements: [update statement](statements.md#update-statement), [delete statement](statements.md#delete-statement). You
can also see more [examples](https://github.com/line/kotlin-jdsl/tree/main/example) on GitHub.

In addition, you can also create your own [custom DSL](custom-dsl.md)

## Execute the query

After building the query, you can use `RenderContext` to execute the query. For example, you can use `JpqlRenderContext`
to execute the query:

```kotlin
val context = JpqlRenderContext()

val jpaQuery: Query = entityManager.createQuery(query, context)

val result = jpaQuery.resultList
```

`RenderContext` has elements for rendering the query as String. Kotlin JDSL
provides `JpqlRenderContext` as the default `RenderContext` for the JPQL.

Creating `RenderContext` is expensive, so the Kotlin JDSL recommends creating it once and reusing it.
Since `RenderContext` is immutable, you can access `RenderContext` from multiple threads.
