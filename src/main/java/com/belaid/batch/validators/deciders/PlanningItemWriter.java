package com.belaid.batch.validators.deciders;

import com.belaid.batch.domaine.Planning;
import com.belaid.batch.services.MailContentGenerator;
import com.belaid.batch.services.PlanningMailSenderService;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class PlanningItemWriter implements ItemWriter<Planning> {

    private PlanningMailSenderService planningMailSenderService;
    private MailContentGenerator mailContentGenerator;

    public PlanningItemWriter(final PlanningMailSenderService planningMailSenderService, final MailContentGenerator mailContentGenerator) {
        super();
        this.planningMailSenderService = planningMailSenderService;
        this.mailContentGenerator = mailContentGenerator;
    }

    @Override
    public void write(List<? extends Planning> list) throws Exception {

        for (Planning planning : list) {
            String content = mailContentGenerator.generate(planning);
            planningMailSenderService.send(planning.getFormateur().getAdresseEmail(), content);
        }

    }
}
