# Customizing

## DSL

By inheriting from the Jpql class, you can create your own DSL functions. Jpql class has all the DSL functions provided by Kotlin JDSL. You can use them to create your own DSL functions.&#x20;

If you want to create the DSL function that does more than the DSL functions provided by Kotlin JDSL, you can create and provide your own Query Model. Your query model just needs to implement [expression](expressions.md) or [predicate](predicates.md) interfaces and provide [JpqlSerializer](customizing.md#serializer) implementation to RenderContext to let the Kotlin JDSL know how to print.

{% hint style="info" %}
You must to implement JpqlDsl.Constructor as a companion object so that jpql function can recognize the DSL class and create an object of it.
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

To print your Query Model, you have to implement JpqlSerializer and provide it to the RenderContext.&#x20;

JpqlSerializer provides JpqlWriter and RenderContext for you to implement serialization. From RenderContext, you can get JpqlRenderSerializer, which can serialize other query parts in your QueryModel. And you can also get JpqlRenderStatement and JpqlRenderClause, which let you know which statement and clause are currently being rendered. You can use them to print your Query Model as a String using JpqlWriter.

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
        writer.write("(")
        delegate.serialize(part.expr, writer, context)
        writer.write(", ")
        delegate.serialize(part.pattern, writer, context)
        writer.write(")")
    }
}
```

JpqlRenderContext provides a registerModule function that allows you to register the JpqlSerializer implementation.

```kotlin
val myModule = object : JpqlRenderModule {
    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        context.addSerializer(MyRegexLikeSerializer())
    }
}

val myContext = JpqlRenderContext().registerModule(myModule)

val jpqQuery = entityManager.createQuery(query, myContext)
```
