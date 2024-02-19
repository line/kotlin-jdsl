# Expressions

Kotlin JDSL은 JPQL의 expression를 표현하기 위해 `Expression` 인터페이스를 가지고 있습니다.

## Alias

`Expression`의 `as()`를 호출하는 것으로 `Expression`에 alias를 걸 수 있습니다.
`expression()`을 이용하면 `Expression`의 참조를 만들 수 있습니다.
참조는 alias를 통해서 구분되며 동일한 alias를 가지고 있는 `Expression`을 참조할 수 있습니다.
이를 통해 `Expression`에 alias를 걸고 alias가 걸린 `Expression`을 다른 clause에서 참조할 수 있습니다.

```kotlin
val bookPrice = expression(BigDecimal::class, "price")

select(
    path(Book::price)(BookPrice::value).`as`(bookPrice)
).from(
    entity(Book::class)
).where(
    bookPrice.eq(BigDecimal.valueOf(100))
)

// OR

select(
    path(Book::price)(BookPrice::value).`as`(expression("price"))
).from(
    entity(Book::class)
).where(
    expression(BigDecimal::class, "price").eq(BigDecimal.valueOf(100))
)
```

### Type Cast

어떤 경우에는 `Expression`의 타입을 원하는 타입으로 변경하고 싶을 때가 있을 것입니다.
이를 위해 Kotlin JDSL은 `as()`를 통해서 unsafe type casting을 지원합니다.

{% hint style="info" %}
This is a shortened form of `as Expression<T>`, so it may not work as expected.
{% endhint %}

```kotlin
avg(path(FullTimeEmployee::annualSalary)(EmployeeSalary::value)).`as`(BigDecimal::class)
```

## Arithmetic operations

산술 연산자를 만들기 위해서는 다음 함수들을 사용할 수 있습니다.

* \+ (plus)
* \- (minus)
* \* (times)
* / (div)

```kotlin
path(Book::price).plus(path(Book::salePrice))
plus(path(Book::price), path(Book::salePrice))

path(Book::price).minus(path(Book::salePrice))
minus(path(Book::price), path(Book::salePrice))

path(Book::price).times(path(Book::salePrice))
times(path(Book::price), path(Book::salePrice))

path(Book::price).div(path(Book::salePrice))
div(path(Book::price), path(Book::salePrice))
```

### Parentheses

확장 함수가 아닌 일반 함수를 호출하는 것으로 산술 연산자에 연산 순서를 위한 소괄호를 추가할 수 있습니다.
확장 함수의 경우 연산 순서가 모호해서 소괄호를 추가할 수 없습니다.

```kotlin
// 일반 함수: (Book.price - Book.salePrice) * (100)
times(
    path(Book::price).minus(path(Book::salePrice)),
    BigDecimal.valueOf(100),
)

// 확장 함수: Book.price - Book.salePrice * 100
path(Book::price).minus(path(Book::salePrice)).times(BigDecimal.valueOf(100))
```

## Values

값을 만들기 위해, `value()`를 사용할 수 있습니다.
모든 값은 쿼리 파라미터로 치환되며, 이 파라미터는 변경할 수 없습니다.

{% hint style="info" %}
만약 KClass가 `value()`에 전달되면 이는 [Entity](entities.md)로 인식됩니다.
{% endhint %}

```kotlin
// SELECT Book.isbn FROM Book as Book WHERE Book.price = ?
select(
    path(Book::isbn)
).from(
    entity(Book::class)
).where(
    path(Book::price).eq(value(BigDecimal.valueOf(100)))
)
```

### Params

쿼리 파라미터를 만들기 위해, `value()` 대신 `param()`을 사용할 수 있습니다.
`param()`으로 만들어진 파라미터는 변경이 가능합니다.

```kotlin
val context = JpqlRenderContext()

val query = jpql {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::price).eq(param("price"))
    )
}

val queryParams = mapOf(
    "price" to BigDecimal.valueOf(100)
)

entityManager.createQuery(query, queryParams, context)
```

### Literals

literal을 만들기 위해, `value()` 대신 `xxxLiteral()`을 이용할 수 있습니다.

{% hint style="info" %}
string literal을 출력할 때 만약 '(작은 따옴표)가 있으면 '(작은 따옴표)는 ''(작은 따옴표 2개)로 변경되어 출력됩니다. 예로 'literal''s' 처럼 출력됩니다.
{% endhint %}

| Type    | Function       | Rendered                     |
|---------|----------------|------------------------------|
| Int     | intLiteral     | {value}                      |
| Long    | longLiteral    | {value}L                     |
| Float   | floatLiteral   | {value}F                     |
| Double  | doubleLiteral  | {value}                      |
| Boolean | booleanLiteral | TRUE or FALSE                |
| Char    | charLiteral    | '{value}'                    |
| String  | stringLiteral  | '{value}'                    |
| Enum    | enumLiteral    | {qualified name}.{enum name} |

