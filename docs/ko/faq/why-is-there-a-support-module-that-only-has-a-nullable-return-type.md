# 왜 Kotlin JDSL은 Nullable한 반환 타입을 허용하나요?

아마도 Kotlin JDSL의 일부 support 모듈이 제공하는 메소드들이 모두 nullable 반환 타입만 가진 것에 의문을 가지실 수 있습니다.
쿼리에 있는 Join 결과가 nullable인지 아닌지 알 수 없기 때문에 Kotlin JDSL은 쿼리의 반환 타입이 non-null인지 nulalble인지 추론할 수 없습니다.
다음은 nullable 타입이 반환되는 몇가지 케이스들입니다.

| Return Type | Comment                                                           |
|-------------|-------------------------------------------------------------------|
| Column      | Column 정의가 nullable일 경우 null을 반환할 수 있습니다.                         |
| Entity      | Left Join과 같이 entity가 nullable한 방식으로 Join되었을 경우 null을 반환할 수 있습니다. |

예를 들어서 다음은 Left Join으로 인해 nullable한 entity를 반환하는 경우입니다.

```kotlin
val query = jpql {
    select(
        path(BookAuthor),
    ).from(
        entity(Author::class),
        leftJoin(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
    )
}
```

Kotlin JDSL은 non-null한 반환 타입과 nullable한 반환 타입을 구분해서 별도로 메소드를 제공할 수 있었지만 사용자에게 혼란을 줄 수 있기 때문에 그렇게 하지 않았습니다.
예를 들어 JPQL은 기본적으로 nullable한 반환 타입을 사용하므로 non-null 반환 타입에 대한 네이밍 컨벤션이 없습니다.
Kotlin JDSL이 네이밍 컨벤션을 도입할 경우 사용자는 내부 구현에 대해 의문을 가질 수 있습니다.
예를 들어 메소드가 null을 필터링해서 반환하는지 null이 포함된 경우 예외를 던지는지 헷갈릴 수 있습니다.
Kotlin JDSL은 사용자가 라이브러리를 사용할 때 추가적인 학습을 하는 것을 바라지 않기 때문에 Kotlin JDSL은 자체 메소드를 정의하지 않았습니다.
