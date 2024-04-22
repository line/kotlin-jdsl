# 왜 Kotlin JDSL은 Nullable한 반환 타입을 허용하나요?

Kotlin JDSL을 사용하면 Type Safe하게 쿼리를 작성할 수 있게 도와주지만
다건의 데이터를 반환할 때 non-null한 데이터 또한 null을 허용하는지 궁금증을 가질 수 있습니다. (예를 들어 `findSlice`의 `Slice<T?>`, `findPage`의 `Page<T?>`)
이 글은 Kotlin JDSL에서 nullable한 반환 타입을 허용하는 이유를 설명합니다.

## Nullable 반환 타입의 필요성

Kotlin JDSL이 nullable한 반환 타입을 지원하는 이유는 JPQL 쿼리의 작성 방법에 따라 반환되는 리스트 항목 중 null 값이 포함될 수 있기 때문입니다.
가장 간단한 예로는 DTO Projection을 사용하지 않고, column이나 entity를 조회할 때를 들 수 있습니다.

아래 표는 Kotlin JDSL을 사용할 때 nullable한 반환 타입이 발생할 수 있는 몇 가지 경우를 나타냅니다.

| 항목	            | null 여부 | 	이유                                                                                                   |
|----------------|---------|-------------------------------------------------------------------------------------------------------|
| DTO Projection | 	X      | 	모든 ROW에 대한 생성자 호출하기 때문에 객체가 생성되어 null을 허용하는 결과가 나오지 않음<br/>DTO의 필드가 null일 수는 있으나 DTO 객체가 null일 수는 없음 |
| Column         | 	O      | 	null인 필드를 조회하는 경우 존재                                                                                 |
| Entity         | 	O      | 	Left Join시 조인의 대상이 되는 Entity가 null인 경우 존재                                                            |

또 다른 예로 아래 코드는 Author 엔티티가 있지만, left join의 대상인 BookAuthor 엔티티는 null일 수 있는 상황을 보여줍니다.

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

## 설계 결정의 배경

초기 Kotlin JDSL 3.0 개발 시에는 nullable 추론을 시도했습니다.
이는 쿼리 결과의 nullable 여부를 타입 시스템을 통해 자동으로 추론하려는 시도였습니다.
하지만, join을 사용하는 쿼리의 개발 과정에서 완벽한 nullable 추론이 불가능하다는 문제에 직면했습니다.

Kotlin JDSL 3.0부터는 되도록 사용자가 알고 있는 쿼리 작성 지식만을 활용해서 적은 러닝 커브로 사용할 수 있게 하는 노선으로 정착합니다.
이는 사용자가 바라보는 인터페이스가 각자 달라 혼란을 방지하고, Kotlin JDSL 문법을 별도로 학습할 필요 없이 기존의 JPQL 지식을 기반으로 활용할 수 있게 합니다.
