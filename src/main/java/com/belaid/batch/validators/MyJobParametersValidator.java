package com.belaid.batch.validators;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.util.StringUtils;

public class MyJobParametersValidator implements JobParametersValidator {
    @Override
    public void validate(JobParameters jobParameters) throws JobParametersInvalidException {
        if (!StringUtils.endsWithIgnoreCase(jobParameters.getString("formateurFile"),"csv")){
            throw new JobParametersInvalidException("le fichier de formateur doit etre au format csv");
        }
        if (!StringUtils.endsWithIgnoreCase(jobParameters.getString("formationFile"),"xml")){
            throw new JobParametersInvalidException("le fichier de formation doit etre au format xml");
        }
        if (!StringUtils.endsWithIgnoreCase(jobParameters.getString("seanceFile"),"csv")){
            throw new JobParametersInvalidException("le fichier de seance doit etre au format csv");
        }
    }
}
