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
                                          "pess_cerveja",
                                          "pess_refrigerante",
                                          "pess_suco",
                                          "pess_ice",
                                          "pess_toddynho",
                                          "pess_coco",
                                          "quar_id",
                                          "pess_cadastro")
                            .usingGeneratedKeyColumns("pess_id");
    }

    public void insert(final Pessoa pessoa) throws InfraException {
        try {
            final Map params = new HashMap();
            params.put("pess_nome",    pessoa.getNome());
            params.put("tele_id",      pessoa.getIdTelegram());
            params.put("pess_pagou", false);
            params.put("pess_cerveja", false);
            params.put("pess_refrigerente", false);
            params.put("pess_suco", false);
            params.put("pess_ice", false);
            params.put("pess_toddynho", false);
            params.put("pess_coco", false);
            params.put("quar_id",null);
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
           .append("       pess_nome         = :nome ,")
           .append("       pess_pagou        = :pagou ,")
           .append("       pess_cerveja      = :cerveja ,")
           .append("       pess_refrigerante = :refrigerante ,")
           .append("       pess_suco         = :suco ,")
           .append("       pess_ice          = :ice ,")
           .append("       pess_toddynho     = :toddynho ,")
           .append("       pess_coco         = :coco ,")
           .append("       tele_id           = :telegram,")
           .append("       quar_id           = :quartoId ")
           .append(" where pess_id           = :id");
        final Map<String,Object> param = new HashMap();
        param.put("nome",     pessoa.getNome());
        param.put("telegram", pessoa.getIdTelegram());
        param.put("pagou",    pessoa.isPagou());
        param.put("cerveja",      pessoa.isCerveja());
        param.put("refrigerante", pessoa.isRefrigerante());
        param.put("suco",         pessoa.isSuco());
        param.put("ice",          pessoa.isSuco());
        param.put("toddynho",     pessoa.isToddynho());
        param.put("quartoId",     pessoa.getIdQuarto());
        param.put("coco",         pessoa.isAguaCoco());
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
           .append("       pess_cerveja,")
           .append("       pess_refrigerante,")
           .append("       pess_suco,")
           .append("       pess_ice,")
           .append("       pess_toddynho,")
           .append("       pess_coco,")
           .append("       quar_id,")
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
                .append("       pess_cerveja,")
                .append("       pess_refrigerante,")
                .append("       pess_suco,")
                .append("       pess_ice,")
                .append("       pess_toddynho,")
                .append("       pess_coco,")
                .append("       quar_id,")
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
           .append("       quar_id,")
           .append("       pess_cerveja,")
           .append("       pess_refrigerante,")
           .append("       pess_suco,")
           .append("       pess_ice,")
           .append("       pess_toddynho,")
           .append("       pess_coco,")
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
        pessoa.setId           (rs.getLong     ("pess_id"));
        pessoa.setNome         (rs.getString   ("pess_nome"));
        pessoa.setCadastro     (rs.getTimestamp("pess_cadastro"));
        pessoa.setPagou        (rs.getBoolean  ("pess_pagou"));
        pessoa.setIdQuarto     (rs.getLong     ("quar_id") == 0 ? null : rs.getLong("quar_id") );
        pessoa.setCerveja      (rs.getBoolean  ("pess_cerveja"));
        pessoa.setRefrigerante (rs.getBoolean  ("pess_refrigerante"));
        pessoa.setSuco         (rs.getBoolean  ("pess_suco"));
        pessoa.setIce          (rs.getBoolean  ("pess_ice"));
        pessoa.setToddynho     (rs.getBoolean  ("pess_toddynho"));
        pessoa.setAguaCoco     (rs.getBoolean  ("pess_coco"));
        pessoa.setIdTelegram   (rs.getLong     ("tele_id"));
        return pessoa;
    }

}