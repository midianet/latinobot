package midianet.latinoware.repository;

import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Pagamento;
import midianet.latinoware.model.Pessoa;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by marcos-fc on 09/06/16.
 */
@Repository
public class PagamentoRepository {

    private Logger log = Logger.getLogger(PessoaRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public PagamentoRepository(final JdbcTemplate template) {
        jdbcInsert =  new SimpleJdbcInsert(template)
                .withTableName("tb_pagamento")
                .usingColumns("paga_data",
                              "paga_valor",
                              "pess_id")
                .usingGeneratedKeyColumns("paga_id");
    }

    public void insert(final Pagamento pagamento) throws InfraException {
        try {
            final Map params = new HashMap();
            params.put("paga_data",    pagamento.getData());
            params.put("paga_valor",   pagamento.getValor());
            params.put("pess_id",      pagamento.getPessoa().getId());
            pagamento.setId(jdbcInsert.executeAndReturnKey(params).longValue());
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public void update(final Pagamento pagamento) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("update tb_pagamento set ")
                .append("       paga_data  = :data,")
                .append("       paga_valor = :valor ")
                .append("       pess_id    = :idPessoa ")
                .append(" where paga_id    = :id");
        final Map<String,Object> param = new HashMap();
        param.put("data",     pagamento.getData());
        param.put("valor",    pagamento.getValor());
        param.put("idPessoa", pagamento.getPessoa().getId());
        param.put("id",       pagamento.getId());
        try {
            jdbc.update(sql.toString(), param);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public void delete(final Long id) throws InfraException{
        final String sql = "delete tb_pagamento where paga_id = :id";
        final Map<String,Object> param = new HashMap();
        param.put("id",id);
        try {
            jdbc.update(sql.toString(), param);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }

    }

    public List<Pagamento> listByPessoa(final Long idPessoa) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("select paga_id,")
                .append("       paga_data,")
                .append("       paga_valor,")
                .append("  from tb_pagamento ")
                .append(" where pess_id = :idPessoa")
                .append("  order by paga_data");
        try {
            return jdbc.query(sql.toString(), this::mapRow);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    private Pagamento mapRow(final ResultSet rs, final int i) throws SQLException {
        final Pagamento pagamento = new Pessoa();
        pessoa.setId        (rs.getLong     ("pess_id"));
        pessoa.setNome      (rs.getString   ("pess_nome"));
        pessoa.setCadastro  (rs.getTimestamp("pess_cadastro"));
        pessoa.setIdTelegram(rs.getLong     ("tele_id"));
        return pessoa;
    }


}