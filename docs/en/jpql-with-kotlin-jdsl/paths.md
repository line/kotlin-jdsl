# Paths

Kotlin JDSL has `Path` interface to represent a path expression in JPQL.
To build `Path`, you can use `path()` and `invoke()`.

```kotlin
// Book.isbn.value
path(Book::isbn).path(Isbn::value)
path(Book::isbn)(Isbn::value)

// b.isbn.value
entity(Book::class, "b").path(Book::isbn).path(Isbn::value)
entity(Book::class, "b")(Book::isbn)(Isbn::value)
```

## Java entity

`path()` and `invoke()` can take `KProperty1` or `KFuction1` as an argument.
`KFunction1` is useful when you use Java entity with private property and public getter.

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

Kotlin JDSL infers the property name from the getter with the following rules:

- If the name starts with `is`, use the name as it is.
- If the name starts with `get`, remove `get` and change the first letter to lowercase.
- Otherwise, use the name as it is.

```kotlin
// Book.isAvailable
path(Book::isAvailable)

// Book.available
path(Book::getAvailable)
```

If you want to use your own rule instead of the above rules, you can implement `JpqlPropertyIntrospector` and provide it to `RenderContext`.
See [Custom DSL](./custom-dsl.md) for more details.
If you are using Spring, see [Spring supports](./spring-supports.md) also.

```kotlin
class MyIntrospector : JpqlPropertyIntrospector() {
    override fun introspect(property: KCallable<*>): JpqlPropertyDescription? {
        if (property is KFunction1<*, *>) {
            // resolve a name with your own rule
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

`Path` can be used as [`Expression`](expressions.md), such as in a [select clause](statements.md#select-clause) or [predicate](predicates.md).

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

To cast type of `Path` to subclass, you can use `treat()`.

```kotlin
path(EmployeeDepartment::employee).treat(FullTimeEmployee::class)
```
