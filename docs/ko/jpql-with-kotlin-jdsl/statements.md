# Statements

JPQL은 select, update, delete statement를 지원합니다.
Kotlin JDSL은 이 statement들을 만들 수 있는 DSL을 제공합니다.

## Select statement

`jpql()`에서 `select()`를 호출하는 것으로 select statement를 만들 수 있습니다.

```kotlin
val query = jpql {
    select(
        path(Employee::employeeId),
    ).from(
        entity(Employee::class),
        join(Employee::departments),
    ).where(
        type(entity(Employee::class)).eq(FullTimeEmployee::class)
    ).groupBy(
        path(Employee::employeeId),
    ).having(
        count(Employee::employeeId).greaterThan(1L),
    ).orderBy(
        count(Employee::employeeId).desc(),
        path(Employee::employeeId).asc(),
    )
}
```

### Select clause

select statement의 select clause를 만들기 위해, `select()`를 이용할 수 있습니다.
`select()`는 [`Expression`](expressions.md)을 파라미터로 받아 프로젝션을 표현합니다.
만약 하나의 `Expression`만 `select()`에 넘어온다면 타입 추론으로 select statement의 타입을 결정하지만 하나 이상의 `Expression`이 넘어온다면 타입 명시가 필요합니다.

```kotlin
// It can infer the result type.
select(path(Author::authorId))

// It cannot infer the result type.
select(path(Author::authorId), path(Author::name))

// This allows it to know the result type.
select<CustomEntity>(path(Author::authorId), path(Author::name))
```

#### DTO projection

DTO 클래스와 클래스의 생성자를 `selectNew()`에 넘기는 것으로 DTO 프로젝션을 만들 수 있습니다.

```kotlin
data class Row(
    val departmentId: Long,
    val count: Long,
)

selectNew<Row>(
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
)
```

### From clause

select statement의 from clause를 만들기 위해, `from()`을 이용할 수 있습니다.
`from()`은 [Entity](entities.md)와 [Join](statements.md#join)을 파라미터로 받아 어떤 entity를 통해 조회가 되는지 표현합니다.

```kotlin
from(
    entity(Author::class),
    join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
)
```

#### Join

조회되는 entity를 조인하기 위해, `join()`과 `fetchJoin()`을 사용할 수 있습니다.
Join에는 2종류가 있으며 일반 Join과 연관관계 Join이 있습니다.
두 Join은 연관관계가 있는 entity를 조인하는지 없는 entity를 조인하는지에 따라 구별됩니다.

```kotlin
@Entity
// ...
class Book(
    // ...

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    val authors: MutableSet<BookAuthor>,
)

@Entity
// ...
class BookAuthor(
    @Id
    @Column(name = "author_id")
    val authorId: Long,
) {
    @Id
    @ManyToOne
    @JoinColumn(name = "isbn")
    lateinit var book: Book
}

@Entity
// ...
class Author(
    @Id
    @Column(name = "author_id")
    val authorId: Long,

    // ...
)

from(
    entity(Book::class),
    join(Book::authors), // Association Join
    join(Author::class).on(path(BookAuthor::authorId).eq(path(Author::authorId))), // Join
)
```

`join()` 이후에 `as()`를 호출하는 것으로 조인될 entity에 alias를 부가할 수 있습니다.
만약 동일한 타입의 entity를 여러개 from clause에 포함시킬 때 이 기능을 이용할 수 있습니다.

```kotlin
from(
    entity(Book::class),
    join(Book::authors).`as`(entity(BookAuthor::class, "author")),
)
```

### Where clause

select statement의 where clause를 만들기 위해, `where()`를 사용할 수 있습니다.
`where()`은 [Predicate](predicates.md)를 파라미터로 받아 조회 데이터의 제약을 표현합니다.
`where()`와 `and()`의 축약어로 `whereAnd()`를 사용할 수 있습니다.
마찬가지로 `where()`와 `or()`의 축약어로 `whereOr()`을 사용할 수 있습니다.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

### Group by clause

select statement의 group by clause를 만들기 위해, `groupBy()`를 사용할 수 있습니다.
`groupBy() 는 [Expression](expressions.md)을 파라미터로 받아 데이터의 그룹핑을 표현합니다.

```kotlin
groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

select statement의 having clause를 만들기 위해, `having()`을 사용할 수 있습니다.
`having()`은 [Expression](expressions.md)을 파라미터로 받아 추가적인 조회 데이터의 제약을 표현합니다.
`having()`과 `and()`의 축약어로 `havingAnd()`를 사용할 수 있습니다.
마찬가지로 `having()`과 `or()`의 축약어로 `havingOr()`을 사용할 수 있습니다.

```kotlin
having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

select statment의 order by clause를 만들기 위해, `orderBy()`를 사용할 수 있습니다.
`orderBy()`는 [Sort](sorts.md)를 파라미터로 받아 데이터의 정렬을 표현합니다.

```kotlin
orderBy(
    path(Book::isbn).asc(),
)
```

## Update statement

`jpql()`에서 `update()`를 호출하는 것으로 update statement를 만들 수 있습니다.

```kotlin
val query = jpql {
    update(
        entity(Book::class)
    ).set(
        path(Book::price)(BookPrice::value),
        BigDecimal.valueOf(100)
    ).set(
        path(Book::salePrice)(BookPrice::value),
        BigDecimal.valueOf(80)
    ).where(
        path(Book::isbn).eq(Isbn("01"))
    )
}
```

### Update clause

update statment의 update clause를 만들기 위해, `update()`를 사용할 수 있습니다.
`update()`는 [Entity](entities.md)를 파라미터로 받아 수정될 entity를 표현합니다.

```kotlin
update(
    entity(Employee::class),
)
```

### Set clause

update statement의 set clause를 만들기 위해, `set()`을 사용할 수 있습니다.
`set()`은 [Expression](expressions.md)을 파라미터로 받아 할당을 표현합니다.
`set()`을 여러번 호출하는 것으로 여러 개를 할당할 수 있습니다.

```kotlin
set(
    path(Book::price)(BookPrice::value),
    BigDecimal.valueOf(100)
).set(
    path(Book::salePrice)(BookPrice::value),
    BigDecimal.valueOf(80)
)
```

### Where clause

update statement의 where clause를 만들기 위해, `where()`를 사용할 수 있습니다.
`where()`은 [Predicate](predicates.md)를 파라미터로 받아 조회 데이터의 제약을 표현합니다.
`where()`와 `and()`의 축약어로 `whereAnd()`를 사용할 수 있습니다.
마찬가지로 `where()`와 `or()`의 축약어로 `whereOr()`을 사용할 수 있습니다.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

## Delete statement

`jpql()`에서 `deleteFrom()`를 호출하는 것으로 delete statement를 만들 수 있습니다.

```kotlin
val query = jpql {
    deleteFrom(
        entity(Book::class),
    ).where(
        path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
    )
}
```

### Delete from clause

delete statement의 delete clause를 만들기 위해, `deleteFrom()`을 사용할 수 있습니다.
`deleteFrom()`은 [Entity](entities.md)를 파라미터로 받아 삭제할 entity를 표현합니다.

```kotlin
deleteFrom(
    entity(Book::class),
)
```

### Where clause

delete statement의 where clause를 만들기 위해, `where()`를 사용할 수 있습니다.
`where()`은 [Predicate](predicates.md)를 파라미터로 받아 조회 데이터의 제약을 표현합니다.
`where()`와 `and()`의 축약어로 `whereAnd()`를 사용할 수 있습니다.
마찬가지로 `where()`와 `or()`의 축약어로 `whereOr()`을 사용할 수 있습니다.

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```
