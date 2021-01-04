package com.belaid.batch;

import com.belaid.batch.domaine.Formateur;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Configuration
public class ChargementFormateurStepConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<Formateur> formateurItemReader(@Value("#{jobParameters['formateurFile']}") final Resource inputFile) {
        return new FlatFileItemReaderBuilder<Formateur>()
                .name("formateurItemReader")
                .resource(inputFile)
                .delimited()
                .delimiter(",")
                .names(new String[]{"id", "nom", "prenom", "adresseEmail"})
                .targetType(Formateur.class)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Formateur> formateurItemWriter(final DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Formateur>()
                .dataSource(dataSource)
                .sql("INSERT INTO formateurs(id,nom,prenom,adresse_email) VALUES(?,?,?,?);")
                .itemPreparedStatementSetter(new ItemPreparedStatementSetter<Formateur>() {
                    @Override
                    public void setValues(Formateur formateur, PreparedStatement ps) throws SQLException {
                        ps.setInt(1,formateur.getId());
                        ps.setString(2,formateur.getNom());
                        ps.setString(3,formateur.getPrenom());
                        ps.setString(4,formateur.getAdresseEmail());
                    }
                })
                .build();
    }

    @Bean
    public Step chargementFormateursStep(final StepBuilderFactory stepBuilderFactory){

        return stepBuilderFactory.get("chargementFormateursStep")
                .<Formateur, Formateur>chunk(10)
                .reader(formateurItemReader(null))
                .writer(formateurItemWriter(null))
                .listener(chargementFormateursStepListner())
                .build();

    }

    @Bean
    public StepExecutionListener chargementFormateursStepListner(){
        return new ChargementFormateursStepListner();
    }

}
