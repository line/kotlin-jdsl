# Paths

Kotlin JDSL은 JPQL의 path expression을 표현하기 위해서 `Path` 인터페이스를 가지고 있습니다.
`Path`를 만들기 위해, `path()` 와 `invoke()`를 사용할 수 있습니다.

```kotlin
// Book.isbn.value
path(Book::isbn).path(Isbn::value)
path(Book::isbn)(Isbn::value)

// b.isbn.value
entity(Book::class, "b").path(Book::isbn).path(Isbn::value)
entity(Book::class, "b")(Book::isbn)(Isbn::value)
```

## Java entity

`path()` 와 `invoke()`는 `KProperty1` 또는 `KFuction1`를 인자로 받습니다.
`KFunction1`의 경우, getter만 public인 Java로 선언한 entity를 사용할 때 유용합니다.

```java
@Entity
public class Book {
    @Id
    private Long id;

    private String title;

    public String getTitle() {
        return title;
    }
}
```

```kotlin
// compile error
path(Book::title)

// Book.title
path(Book::getTitle)
```

Kotlin JDSL은 getter 이름에서 프로퍼티 이름을 추론하기 위해 다음 규칙을 따릅니다.

- `is`로 시작하는 경우 이름 그대로 사용합니다.
- `get`으로 시작하는 경우 `get`을 제거하고 이후 첫 글자를 소문자로 변경합니다.
- 그 외의 경우, 이름 그대로 사용합니다.

```kotlin
// Book.isAvailable
path(Book::isAvailable)

// Book.available
path(Book::getAvailable)
```

위 규칙 대신 나만의 규칙을 사용하고 싶다면, `JpqlPropertyIntrospector`를 구현하고 이를 이를 `RenderContext`에 추가해야 합니다.

```kotlin
class MyIntrospector : JpqlPropertyIntrospector() {
    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? {
        if (property is KFunction1<*, *>) {
            // 나만의 규칙으로 이름을 추론합니다
            val name = ...
            return MyProperty(name)
        }

        return null
    }

    private data class MyProperty(
        override val name: String,
    ) : JpqlPropertyDescription
}

val myModule = object : JpqlRenderModule {
    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        context.prependIntrospector(MyIntrospector())
    }
}

val myContext = JpqlRenderContext().registerModule(myModule)
```

## Expression

`Path`는 [select clause](statements.md#select-clause) 나 [predicate](predicates.md) 등에서 [`Expression`](expressions.md)으로 사용될 수 있습니다.

```kotlin
// SELECT Book.isbn FROM Book AS Book WHERE Book.isbn.value = :param1
jpql {
    select(
        path(Book::isbn),
    ).from(
        entity(Book::class),
    ).where(
        path(Book::isbn)(Ibsn::value).eq("01"),
    )
}
```

## Treat

`Path`의 타입을 자식 타입으로 변경하기 위해, `treat()`를 사용할 수 있습니다.

```kotlin
path(EmployeeDepartment::employee).treat(FullTimeEmployee::class)
```
