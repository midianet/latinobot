package midianet.latinoware.repository;

import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Pessoa;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
public class PessoaRepository {

    private Logger log = Logger.getLogger(PessoaRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public PessoaRepository(final JdbcTemplate template) {
        jdbcInsert =  new SimpleJdbcInsert(template)
                            .withTableName("tb_pessoa")
                            .usingColumns("pess_nome",
                                          "tele_id",
                                          "pess_pagou",
                                          "pess_cadastro")
                            .usingGeneratedKeyColumns("pess_id");
    }

    public void insert(final Pessoa pessoa) throws InfraException {
        try {
            final Map params = new HashMap();
            params.put("pess_nome",    pessoa.getNome());
            params.put("tele_id",      pessoa.getIdTelegram());
            params.put("pess_pagou", false);
            params.put("pess_cadastro", new Timestamp(new Date().getTime()));
            pessoa.setId(jdbcInsert.executeAndReturnKey(params).longValue());
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public void update(final Pessoa pessoa) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("update tb_pessoa set ")
           .append("       pess_nome = :nome ,")
           .append("        pess_pagou = :pagou ,")
           .append("       tele_id   = :telegram ")
           .append(" where pess_id   = :id");
        final Map<String,Object> param = new HashMap();
        param.put("nome",     pessoa.getNome());
        param.put("telegram", pessoa.getIdTelegram());
        param.put("pagou",    pessoa.isPagou());
        param.put("id",       pessoa.getId());
        try {
            jdbc.update(sql.toString(), param);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public void delete(final Long id) throws InfraException{
        final String sql = "delete tb_pessoa where pess_id = :id";
        final Map<String,Object> param = new HashMap();
        param.put("id",id);
        try {
            jdbc.update(sql.toString(), param);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }

    }

    public Optional<Pessoa> findByIdTelegram(final Long id) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("select pess_id,")
           .append("       pess_nome,")
           .append("       pess_cadastro,")
           .append("       pess_pagou,")
           .append("       tele_id")
           .append("  from tb_pessoa ")
           .append(" where tele_id = :id");
        final Map<String,Object> param = new HashMap();
        param.put("id",id);
        try {
            return Optional.ofNullable(jdbc.queryForObject(sql.toString(), param, this::mapRow));
        }catch(EmptyResultDataAccessException e){
            return Optional.empty();
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public Optional<Pessoa> findById(final Long id) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("select pess_id,")
                .append("       pess_nome,")
                .append("       pess_cadastro,")
                .append("       pess_pagou,")
                .append("       tele_id")
                .append("  from tb_pessoa ")
                .append(" where pess_id = :id");
        final Map<String,Object> param = new HashMap();
        param.put("id",id);
        try {
            return Optional.ofNullable(jdbc.queryForObject(sql.toString(),param, this::mapRow));
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public List<Pessoa> listAll() throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("select pess_id,")
           .append("       pess_nome,")
           .append("       pess_cadastro,")
           .append("       pess_pagou,")
           .append("       tele_id")
           .append("  from tb_pessoa ")
           .append("  order by pess_cadastro");
        try {
            return jdbc.query(sql.toString(), this::mapRow);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    private Pessoa mapRow(final ResultSet rs, final int i) throws SQLException {
        final Pessoa pessoa = new Pessoa();
        pessoa.setId        (rs.getLong     ("pess_id"));
        pessoa.setNome      (rs.getString   ("pess_nome"));
        pessoa.setCadastro  (rs.getTimestamp("pess_cadastro"));
        pessoa.setPagou     (rs.getBoolean  ("pess_pagou"));
        pessoa.setIdTelegram(rs.getLong     ("tele_id"));
        return pessoa;
    }

}