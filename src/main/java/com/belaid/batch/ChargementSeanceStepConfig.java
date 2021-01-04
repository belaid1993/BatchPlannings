package com.belaid.batch;

import com.belaid.batch.domaine.Seance;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ChargementSeanceStepConfig {

    @Bean
    @StepScope
    public FlatFileItemReader<Seance> seanceFlatFileItemReader(@Value("#{jobParameters['seanceFile']}") final Resource inputFile) {
        return new FlatFileItemReaderBuilder<Seance>()
                .name("seanceFlatFileItemReader")
                .resource(inputFile)
                .delimited()
                .delimiter(",")
                .names(new String[]{"codeFormation", "idFormateur", "dateDebut", "dateFin"})
                .fieldSetMapper(fieldSetMapper(null))
                .build();
    }

    @Bean
    public ConversionService conversionService() {
        DefaultConversionService dcs = new DefaultConversionService();
        DefaultConversionService.addDefaultConverters(dcs);
        dcs.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String s) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyy");
                return LocalDate.parse(s, dtf);
            }
        });

        return dcs;
    }

    @Bean
    public FieldSetMapper<Seance> fieldSetMapper(final ConversionService conversionService){
        BeanWrapperFieldSetMapper<Seance> bean = new BeanWrapperFieldSetMapper<>();
        bean.setTargetType(Seance.class);
        bean.setConversionService(conversionService);
        return bean;
    }

    @Bean
    public JdbcBatchItemWriter<Seance> seanceJdbcBatchItemWriter(final DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Seance>()
                .dataSource(dataSource)
                .sql("INSERT INTO seances(code_formation,id_formateur,date_debut,date_fin) VALUES(?,?,?,?);")
                .itemPreparedStatementSetter(new ItemPreparedStatementSetter<Seance>() {
                    @Override
                    public void setValues(Seance seance, PreparedStatement ps) throws SQLException {
                        ps.setString(1, seance.getCodeFormation());
                        ps.setInt(2, seance.getIdFormateur());
                        ps.setDate(3, Date.valueOf(seance.getDateDebut()));
                        ps.setDate(4, Date.valueOf(seance.getDateFin()));
                    }
                })
                .build();

    }

    @Bean
    public SkipPolicy seanceSkipPolicy(){
        return new SeanceSkipPolicy();
    }

    @Bean
    public Step chargementSeanceStep(final StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("chargementSeanceStep")
                .<Seance, Seance>chunk(10)
                .reader(seanceFlatFileItemReader(null))
                .writer(seanceJdbcBatchItemWriter(null))
                .faultTolerant()
                .skipPolicy(seanceSkipPolicy())
                .listener(chargementSeancesStepListner())
                .build();
    }

    @Bean
    public StepExecutionListener chargementSeancesStepListner() {
        return new ChargementSeancesStepListner();
    }
}
