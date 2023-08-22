# Statements

Kotlin JDSL을 통해 select, update, delete statement를 만들 수 있습니다.

## Select statement

jpql 함수 블록 안에서 select 함수를 호출하는 것으로 select statement를 만들 수 있습니다. select statement의 각 clause는 각각의 이름에 대응하는 함수를 통해서 표현할 수
있습니다.

```kotlin
val query = jpql {
    select<Row>(
        path(Employee::employeeId).`as`(expression("employeeId")),
        count(Employee::employeeId).`as`(expression("count")),
    ).from(
        entity(Employee::class),
        join(Employee::departments),
    ).where(
        path(EmployeeDepartment::departmentId).eq(3L)
    ).groupBy(
        path(Employee::employeeId),
    ).having(
        count(Employee::employeeId).greaterThan(1L),
    ).orderBy(
        expression<Long>("employeeId").asc()
    )
}
```

### Select clause

select clause는 select 함수를 통해서 표현됩니다. select 함수는 [expression](expressions.md)을 파라미터로 받을 수 있습니다. 만약 하나의 expression만 select
함수에 전달한다면, Kotlin JDSL은 이 expression을 통해 select clause의 반환 타입을 추론할 수 있습니다. 하지만 여러 expression이 전달되면 Kotlin JDSL은 타입을 추론할
수 없기 때문에 사용자가 타입을 명시 시킬 필요가 있습니다.

```kotlin
// It can infer the result type.
select(path(Author::authorId))

// It cannot infer the result type.
select(path(Author::authorId), path(Author::name))

// This allows it to know the result type.
select<CustomEntity>(path(Author::authorId), path(Author::name))
```

selectNew 함수를 이용하면 DTO를 조회할 수 있습니다. selectNew 함수에 조회하고 싶은 DTO 클래스를 명시하고 클래스 생성자의 파라미터를 selectNew 함수에 전달하면 DTO를 조회할 수 있게
됩니다.

<pre class="language-kotlin"><code class="lang-kotlin"><strong>data class Row(
</strong>    val departmentId: Long,
    val count: Long,
)

selectNew&#x3C;Row>(
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
)
</code></pre>

### From clause

