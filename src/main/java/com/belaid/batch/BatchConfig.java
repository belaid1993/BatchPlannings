package com.belaid.batch;

import com.belaid.batch.validators.MyJobParametersValidator;
import com.belaid.batch.validators.deciders.SeancesStepDecider;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.CompositeJobParametersValidator;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Bean
    public DefaultJobParametersValidator defaultJobParametersValidator() {
        DefaultJobParametersValidator bean = new DefaultJobParametersValidator();
        bean.setRequiredKeys(new String[]{"formateurFile", "formationFile", "seanceFile"});
        bean.setOptionalKeys(new String[]{"run.id"});
        return bean;
    }

    @Bean
    public JobParametersValidator jobParametersValidator() {
        return new MyJobParametersValidator();
    }

    @Bean
    public JobParametersValidator compositeJobParametersValidator() {
        CompositeJobParametersValidator bean = new CompositeJobParametersValidator();
        bean.setValidators(Arrays.asList(defaultJobParametersValidator(), jobParametersValidator()));
        return bean;
    }

    @Bean
    public JobExecutionDecider seancesStepDecider() {
        return new SeancesStepDecider();
    }

    @Bean
    public Flow chargementFormateursFlow(final Step chargementFormateursStep) {
        return new FlowBuilder<Flow>("chargementFormateursFlow")
                .start(chargementFormateursStep)
                .end();
    }

    @Bean
    public Flow chargementFormationFlow(final Step chargementFormationStep){
        return new FlowBuilder<Flow>("chargementFormationFlow")
                .start(chargementFormationStep)
                .end();
    }

    @Bean
    public Flow parallelFlow(){
        return new FlowBuilder<Flow>("parallelFlow")
                .split(new SimpleAsyncTaskExecutor())
                .add(chargementFormateursFlow(null), chargementFormationFlow(null))
                .end();
    }

    @Bean
    public Job myJob(final JobBuilderFactory jobBuilderFactory, final Step chargementSeanceStep, final Step planningStep) {
        return jobBuilderFactory.get("myJob")
                .start(parallelFlow())
                .next(seancesStepDecider()).on("csv").to(chargementSeanceStep)
                .from(chargementSeanceStep).on("*").to(planningStep)
                .end()
                .validator(compositeJobParametersValidator())
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
