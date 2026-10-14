# AGENTS.md

Guide for AI coding agents working in the **Kotlin JDSL** repository. Read this before making changes.

Every statement below was verified against the repository files at the time of writing. This file is documentation, not configuration: if anything here conflicts with the actual build files, the build files win — trust them, and update this document as part of your change. If you modify build scripts, CI workflows, or the module layout, update the affected sections here in the same PR.

## Quick Reference

```bash
./gradlew test                # all tests (example integration tests included)
./gradlew lintKotlin          # ktlint — CI and the pre-commit hook run this
./gradlew formatKotlin        # auto-fix when lint fails
./gradlew :<module>:test      # one module, e.g. :spring-data-jpa-support
```

Non-negotiables (details below):

- Core modules stay Java 8; do not bump their toolchain or Kotlin API.
- Framework dependencies in `support/*` stay `compileOnly`.
- Conventional Commits only — commitlint enforces this in CI.
- PRs target `develop`; pushes there publish a snapshot.
- Public API carries `@SinceJdsl` (or `@Experimental` / `@Internal`).

## Project Overview

Kotlin JDSL is a Kotlin library for building and executing queries without an annotation processor or a generated metamodel.
Queries are built from `KClass` and `KProperty` references (e.g. `Book::title`), so no code regeneration is needed when entities change.

