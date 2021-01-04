package com.belaid.batch;

import com.belaid.batch.domaine.Formateur;
import com.belaid.batch.domaine.Planning;
import com.belaid.batch.services.MailContentGenerator;
import com.belaid.batch.services.MailContentGeneratorImpl;
import com.belaid.batch.services.PlanningMailSenderService;
import com.belaid.batch.services.PlanningMailSenderServiceImpl;
import com.belaid.batch.validators.PlanningProccesor;
import com.belaid.batch.validators.deciders.PlanningItemWriter;
import freemarker.template.Configuration;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@org.springframework.context.annotation.Configuration
public class PlanningStepConfig {

    @Bean(destroyMethod = "")
    public JdbcCursorItemReader<Planning> jdbcCursorItemReader(final DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Planning>()
                .name("jdbcCursorItemReader")
                .dataSource(dataSource)
                .sql("SELECT DISTINCT f.* FROM formateurs f JOIN seances s ON f.id=s.id_formateur")
                .rowMapper(new RowMapper<Planning>() {
                    @Override
                    public Planning mapRow(ResultSet rs, int i) throws SQLException {
                        Planning planning = new Planning();
                        Formateur formateur = new Formateur();

                        formateur.setId(rs.getInt("id"));
                        formateur.setNom(rs.getString("nom"));
                        formateur.setPrenom(rs.getString("prenom"));
                        formateur.setAdresseEmail(rs.getString("adresse_email"));

                        planning.setFormateur(formateur);

                        return planning;
                    }
                })
                .build();
    }

    @Bean
    public ItemProcessor<Planning, Planning> planningItemProcessor(final NamedParameterJdbcTemplate jdbcTemplate) {
        return new PlanningProccesor(jdbcTemplate);
    }

    @Bean
    public MailContentGenerator mailContentGenerator(final Configuration config) throws IOException {
        return new MailContentGeneratorImpl(config);
    }

    @Bean
    public PlanningMailSenderService planningMailSenderService(final JavaMailSender javaMailSender) {
        return new PlanningMailSenderServiceImpl(javaMailSender);
    }

    @Bean
    public ItemWriter<Planning> planningItemWriter(final PlanningMailSenderService planningMailSenderService, final MailContentGenerator mailContentGenerator) {
        return new PlanningItemWriter(planningMailSenderService, mailContentGenerator);
    }

    @Bean
    public Step planningStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("planningStep")
                .<Planning, Planning>chunk(10)
                .reader(jdbcCursorItemReader(null))
                .processor(planningItemProcessor(null))
                .writer(planningItemWriter(null, null))
                .build();
    }
}
