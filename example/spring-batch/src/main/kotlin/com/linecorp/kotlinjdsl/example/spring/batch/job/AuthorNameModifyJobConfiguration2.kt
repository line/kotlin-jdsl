package com.linecorp.kotlinjdsl.example.spring.batch.job

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.spring.batch.entity.author.Author
import com.linecorp.kotlinjdsl.example.spring.batch.job.AuthorNameModifyJobConfiguration2.Companion.JOB_NAME
import com.linecorp.kotlinjdsl.support.spring.batch.item.database.orm.KotlinJdslQueryProviderFactory
import jakarta.persistence.EntityManagerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import org.springframework.transaction.PlatformTransactionManager

@Configuration
@ConditionalOnProperty(name = ["spring.batch.job.names"], havingValue = JOB_NAME)
class AuthorNameModifyJobConfiguration2(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val entityManagerFactory: EntityManagerFactory,
    private val kotlinJdslQueryProviderFactory: KotlinJdslQueryProviderFactory,
    private val jobParameters: AuthorNameModifyJobParameters2,
) {
    companion object {
        const val JOB_NAME = "author-name-modify-job-2"
        private const val STEP_NAME = "author-name-modify-step"
        private const val CHUNK_SIZE = 100
    }

    @Bean(JOB_NAME)
    fun authorNameModifyJob(): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(authorNameModifyStep())
            .build()
    }

    @Bean(STEP_NAME)
    fun authorNameModifyStep(): Step {
        return StepBuilder(STEP_NAME, jobRepository)
            .chunk<Author, Author>(CHUNK_SIZE, transactionManager)
            .reader(authorNameModifyReader())
            .processor(authorNameModifyProcessor())
            .writer(authorNameModifyWriter())
            .build()
    }

    @Bean
    @StepScope
    fun authorNameModifyReader(): JpaCursorItemReader<Author> {
        val query = jpql {
            select(
                entity(Author::class),
            ).from(
                entity(Author::class),
            ).where(
                path(Author::authorId).ge(param("authorId")),
            )
        }
        val queryParams = mapOf("authorId" to jobParameters.authorId)

        val kotlinJdslQueryProvider = kotlinJdslQueryProviderFactory.createKotlinJdslQueryProvider(query, queryParams)

        return JpaCursorItemReaderBuilder<Author>()
            .entityManagerFactory(entityManagerFactory)
            .queryProvider(kotlinJdslQueryProvider)
            .saveState(false)
            .build()
    }

    @Bean
    fun authorNameModifyProcessor(): ItemProcessor<Author, Author> {
        return ItemProcessor { author ->
            author.apply { name = "${name}0" }
        }
    }

    @Bean
    fun authorNameModifyWriter(): JpaItemWriter<Author> {
        return JpaItemWriterBuilder<Author>()
            .entityManagerFactory(entityManagerFactory)
            .build()
    }
}

@JobScope
@Component
@ConditionalOnProperty(name = ["spring.batch.job.names"], havingValue = JOB_NAME)
data class AuthorNameModifyJobParameters2(
    @Value("#{jobParameters[authorId]}")
    val authorId: Long,
)
