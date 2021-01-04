package com.belaid.batch.validators;

import com.belaid.batch.domaine.Planning;
import com.belaid.batch.domaine.PlanningItem;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanningProccesor implements ItemProcessor<Planning, Planning> {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private static final String QUERY = "select f.libelle, s.date_debut,s.date_fin"
            + " from formations f join seances s on f.code=s.code_formation"
            + " where s.id_formateur=:formateur"
            + " order by s.date_debut";

    public PlanningProccesor(final NamedParameterJdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Planning process(final Planning planning) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("formateur", planning.getFormateur().getId());

        List<PlanningItem> items = jdbcTemplate.query(QUERY, params, new RowMapper<PlanningItem>() {

            @Override
            public PlanningItem mapRow(ResultSet rs, int i) throws SQLException {
                PlanningItem item = new PlanningItem();
                item.setLibelleFormation(rs.getString(1));
                item.setDateDebutSeance(rs.getDate(2).toLocalDate());
                item.setDateFinSeance(rs.getDate(3).toLocalDate());

                return item;
            }
        });

        planning.setSeances(items);

        return planning;
    }
}
