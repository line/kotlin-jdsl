# 데이터베이스별 함수는 어떻게 사용하나요?

표준 JPQL 사양에 포함되지 않은 데이터베이스별 함수는 `function()` DSL 함수를 사용하여 호출할 수 있습니다. `JSON_VALUE`, `GROUP_CONCAT` 등과 같은 함수에 유용합니다.

데이터베이스 함수를 사용하려면 반환 유형, 함수 이름 및 인수를 제공해야 합니다.

다음은 MySQL에서 `GROUP_CONCAT`을 사용하는 예입니다.

```kotlin
val query = jpql {
    select(
        function(
            String::class,
            "GROUP_CONCAT",
            path(Book::name)
        )
    ).from(
        entity(Book::class)
    )
}
```

## JPA 제공업체에 함수 등록

JPA 제공업체가 함수를 이해하려면 함수를 등록해야 할 수 있습니다.

### Hibernate

Hibernate를 사용하는 경우 `FunctionContributor`를 사용하여 함수를 등록해야 합니다.

```kotlin
class MyFunctionContributor : FunctionContributor {
    override fun contributeFunctions(functionContributions: FunctionContributions) {
        functionContributions.functionRegistry.register(
            "group_concat",
            StandardSQLFunction("group_concat", StandardBasicTypes.STRING)
        )
    }
}
```

그런 다음 이 기여자를 등록해야 합니다. `src/main/resources/META-INF/services/org.hibernate.boot.model.FunctionContributor` 파일을 만들고 기여자 클래스의 정규화된 이름을 내용으로 추가하여 등록할 수 있습니다.

```
com.example.MyFunctionContributor
```

또는 Spring Boot를 사용하는 경우 `application.yml`에서 속성을 설정할 수 있습니다.

```yaml
spring:
  jpa:
    properties:
      hibernate:
        metadata_builder_contributor: com.example.MyFunctionContributor
```

이렇게 설정하면 쿼리에서 `group_concat`을 사용할 수 있습니다.
