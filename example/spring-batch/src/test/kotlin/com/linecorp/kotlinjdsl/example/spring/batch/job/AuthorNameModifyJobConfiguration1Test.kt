package com.linecorp.kotlinjdsl.example.spring.batch.job

import com.linecorp.kotlinjdsl.example.spring.batch.entity.author.Author
import com.linecorp.kotlinjdsl.example.spring.batch.repository.author.AuthorRepository
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.test.JobLauncherTestUtils
import org.springframework.batch.test.context.SpringBatchTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@DirtiesContext
@SpringBatchTest
@SpringBootTest(properties = ["spring.batch.job.names=${AuthorNameModifyJobConfiguration1.JOB_NAME}"])
class AuthorNameModifyJobConfiguration1Test : WithAssertions {
    @Suppress("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private lateinit var jobLauncherTestUtils: JobLauncherTestUtils

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Test
    fun `modify name of authors who have id greater or equal than criteria author id`() {
        // given
        val criteriaAuthorId = 3L
        val jobParameters = JobParametersBuilder()
            .addLong("authorId", criteriaAuthorId)
            .toJobParameters()

        // when
        val jobExecution = jobLauncherTestUtils.launchJob(jobParameters)

        val authors = authorRepository.findAll()

        // then
        assertThat(jobExecution.status).isEqualTo(BatchStatus.COMPLETED)
        assertThat(authors).extracting(Author::name.name)
            .containsExactlyInAnyOrder(
                "Author01",
                "Author02",
                "Author030",
                "Author040",
            )
    }
}
