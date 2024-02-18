# Custom DSL

## DSL

Create your own DSL just by inheriting `Jpql` class and adding your own functions.

`Jpql` has all the default DSL functions provided by Kotlin JDSL.
You can use them to create your own functions.
You can also create your own `Model` that implements [`Expression`](expressions.md) or [`Predicate`](predicates.md) and create a function to return this Model.
You can implement [`JpqlSerializer`](custom-dsl.md#serializer) to let Kotlin JDSL to render `Model` to String.

There are two ways to pass your own DSL to `jpql()`.
The first is to implement `JpqlDsl.Constructor` as a companion object to create a DSL object, and the second is to create a DSL instance.

### JpqlDsl.Constructor

With this way, you don't need to create an instance and a new instance is automatically created for each query creation.

```kotlin
class MyJpql : Jpql() {
    companion object Constructor : JpqlDsl.Constructor<MyJpql> {
        override fun newInstance(): MyJpql = MyJpql()
    }

    fun myFunction(value: String): Expression<String> {
        return function(String::class, "myFunction", listOf(value(value)))
    }

    fun Expressionable<String>.regexLike(value: String): Predicate {
        return MyRegexLike(this.toExpression(), value)
    }
}

val query = jpql(MyJpql) {
    select(
        entity(Book::class)
    ).from(
        entity(Book::class)
    ).where(
        myFunction("test").regexLike(".*")
    )
}
```

### Jpql Instance

With this way, you can reuse a single instance for query creation and utilize dependency injection.

```kotlin
class MyJpql(
    private val encryptor: Encryptor,
) : Jpql() {
    fun Path<String>.equalToEncrypted(value: String): Predicate {
        val encrypted = encryptor.encrypt(value)
        return this.eq(encrypted)
    }
}

val encryptor = Encryptor()
val instance = MyJpql(encryptor)

val query = jpql(instance) {
    select(
        entity(Book::class)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::title).equalToEncrypted("plain")
    )
}
```

### Serializer

Implement `JpqlSerializer` and add it to `RenderContext`  to render your own `Model` to String.

`JpqlSerializer` provides `JpqlWriter` and `RenderContext` for you to implement rendering.
You can get `JpqlRenderSerializer` from `RenderContext` and use it to render other `Model` in your `Model`.
You can also get `JpqlRenderStatement` and `JpqlRenderClause` from `RenderContext`, which let you know which statement and clause are currently being rendered.
You can use them to render your `Model` as String.

```kotlin
class MyRegexLikeSerializer : JpqlSerializer<MyRegexLike> {
    override fun handledType(): KClass<MyRegexLike> {
        return MyRegexLike::class
    }

    override fun serialize(part: MyRegexLike, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

//        val statement = context.getValue(JpqlRenderStatement)
//        val clause = context.getValue(JpqlRenderClause)
//
//        statement.isSelect()
//        clause.isWhere()

        writer.write("REGEXP_LIKE")
        writer.writeParentheses {
            delegate.serialize(part.expr, writer, context)
            writer.write(", ")
            delegate.serialize(part.pattern, writer, context)
        }
    }
}
```

`JpqlRenderContext` provides `registerModule()` that allows you to register the `JpqlSerializer` implementation.

```kotlin
val myModule = object : JpqlRenderModule {
    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        context.addSerializer(MyRegexLikeSerializer())
    }
}

val myContext = JpqlRenderContext().registerModule(myModule)

val jpqQuery = entityManager.createQuery(query, myContext)
```
