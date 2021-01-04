package com.belaid.batch;


import com.belaid.batch.domaine.Formateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.listener.StepListenerSupport;

import java.util.concurrent.ForkJoinPool;


public class ChargementFormateursStepListner extends StepListenerSupport<ForkJoinPool, Formateur> implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargementFormateursStepListner.class);

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Chargements des formateurs :{} formateur(s) enregisre(s) " + stepExecution.getReadCount());
        return stepExecution.getExitStatus();
    }
}