## Aggregation functions

집합 함수를 만들기 위해, 다음 함수들을 사용할 수 있습니다.

* COUNT (count)
* MIN (min)
* MAX (max)
* AVG (avg)
* SUM (sum)

```kotlin
count(path(Book::price))
countDistinct(path(Book::price))

max(path(Book::price))
maxDistinct(path(Book::price))

min(path(Book::price))
minDistinct(path(Book::price))

sum(path(Book::price))
sumDistinct(path(Book::price))
```

### Sum

`sum()`은 파라미터에 따라 다른 반환 타입을 가지게 됩니다.

| Type       | Return Type |
|------------|-------------|
| Int        | Long        |
| Long       | Long        |
| Float      | Double      |
| Double     | Double      |
| BigInteger | BigInteger  |
| BigDecimal | BigDecimal  |

## Functions

Kotlin JDSL은 JPA에서 제공하는 여러 함수들을 지원하기 위함 함수들을 제공합니다.

### String functions

* CONCAT (concat)
* SUBSTRING (substring)
* TRIM (trim)
* LOWER (lower)
* UPPER (upper)
* LENGTH (length)
* LOCATE (locate)

```kotlin
concat(path(Book::title), literal(":"), path(Book::imageUrl))

substring(path(Book::title), 4)

trim(path(Book::title))
trim('B').from(path(Book::title))

lower(path(Book::title))

upper(path(Book::title))

length(path(Book::title))

locate("Book", path(Book::title))
```

### Arithmetic functions

* ABS (abs)
* CEILING (ceiling)
* EXP (exp)
* FLOOR (floor)
* INDEX (index)
* LN (ln)
* SIGN (sign)
* SQRT (sqrt)
* ROUND (round)
* SIZE (size)

```kotlin
abs(path(Book::price))

ceiling(path(Book::price))

exp(path(Book::price))

floor(path(Book::price))

index(path(Book::authors))

ln(path(Book::price))

sign(path(Book::price))

sqrt(path(Book::price))

round(path(Book::price), 2)

size(path(Book::authors))
```

| Function | DSL function |
|----------|--------------|
| MOD      | not yet      |
| POWER    | not yet      |

### Datetime functions

* CURRENT\_DATE (currentDate)
* CURRENT\_TIME (currentTime)
* LOCAL DATE (localDate)
* LOCAL TIME (localTime)
* LOCAL DATETIME (localDateTime)

```kotlin
currentDate()

currentTime()

localDate()

localTime()

localDateTime()
```

| Function           | DSL function |
|--------------------|--------------|
| CURRENT\_TIMESTAMP | not yet      |

### Database function

DB 함수나 사용자 정의 함수를 만들기 위해, `function()`을 사용할 수 있습니다.

```kotlin
function(String::class, "myFunction", path(Book::isbn))
```

{% hint style="info" %}
사용할 함수의 정보를 JPA 제공자에 등록할 필요가 있을 수 있습니다.
예를 들어 Hibernate를 사용하고 있다면 `FunctionContributor`를 반드시 등록해야 합니다.
{% endhint %}

## Cases

case를 만들기 위해, `caseWhen()`과 `caseValue()`를 사용할 수 있습니다.

```kotlin
caseWhen(path(Book::price).lt(BigDecimal.valueOf(100))).then("0")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(200))).then("100")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(300))).then("200")
    .`else`("300")

caseValue(path(Book::price))
    .`when`(BigDecimal.valueOf("100")).then("10")
    .`when`(BigDecimal.valueOf("200")).then("20")
    .`when`(BigDecimal.valueOf("300")).then("30")
    .`else`(path(Book::price))
```

### Coalesce

coalesce를 만들기 위해, `coalesce()`을 사용할 수 있습니다.

```kotlin
coalesce(path(Employee::nickname), path(Employee::name))
```

### NullIf

nullIf를 만들기 위해, `nullIf()`을 사용할 수 있습니다.

```kotlin
nullIf(path(Book::price), BigDecimal.ZERO)
```

## New

DTO 프로젝션을 만들기 위해, `new()`를 사용할 수 있습니다.

```kotlin
data class Row(
    val departmentId: Long,
    val count: Long,
)

new(
    Row::class
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
)
```

## Type

type 연산자를 만들기 위해, `type()`을 사용할 수 있습니다.

```kotlin
select(
    path(Employee::id)
).from(
    entity(Employee::class)
).where(
    type(entity(Employee::class)).eq(FullTimeEmployee::class)
)
```

## Custom expression

커스텀 expression을 만들기 위해, `customExpression()`을 사용할 수 있습니다.

```kotlin
customExpression(String::class, "CAST({0} AS VARCHAR)", path(Book::price))
```

만약 `customExpression()`을 많이 사용한다면 [나만의 DSL](custom-dsl.md)을 만드는 것을 고려해보세요.
