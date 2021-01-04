package com.belaid.batch.services;

import com.belaid.batch.domaine.Planning;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

public class MailContentGeneratorImpl implements MailContentGenerator {

    private Template template;

    public MailContentGeneratorImpl(final Configuration configuration) throws IOException {
        super();
        template = configuration.getTemplate("planning.ftl");
    }

    @Override
    public String generate(final Planning planning) throws IOException, TemplateException {

        StringWriter sw = new StringWriter();
        template.process(planning, sw);
        return sw.toString();
    }
}
