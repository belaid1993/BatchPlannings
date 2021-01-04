package com.belaid.batch;

import com.belaid.batch.domaine.Formation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.listener.StepListenerSupport;

public class ChargementFormationsStepListner extends StepListenerSupport<Formation, Formation> implements StepExecutionListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(ChargementFormationsStepListner.class);

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        LOGGER.info("Chargements des formations :{} formations(s) enregisre(s)" + stepExecution.getWriteCount());
        return stepExecution.getExitStatus();
    }
}