- Publisher: LY Corporation. Group id: `com.linecorp.kotlin-jdsl`. License: Apache 2.0.
- The library version is declared in the root `build.gradle.kts` and applies to every project; four Boot 4 / Batch 6 modules (`support/spring-data-jpa-boot4`, `support/spring-batch6`, `example/spring-data-jpa-boot4`, `example/spring-batch6`) re-declare the same value because they apply their own plugin blocks. Always read the current value from the root `build.gradle.kts` and never introduce a different one.
- Runtime requirements for library users: Java 8 or later, Kotlin 2.2 or later.
- The library itself is built with the Kotlin version declared in `libs.versions.toml`.
- The canonical repository is [line/kotlin-jdsl](https://github.com/line/kotlin-jdsl); remote names (`origin`, `upstream`) depend on each contributor's checkout, so never assume them.

## Repository Layout

The project is a multi-module Gradle build with a strict three-layer core, plus per-framework integration modules:

```
src/                 # Root module: shared utils (IterableUtils, ClassUtils, PropertyUtils,
                     #   SuperClassDepthComparator)
                     # and API annotations (@SinceJdsl, @Experimental, @Internal)
dsl/jpql             # :jpql-dsl        Layer 1 - fluent DSL (query builders)
query-model/jpql     # :jpql-query-model Layer 2 - immutable query model
render/jpql          # :jpql-render     Layer 3 - query rendering (JPQL string + params)
support/*            # Execution support per framework (see table below)
example/*            # Examples with H2 integration tests (not published); spring-* are runnable
                     #   Spring Boot apps (Application.kt), the hibernate/eclipselink/hibernate-reactive
                     #   ones are exercised as JUnit tests that build an EntityManagerFactory/SessionFactory
                     #   from src/testFixtures
benchmark/           # JMH benchmarks via kotlinx-benchmark (not published)
docs/en, docs/ko     # GitBook documentation sources (English and Korean)
```

`dsl`, `query-model`, `render`, `support`, and `example` are also real Gradle projects (`:dsl`, `:query-model`, `:render`, `:support`, `:example`) holding shared code and config; the framework-specific modules sit under them (e.g. example modules depend on `projects.example`). Every subproject also depends implicitly on the root module (`implementation(rootProject)` in the root `subprojects` block), so the root `src/` utils and annotations resolve everywhere without a declared dependency.

`settings.gradle.kts` maps logical Gradle project names to these directories (e.g. `:spring-data-jpa-support` -> `support/spring-data-jpa`).
Version catalogs: `libs.versions.toml` (main), `libs.example.versions.toml` (examples), `libs.example.legacy.versions.toml` (javax-era examples).
`TYPESAFE_PROJECT_ACCESSORS` is enabled, so cross-module dependencies use `projects.jpqlDsl`, `projects.jpqlQueryModel`, `projects.jpqlRender`, `projects.springDataJpaSupport`, etc.

### Layer architecture and dependency direction

```
dsl/jpql (:jpql-dsl) ────────► query-model (:query-model, :jpql-query-model)
render/jpql (:jpql-render) ──► query-model
support/* ──► jpql-dsl + jpql-query-model + jpql-render (compileOnly; the Boot 4 / Batch 6 support
                  modules use implementation for the core modules)
example/* ──► core modules + one support module
benchmark ──► :jpql-dsl + :jpql-render only (it benchmarks the DSL and rendering, not the support layer)
```

1. **DSL layer** (`dsl/jpql`): step interfaces (`SelectQueryFromStep`, `JoinOnStep`, `CaseWhenStep`, ...) at package level and concrete builders in `impl/` subpackages (e.g. `SelectQueryFromStepDsl`). Entry point is the `jpql { ... }` function backed by the `Jpql` class; custom DSLs implement the `JpqlDsl` interface and expose a companion object implementing `JpqlDsl.Constructor`.
2. **Query model layer** (`query-model/jpql`): immutable data classes describing a query (`SelectQuery`, `UpdateQuery`, `DeleteQuery`, `Expression`, `Predicate`, `Path`, `Sort`, ...). Implementations live in `impl/` subpackages prefixed with `Jpql` (e.g. `JpqlFloor`, `JpqlBetween`). Factory objects (`Entities`, `Paths`, `Expressions`, `Predicates`, `Sorts`, `SelectQueries`, `UpdateQueries`, `DeleteQueries`) are the only supported way to create parts.
3. **Render layer** (`render/jpql`): `JpqlRenderer` turns a query model into a `JpqlRendered` (query string + params). `JpqlRenderContext` is a key/element context (like `kotlinx.coroutines` `CoroutineContext`, supports `plus`/`fold`). Rendering is table-driven: one `JpqlSerializer<T>` per model type in `serializer/impl/` (e.g. `JpqlFloorSerializer`), plus `JpqlIntrospector`s for entity metadata (`JakartaJpqlIntrospector`, `JavaxJpqlIntrospector`, `KotlinStyleJpqlPropertyIntrospector`). Custom serializers/introspectors are registered through `JpqlRenderModule` (`addSerializer`, `prependIntrospector`, ...).

### Support modules

| Module | Gradle name | Framework | JVM toolchain |
|---|---|---|---|
| `support/spring-data-jpa` | `:spring-data-jpa-support` | Spring Data JPA 3 / Boot 3 (jakarta) | 17 |
| `support/spring-data-jpa-boot4` | `:spring-data-jpa-boot4-support` | Spring Boot 4 | 17 |
| `support/spring-batch` | `:spring-batch-support` | Spring Batch 5 / Boot 3 | 17 |
| `support/spring-batch6` | `:spring-batch6-support` | Spring Batch 6 / Boot 4 | 17 |
| `support/hibernate` | `:hibernate-support` | Hibernate ORM 6 (jakarta) | 11 |
| `support/hibernate-reactive` | `:hibernate-reactive-support` | Hibernate Reactive 2 (jakarta) | 11 |
| `support/eclipselink` | `:eclipselink-support` | EclipseLink 3 (jakarta) | 8 |
| `support/*-javax` | `:*-javax-support` | javax.persistence variants (Boot 2, Spring Data JPA 2, Hibernate 5, Hibernate Reactive 1, EclipseLink 2, Batch 4) | 8 (11 for hibernate-reactive) |

Every framework dependency in `support/*` is `compileOnly`; the same artifacts appear again as `testImplementation`. This is intentional: users bring their own framework version, so never change these to `implementation`.

## Build, Test, Lint Commands

Run everything from the repository root with the Gradle wrapper:

```bash
./gradlew build                    # assemble + test all modules
./gradlew test                     # all module tests, including the example integration tests
./gradlew :jpql-dsl:test           # single module (also :jpql-query-model, :jpql-render,
                                   #   :spring-data-jpa-support, :spring-data-jpa-boot4-support, ...)
./gradlew lintKotlin               # ktlint check (same as CI and the pre-commit hook)
./gradlew formatKotlin             # auto-format Kotlin sources
./gradlew koverXmlReport           # coverage XML (CI uploads this to Codecov)
./gradlew koverHtmlReport          # coverage HTML report
./gradlew :benchmark:benchmark     # run JMH benchmarks
```

Notes:

- CI runs on JDK 17 (`actions/setup-java`, distribution `adopt`; `.github/workflows/test.yaml` is the source of truth). Core modules still target Java 8 through Gradle toolchains; the foojay resolver auto-provisions the needed JDKs, so a local JDK 8/11/17 mix is not required.
- Toolchain policy: core modules (`:dsl`, `:query-model`, `:render`, root) must stay Java 8 compatible. Only support, example, and benchmark modules may raise the toolchain (11 or 17). Do not bump the core toolchain.
- Most example modules and the benchmark module pin Kotlin `apiVersion`/`languageVersion` to 1.9 (the Boot 4 and Batch 6 examples do not); they exist for manual verification — see "Things to Avoid" for their publish policy.
- Tests use the JUnit Platform; failures print full stack traces (configured in root `build.gradle.kts`).
- Gradle is configured with `org.gradle.parallel=true`, `org.gradle.workers.max=4`, `-Xmx2G` (see `gradle.properties`).

## Coding Conventions

- Kotlin official code style (enforced by kotlinter/ktlint and `.editorconfig`): 4-space indent, max line length 120 (300 for Markdown), trailing commas allowed, import layout `*, java.**, javax.**, kotlin.**, ^`, no wildcard imports.
- **API stability annotations are mandatory on public API** (defined in the root `src/main`):
  - `@SinceJdsl("<major>.<minor>[.<patch>]")` on every public declaration, stating the first JDSL version it appeared in.
  - `@Experimental` for declarations whose design may still change.
  - `@Internal` for declarations usable only inside `com.linecorp.kotlinjdsl` (no compatibility guarantees).
- Structural pattern, consistent across layers:
  - Public interfaces and factory objects at package level; implementations in an `impl/` subpackage.
  - Query-model implementations are named `Jpql<Concept>`; renderers `Jpql<Concept>Serializer`; DSL builders `<Concept>Dsl` implementing `<Concept>Step` interfaces.
- Query-model classes are immutable data classes; DSL tests rely on structural equality, so keep `equals`/`hashCode` (generated by `data class`) intact.
- Compiler flags set for all modules: `-Xallow-kotlin-package` (the DSL needs `kotlin.internal.Exact`), `-Xconsistent-data-class-copy-visibility`, and `jvmTargetValidationMode = ERROR`.
- Root `build.gradle.kts` applies kotlin-jvm, kover, kotlinter, `java-test-fixtures`, `maven-publish`, and `signing` to **all** projects. Prefer test fixtures over copy-pasted test helpers.

### Adding a new JPQL feature (the full checklist)

A new expression/predicate/sort feature touches all three layers. Follow the existing examples (e.g. `FLOOR`):

1. `query-model/jpql`: `Jpql*` impl in `impl/` annotated `@Internal`, implementing the existing `Expression` / `Predicate` / `Sort` interface where applicable (simple features like `floor` add no new interface); a factory function in the corresponding factory object (`Expressions`, `Predicates`, `Sorts`) annotated `@SinceJdsl`.
2. `render/jpql`: `Jpql*Serializer` in `serializer/impl/`, registered in the private `DefaultModule` of `JpqlRenderContext.kt` — a private class at the end of that file that registers every serializer in a single `addAllSerializer(...)` call and appends `JavaxJpqlIntrospector`/`JakartaJpqlIntrospector` depending on which persistence API is on the classpath, plus `KotlinStyleJpqlPropertyIntrospector`. A serializer that is not added there will not be found at render time.
3. `dsl/jpql`: expose the feature inside `jpql { ... }`. Expression/predicate helpers (like `floor`) are methods on the `Jpql` class delegating to the factory; statement/join/case-style builders use the `*Step` interfaces and `*Dsl` impls.
4. Tests at every layer (see next section), plus an example usage if it demonstrates user-facing behavior.
5. Documentation in **both** `docs/en` and `docs/ko` when user-facing, registering any new page in `docs/en/SUMMARY.md` and `docs/ko/SUMMARY.md` (the GitBook table of contents — a page missing from there is unreachable).

## Testing Conventions

Stack: JUnit Jupiter (5 by default; the Boot 4 / Batch 6 support modules use JUnit 6), mockk (`MockKExtension`, `@MockK`), AssertJ (tests implement `WithAssertions`). Kover excludes `com.linecorp.kotlinjdsl.example.*`, `com.linecorp.kotlinjdsl.benchmark.*` and the `testFixtures` source set.

- **Test structure**: `// when` and `// then` comment markers in test methods; add `// given` when setup is local to the method (fixtures are often declared at class level instead).
- **Naming**: behavior sentences in backticks for method-level tests (`` fun `createQuery() with a select query`() ``); class names `XxxTest`. Regressions for GitHub issues are named `Issue<number>Test` / `Issue<number>` (e.g. `Issue1074Test`) and live in the module that reproduces them (often an example module).
- **DSL unit tests** (`dsl/jpql/src/test`): build the part with the `queryPart { ... }` helper (test fixture `QueryPartExtensions.kt`), convert it with the type-specific `toQuery()` / `toExpression()` / `toPredicate()` / `toSort()` method (see `FloorDslTest`), then assert equality against the same part built with query-model factories (`SelectQueries.selectQuery(...)`, `Expressions.floor(...)`, ...).
- **Serializer tests** (`render/jpql/src/test`): annotate the class with `@JpqlSerializerTest` (combines `MockKExtension` and `JpqlSerializerExtension`, which auto-stubs `JpqlWriter`/`JpqlRenderSerializer`/`JpqlRenderIntrospector` mocks). Assert `handledType()` and the exact write order with `verifySequence { writer.write("FLOOR"); writer.writeParentheses(any()); ... }`.
- **Support unit tests**: mockk the framework types (`EntityManager`, `TypedQuery`, ...), stub with `every { ... } returns ...`, verify with `verifySequence { ... }`.
- **Integration tests** live in `example/*` (not in support modules). The spring-data-jpa examples use `@SpringBootTest` + `@Transactional` against H2 with repositories extending `KotlinJdslJpqlExecutor` (see `example/spring-data-jpa/.../Issue1074Test.kt`); the hibernate / eclipselink / hibernate-reactive examples drive an `EntityManagerFactory` (or reactive `SessionFactory`) built in their `src/testFixtures` instead of a Spring context.
- Shared entities and helpers live in each module's `src/testFixtures/kotlin` (Book, Author, Department, Employee, Publisher).

## Commit and Pull Request Conventions

- **Conventional Commits, enforced by commitlint in CI** (`.github/workflows/lint.yaml` checks every commit in the PR range). Allowed types: `build`, `chore`, `ci`, `docs`, `feat`, `fix`, `perf`, `refactor`, `revert`, `style`, `test`. Rules: lowercase type, header <= 100 chars, body and footer lines <= 100 chars, subject not in sentence-case/start-case/pascal-case/upper-case, no trailing period in the subject. Example: `docs: add AGENTS.md guide for coding agents`.
- The Gradle build installs a `pre-commit` hook from `.githook/` (OS-specific suffixes) that runs `./gradlew lintKotlin`. If a commit is rejected, run `./gradlew formatKotlin` and retry. The hook directory is reinstalled on every Gradle invocation.
- PR template (`.github/PULL_REQUEST_TEMPLATE.md`): Motivation / Modifications / Commit Convention / Result / Closes #issue.
- Non-trivial changes (beyond typos) require signing the ICLA; contact `dl_oss_dev@linecorp.com` for a CCLA (see `CONTRIBUTING.md`).
- Target branch for PRs: `develop` (the integration branch). `main` holds released versions.

## CI/CD and Release

Workflows in `.github/workflows`:

- `test.yaml` — on push to `main`/`develop` and on every PR: JDK 17, `./gradlew test koverXmlReport`, upload coverage to Codecov.
- `lint.yaml` — on PRs to `main`/`develop`: commitlint (Node 24, `npm install` first) and `./gradlew lintKotlin`.
- `publish.yaml` — publishes to Maven Central (Sonatype): a push to `develop` publishes a snapshot; a published GitHub release publishes the release. Runs `./gradlew --max-workers=1 publish closeAndReleaseStagingRepository` with Sonatype credentials and GPG signing keys from repository secrets.

Consequences:

- Do not push to `develop` casually — it triggers a snapshot publication.
- Every published module signs artifacts and produces sources/javadoc jars (configured in root `build.gradle.kts`).
- Renovate (`renovate.json`, base branch `develop`) only automates updates in `libs.example.versions.toml`; the main `libs.versions.toml` and `libs.example.legacy.versions.toml` are explicitly disabled, so core/framework version bumps are manual. Most `chore(deps)` commits on `develop` come from Renovate.

## Compatibility Matrix

Version ranges the support modules are built and tested against are defined in `libs.versions.toml`:

- Persistence APIs: `javax.persistence` 2.2 and `jakarta.persistence` 3.0 / 3.2.
- Hibernate ORM 5 (javax) / 6+ (jakarta); Hibernate Reactive 1 (javax) / 2+ (jakarta); EclipseLink 2 (javax) / 3 (jakarta).
- Spring Boot 2 (javax examples) / 3 / 4; Spring Batch 4 / 5 / 6; Spring Data JPA 2 / 3.

When touching a support module, check which era it targets (jakarta vs javax) and mirror the existing dependency style exactly.

## Documentation

- `docs/en/` and `docs/ko/` are the GitBook sources (published at https://kotlin-jdsl.gitbook.io/docs/). They cover JPQL usage (entities, paths, expressions, predicates, sorts, statements, subqueries, custom DSL), spring/hibernate-reactive supports, FAQ, the roadmap, and the 2.x -> 3.x migration guide.
- User-facing behavior changes should update the docs; keep English and Korean pages in sync.
- README.md links to GitBook, Discord, and the issue tracker; it is intentionally minimal.

## Things to Avoid

- Do not add `implementation` framework dependencies to `support/*` — they are `compileOnly` by design.
- Do not raise the JVM toolchain or Kotlin API of the core modules (`dsl`, `query-model`, `render`, root `src`) — the library must keep running on Java 8 / Kotlin 2.2.
- Do not publish from `example/*` or `benchmark` (their publish tasks are disabled; keep it that way).
- Do not create query-model parts outside the factory objects; DSL and render layers depend on the exact `Jpql*` types for serialization.
- Do not skip `@SinceJdsl` on new public API; the annotation is part of the API contract.
- Do not edit `.git/hooks` directly — hooks are (re)generated from `.githook/` by the Gradle build.
