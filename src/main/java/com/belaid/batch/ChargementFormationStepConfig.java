package com.belaid.batch;

import com.belaid.batch.domaine.Formation;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class ChargementFormationStepConfig {

    @Bean
    @StepScope
    public StaxEventItemReader<Formation> formationStaxEventItemReader(@Value("#{jobParameters['formationFile']}") final Resource inputFile) {
        return new StaxEventItemReaderBuilder<Formation>()
                .name("formationStaxEventItemReader")
                .resource(inputFile)
                .addFragmentRootElements("formation")
                .unmarshaller(jaxb2Marshaller())
                .build();

    }

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller bean = new Jaxb2Marshaller();
        bean.setClassesToBeBound(Formation.class);
        return bean;
    }

    @Bean
    public JdbcBatchItemWriter<Formation> formationItemWriter(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Formation>()
                .dataSource(dataSource)
                .sql("INSERT INTO formations(code,libelle,descriptif) VALUES(?,?,?);")
                .itemPreparedStatementSetter(new ItemPreparedStatementSetter<Formation>() {
                    @Override
                    public void setValues(Formation formation, PreparedStatement ps) throws SQLException {
                        ps.setString(1, formation.getCode());
                        ps.setString(2, formation.getLibelle());
                        ps.setString(3, formation.getDescriptif());
                    }
                })
                .build();
    }

    @Bean
    public Step chargementFormationStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("chargementFormationStep")
                .<Formation, Formation>chunk(10)
                .reader(formationStaxEventItemReader(null))
                .writer(formationItemWriter(null))
                .listener(chargementFormationsStepListner())
                .build();
    }

    @Bean
    public StepExecutionListener chargementFormationsStepListner(){
        return new ChargementFormationsStepListner();
    }
}
