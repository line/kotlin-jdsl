# JPQL with Kotlin JDSL

## Requirements

Kotlin JDSL을 사용하기 위해서는 Java 8 (혹은 그 이상) and Kotlin 1.9 (그 이상)이 요구됩니다.

## Configure the repositories

Kotlin JDSL dependency를 추가하기 전에 maven repository가 추가 되어야 합니다.

### Release

Kotlin JDSL의 release는 모두 [Maven central repository](https://central.sonatype.com/search?q=g%3Acom.linecorp.kotlin-jdsl)에 업로드 됩니다.
이를 이용하기 위해서는 maven repository가 빌드 스크립트에 추가 되어 있어야 합니다.

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
maven 프로젝트는 이미 maven repository가 [Super POM](https://maven.apache.org/guides/introduction/introduction-to-the-pom.html#super-pom)에 추가 되어 있어 추가로 maven repository를 설정할 필요가 없습니다.
{% endhint %}
{% endtab %}

{% endtabs %}

### Snapshot

Kotlin JDSL의 snapshot은 모두 OSS snapshot repository에 업로드 됩니다.
이를 이용하기 위해서는 OSS snapshot repository가 빌드 스크립트에 추가 되어 있어야 합니다.

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

Kotlin JDSL을 실행시키기 위해서는 다음 dependency들이 필수로 요구됩니다.

- jpql-dsl: JPQL 쿼리를 만들 수 있게 도와주는 DSL
- jpql-render: DSL로 만든 쿼리를 String 변화시켜주는 라이브러리

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

Kotlin JDSL은 DSL로 생성된 쿼리를 실행시킬 수 있는 Support dependency를 제공합니다.
각 JPA 제공자에 맞춰 다음 dependency들 중에서 선택하여 사용할 수 있습니다.

- hibernate-support: Hibernate를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- eclipselink-support: EclipseLink를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-batch-support: Spring Batch와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-data-jpa-support: Spring Data Jpa와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.

#### Javax

javax 패키지를 사용하는 경우 다음 dependency들 중에서 선택하여 사용할 수 있습니다.

- hibernate-javax-support: Hibernate를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- eclipselink-javax-support: EclipseLink를 통해 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-batch-javax-support: Spring Batch와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.
- spring-data-jpa-javax-support: Spring Data Jpa와 함께 쿼리를 실행하도록 도움을 주는 라이브러리.

## Build a query

`jpql()`에서 `select()`를 호출하는 것으로 [select statement](statements.md#select-statement)를 만들 수 있습니다.

```kotlin
val query = jpql {
    select(
        path(Author::authorId),
    ).from(
        entity(Author::class)
    )
}
```

유사하게 Kotlin JDSL은 다른 statement를 위한 함수도 지원합니다: [update statement](statements.md#update-statement), [delete statement](statements.md#delete-statement).
더 많은 에제를 보고 싶으시면 GitHub에 [examples](https://github.com/line/kotlin-jdsl/tree/main/example)을 참고해주세요.

추가로 [custom DSL](custom-dsl.md)을 통해 본인만의 DSL을 만들 수도 있습니다.

## Execute the query

쿼리를 만든 뒤에는 `RenderContext`를 이용해 쿼리를 실행할 수 있습니다.
예를 들어 `JpqlRenderContext`로 다음과 같이 실행이 가능합니다.

```kotlin
val context = JpqlRenderContext()

val jpaQuery: Query = entityManager.createQuery(query, context)

val result = jpaQuery.resultList
```

`RenderContext`는 쿼리를 String으로 랜더링할 수 있는 요소들을 가지고 있습니다.
Kotlin JDSL은 `RenderContext`의 default 구현체로 `JpqlRenderContext`를 제공합니다.

`RenderContext`를 만드는 비용은 비싸기 때문에 한번만 만들고 이를 재활용하는 것을 추천드립니다.
`RenderContext`는 immutable 객체로 멀티 쓰레드 환경에서 사용하기에 안전합니다.
