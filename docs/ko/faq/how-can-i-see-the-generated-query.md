# 어떻게 생성된 쿼리를 볼 수 있나요?

Kotlin JDSL은 DSL를 통해 생성된 쿼리 및 파라미터를 debug 로그로 출력합니다. 그렇기 때문에 `com.linecorp.kotlinjdsl` 패키지의 로그 레벨을 debug로 수정하면 보실 수 있습니다.

로그에 포함된 파라미터의 경우 `toString` 함수로 출력되기 때문에 만약 `toString` 함수가 오버라이드 되지 않았다면 식별에 어려움이 있을 수 있습니다.

```
select(
    path(Book::isbn),
).from(
    entity(Book::class),
).where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
).orderBy(
    path(Book::isbn).asc(),
)
```

```
2023-01-01T00:00:00.000+09:00 DEBUG c.l.kotlinjdsl.render.jpql.JpqlRenderer  : The query is rendered.
SELECT Book.isbn FROM Book AS Book WHERE Book.publishDate BETWEEN :param1 AND :param2 ORDER BY Book.isbn ASC
{param1=2023-01-01T00:00+09:00, param2=2023-06-30T23:59:59+09:00}
```
