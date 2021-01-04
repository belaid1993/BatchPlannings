package com.belaid.batch;

import com.belaid.batch.domaine.Seance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.listener.StepListenerSupport;

public class ChargementSeancesStepListner extends StepListenerSupport<Seance, Seance> implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChargementSeancesStepListner.class);

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Seance(s) enregisre(s) " + stepExecution.getWriteCount());
        return stepExecution.getExitStatus();
    }
}
