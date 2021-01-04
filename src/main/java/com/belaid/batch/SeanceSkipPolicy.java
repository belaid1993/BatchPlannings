package com.belaid.batch;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.dao.DataIntegrityViolationException;

public class SeanceSkipPolicy implements SkipPolicy {
    @Override
    public boolean shouldSkip(Throwable t, int i) throws SkipLimitExceededException {
        if (t instanceof DataIntegrityViolationException && i < 10) {
            return true;
        }
        return false;
    }
}
