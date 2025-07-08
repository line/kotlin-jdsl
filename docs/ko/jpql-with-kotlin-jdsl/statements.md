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

### 집합 연산 (`UNION`, `UNION ALL`, `EXCEPT`, `EXCEPT ALL`)

Jakarta Persistence 3.2부터 JPQL은 집합 연산자를 사용하여 둘 이상의 `SELECT` 쿼리 결과를 결합하는 기능을 공식적으로 지원합니다. Kotlin JDSL은 이러한 새로운 표준 기능인 `UNION`, `UNION ALL`, `EXCEPT`, `EXCEPT ALL` 연산을 지원합니다. (`INTERSECT` 또한 JPA 3.2에 추가되었습니다.)

*   `UNION`: 두 쿼리의 결과 집합을 결합하고 중복된 행을 제거합니다.
*   `UNION ALL`: 두 쿼리의 결과 집합을 결합하고 모든 중복된 행을 포함합니다.
*   `EXCEPT`: 첫 번째 쿼리에서 두 번째 쿼리에 없는 행을 반환하며, 중복을 제거합니다.
*   `EXCEPT ALL`: 첫 번째 쿼리에서 두 번째 쿼리에 없는 행을 반환하며, 모든 중복을 포함합니다.

집합 연산(`UNION`, `UNION ALL`, `EXCEPT`, `EXCEPT ALL`)에 포함되는 `SELECT` 문들은 select 목록에 동일한 수의 열을 가져야 하며, 해당 열의 데이터 타입은 서로 호환되어야 합니다.

**연결된 Select 쿼리와 함께 사용:**

select 쿼리 구조(예: `select`, `from`, `where`, `groupBy`, 또는 `having` 절 뒤)에 `union()`, `unionAll()`, `except()`, 또는 `exceptAll()`을 연결하여 사용할 수 있습니다. `orderBy()` 절이 사용되는 경우, 집합 연산의 최종 결과에 적용됩니다.

```kotlin
// UNION 예제
val unionQuery = jpql {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::price)(BookPrice::value).lessThan(BigDecimal.valueOf(20))
    ).union( // 우측 쿼리 또한 select 구조입니다.
        select(
            path(Book::isbn)
        ).from(
            entity(Book::class)
        ).where(
            path(Book::salePrice)(BookPrice::value).lessThan(BigDecimal.valueOf(15))
        )
    ).orderBy(
        path(Book::isbn).asc()
    )
}

// UNION ALL 예제
val unionAllQuery = jpql {
    select(
        path(Author::name)
    ).from(
        entity(Author::class)
    ).where(
        path(Author::name).like("%Rowling%")
    ).unionAll( // 우측 쿼리 또한 select 구조입니다.
        select(
            path(Author::name)
        ).from(
            entity(Author::class)
        ).where(
            path(Author::name).like("%Tolkien%")
        )
    ).orderBy(
        path(Author::name).desc()
    )
}

// EXCEPT 예제
val exceptQuery = jpql {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::price)(BookPrice::value).lessThan(BigDecimal.valueOf(30))
    ).except( // 우측 쿼리 또한 select 구조입니다.
        select(
            path(Book::isbn)
        ).from(
            entity(Book::class)
        ).where(
            path(Book::salePrice)(BookPrice::value).lessThan(BigDecimal.valueOf(20))
        )
    ).orderBy(
        path(Book::isbn).asc()
    )
}

// EXCEPT ALL 예제
val exceptAllQuery = jpql {
    select(
        path(Author::name)
    ).from(
        entity(Author::class)
    ).where(
        path(Author::name).like("%Fantasy%")
    ).exceptAll( // 우측 쿼리 또한 select 구조입니다.
        select(
            path(Author::name)
        ).from(
            entity(Author::class)
        ).where(
            path(Author::name).like("%Mystery%")
        )
    ).orderBy(
        path(Author::name).desc()
    )
}
```

**최상위 레벨 연산으로 사용:**

`jpql` 블록 내에서 두 개의 `JpqlQueryable<SelectQuery<T>>` 인스턴스를 결합하여 `union()`, `unionAll()`, `except()`, `exceptAll()`을 최상위 레벨 연산으로 사용할 수도 있습니다.

```kotlin
val query1 = jpql {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::price)(BookPrice::value).eq(BigDecimal.valueOf(10))
    )
}

val query2 = jpql {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::salePrice)(BookPrice::value).eq(BigDecimal.valueOf(10))
    )
}

// 최상위 레벨 UNION ALL
val topLevelUnionAllQuery = jpql {
    unionAll(query1, query2)
        .orderBy(path(Book::isbn).asc())
}

// 최상위 레벨 EXCEPT ALL
val topLevelExceptAllQuery = jpql {
    exceptAll(query1, query2)
        .orderBy(path(Book::isbn).asc())
}
```

**`ORDER BY`에 대한 중요 참고 사항:**

`ORDER BY` 절은 집합 연산(`UNION`, `UNION ALL`, `EXCEPT`, `EXCEPT ALL`)의 최종 결과 집합에 적용됩니다. 집합 연산 자체에 영향을 미치는 방식으로 집합 연산의 일부인 개별 `SELECT` 쿼리에 적용될 수 없습니다. (물론, 하위 쿼리가 집합 연산 전에 결과를 제한하는 등의 다른 목적으로 자체 `ORDER BY`를 가질 수 있지만, 일반적으로 JPQL에서 집합 연산과 최종 정렬을 위해 상호 작용하는 방식은 아닙니다.) `ORDER BY` 절의 정렬 기준은 일반적으로 첫 번째 쿼리의 `SELECT` 목록에 있는 열의 별칭 또는 위치를 참조합니다.

**데이터베이스 호환성 참고사항:**

이러한 집합 연산은 JPA 3.2 사양의 일부이지만, 모든 데이터베이스가 모든 연산을 지원하는 것은 아닙니다. 예를 들어:
- H2 데이터베이스(버전 1.4.192 - 2.3.232)는 `UNION`, `UNION ALL`, `EXCEPT`를 지원하지만 `EXCEPT ALL`은 지원하지 않습니다
- PostgreSQL, Oracle, SQL Server는 네 가지 연산(`UNION`, `UNION ALL`, `EXCEPT`, `EXCEPT ALL`)을 모두 지원합니다
- MySQL은 `UNION`, `UNION ALL`은 지원하지만 `EXCEPT` 연산은 지원하지 않습니다 (대안으로 `NOT EXISTS` 또는 `LEFT JOIN` 사용)

이러한 연산을 사용할 때는 대상 데이터베이스가 이를 지원하는지 확인하거나, 지원하지 않는 데이터베이스에 대한 대체 쿼리 전략을 제공해야 합니다.

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