from clause은 from 함수를 통해서 표현됩니다. from 함수는 [entity](entities.md)와 [join](statements.md#join)을 파라미터로 받을 수 있습니다. from 함수의 첫
번째 파라미터는 항상 entity여야 합니다.

<pre class="language-kotlin"><code class="lang-kotlin"><strong>from(
</strong>    entity(Author::class),
    join(BookAuthor::class).on(path(Author::authorId).equal(path(BookAuthor::authorId))),
)
</code></pre>

### Join

join clause은 join 혹은 fetchJoin 함수를 통해서 표현됩니다. join에는 2종류가 있으며 join과 association join이 있습니다. join은 연관관계가 없는 entity들을
join할 때 사용되며 on 조건이 필수로 요구됩니다. association join은 연관관계가 있는 entity들을 join할 때 사용되며 on 조건은 선택적으로 추가할 수 있습니다.

```kotlin
from(
    entity(Book::class),
    join(Book::authors),
    join(Author::class).on(path(BookAuthor::authorId).eq(path(Author::authorId))),
)
```

join 함수 이후에 as 함수를 호출하는 것으로 select statement의 다른 clause에서 사용할 수 있는 alias를 join clause에 추가할 수 있습니다. 동일한 타입을 가진 entity를 여럿
from clause에 join으로 포함하고 싶다면 이 기능을 이용해 포함 시킬 수 있습니다.

```kotlin
from(
    entity(Book::class),
    join(Book::authors).`as`(entity(BookAuthor::class, "author")),
)
```

### Where clause

where clause는 where 함수를 통해서 표현됩니다. where 함수는 [predicate](predicates.md)를 파라미터로 받을 수 있습니다. 여러 predicate를 AND 혹은 OR로 묶고
싶다면 whereAnd와 whereOr 함수를 이용해 묶을 수 있습니다.

{% hint style="info" %}
만약 whereAnd 함수의 predicate가 모두 null이거나 비어있으면 1 = 1이 쿼리로 출력됩니다. whereOr의 경우는 1 = 0이 출력됩니다.
{% endhint %}

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

whereAnd와 whereOr 함수는 만약 파라미터로 넘어온 predicate가 null이면 이를 무시합니다. 이 기능을 이용해서 동적 쿼리를 읽기 쉽게 작성할 수 있습니다.

```kotlin
data class BookSpec(
    val isbn: Isbn?,
    val publisherId: Long?,
    val authorId: Long?,
)

val query = jpql {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
        join(Book::publisher),
        join(Book::authors),
    ).whereAnd(
        spec.isbn?.let { path(Book::isbn).eq(it) },
        spec.publisherId?.let { path(BookPublisher::publisherId).eq(it) },
        spec.authorId?.let { path(BookAuthor::authorId).eq(it) },
    )
}
```

### Group by clause

group by 절은 groupBy 함수를 통해서 표현됩니다. groupBy 함수는 [expression](expressions.md)을 파라미터로 받을 수 있습니다.

```kotlin
groupBy(
    path(EmployeeDepartment::departmentId),
)
```

### Having clause

having 절은 having 함수를 통해서 표현됩니다. having 함수는 [predicate](predicates.md)를 파라미터로 받을 수 있습니다. 여러 predicate를 AND 혹은 OR로 묶고 싶다면
havingAnd와 havingOr 함수를 이용해 묶을 수 있습니다.

{% hint style="info" %}
만약 havingAnd 함수의 predicate가 모두 null이거나 비어있으면 1 = 1이 쿼리로 출력됩니다. havingOr의 경우는 1 = 0이 출력됩니다.
{% endhint %}

```kotlin
having(
    count(Employee::employeeId).greaterThan(1L),
)
```

### Order by clause

order by 절은 orderBy 함수를 통해서 표현됩니다. orderBy 함수는 [sort](sorts.md)를 파라미터로 받을 수 있습니다.

```kotlin
orderBy(
    path(Book::isbn).asc(),
)
```

## Update statement

jpql 함수 블록 안에서 update 함수를 호출하는 것으로 update statement를 만들 수 있습니다. update statement의 각 clause는 각각의 이름에 대응하는 함수를 통해서 표현할 수
있습니다.

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

update clause는 update 함수를 통해서 표현됩니다. update 함수는 [entity](entities.md)를 파라미터로 받을 수 있습니다.

```kotlin
update(
    entity(Employee::class),
)
```

### Set clause

set clause는 set 함수를 통해서 표현됩니다. set 함수는 [expression](expressions.md)를 파라미터로 받을 수 있습니다. set 함수의 첫 번째 파라미터는 할당이 될 대상이 되며 두
번째 파라미터는 할당할 값이 됩니다. set 함수 호출 이후여 set 함수를 또 호출하는 것으로 여러 컬럼에 값을 할당할 수 있습니다.

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

where clause는 where 함수를 통해서 표현됩니다. where 함수는 [predicate](predicates.md)를 파라미터로 받을 수 있습니다. 여러 predicate를 AND 혹은 OR로 묶고
싶다면 whereAnd와 whereOr 함수를 이용해 묶을 수 있습니다.

{% hint style="info" %}
만약 whereAnd 함수의 predicate가 모두 null이거나 비어있으면 1 = 1이 쿼리로 출력됩니다. whereOr의 경우는 1 = 0이 출력됩니다.
{% endhint %}

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

whereAnd와 whereOr 함수는 만약 파라미터로 넘어온 predicate가 null이면 이를 무시합니다. 이 기능을 이용해서 동적 쿼리를 읽기 쉽게 작성할 수 있습니다.

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
    ).whereAnd(
        spec.isbn?.let { path(Book::isbn).eq(it) },
        spec.publishDate?.let { path(Book::publishDate).eq(it) },
    )
}
```

## Delete statement

jpql 함수 블록 안에서 deleteFrom 함수를 호출하는 것으로 delete statement를 만들 수 있습니다. delete statement의 각 clause는 각각의 이름에 대응하는 함수를 통해서 표현할
수 있습니다.

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

delete from clause는 deleteFrom 함수를 통해서 표현됩니다. deleteFrom 함수는 [entity](entities.md)를 파라미터로 받을 수 있습니다.

```kotlin
deleteFrom(
    entity(Book::class),
)
```

### Where clause

where clause는 where 함수를 통해서 표현됩니다. where 함수는 [predicate](predicates.md)를 파라미터로 받을 수 있습니다. 여러 predicate를 AND 혹은 OR로 묶고
싶다면 whereAnd와 whereOr 함수를 이용해 묶을 수 있습니다.

{% hint style="info" %}
만약 whereAnd 함수의 predicate가 모두 null이거나 비어있으면 1 = 1이 쿼리로 출력됩니다. whereOr의 경우는 1 = 0이 출력됩니다.
{% endhint %}

```kotlin
where(
    path(Book::publishDate).between(
        OffsetDateTime.parse("2023-01-01T00:00:00+09:00"),
        OffsetDateTime.parse("2023-06-30T23:59:59+09:00"),
    ),
)
```

whereAnd와 whereOr 함수는 만약 파라미터로 넘어온 predicate가 null이면 이를 무시합니다. 이 기능을 이용해서 동적 쿼리를 읽기 쉽게 작성할 수 있습니다.

```kotlin
data class BookSpec(
    val isbn: Isbn?,
    val publishDate: OffsetDateTime?,
)

val query = jpql {
    deleteFrom(
        entity(Book::class),
    ).whereAnd(
        spec.isbn?.let { path(Book::isbn).eq(it) },
        spec.publishDate?.let { path(Book::publishDate).eq(it) },
    )
}
```
