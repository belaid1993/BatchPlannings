package com.belaid.batch.services;

import com.belaid.batch.domaine.Planning;
import freemarker.template.TemplateException;

import java.io.IOException;

public interface MailContentGenerator {

    String generate(Planning planning) throws IOException, TemplateException;
}
