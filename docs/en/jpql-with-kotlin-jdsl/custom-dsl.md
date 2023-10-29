# Custom DSL

## DSL

Inheriting `Jpql` class and adding your own functions allows you to create your own DSL.

`Jpql` has all the DSL functions provided by Kotlin JDSL. You can use them to create your own functions.
You can also create your own `Model` that implements [`Expression`](expressions.md) or [`Predicate`](predicates.md) and create a function to return this Model.
Implementing [`JpqlSerializer`](custom-dsl.md#serializer) for the Model allows Kotlin JDSL to render the Model to a String.

{% hint style="info" %}
You need to implement `JpqlDsl.Constructor` as a companion object so that `jpql()` can recognize the DSL.
{% endhint %}

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

### Serializer

To render your Model to String, you can implement `JpqlSerializer` and provide it to `RenderContext`.

`JpqlSerializer` provides `JpqlWriter` and `RenderContext` for you to implement rendering.
From `RenderContext`, you can get `JpqlRenderSerializer`, which can render other model in your Model.
And you can also get `JpqlRenderStatement` and `JpqlRenderClause`, which let you know which statement and clause are currently being rendered.
You can use them to render your Model as String.

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
