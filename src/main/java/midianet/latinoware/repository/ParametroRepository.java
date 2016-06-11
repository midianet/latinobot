package midianet.latinoware.repository;

import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Parametro;
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
import java.util.Map;
import java.util.Optional;

/**
 * Created by marcos-fc on 09/06/16.
 */
@Repository
public class ParametroRepository {

    private Logger log = Logger.getLogger(PessoaRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ParametroRepository(final JdbcTemplate template) {
        jdbcInsert =  new SimpleJdbcInsert(template)
                .withTableName("tb_parametro")
                .usingColumns("para_chave",
                              "para_descricao",
                              "para_valor")
                .usingGeneratedKeyColumns("para_id");
    }

    public Optional<Parametro> findByChave(final String chave) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("select para_chave,")
                .append("       para_descricao,")
                .append("       para_valor ")
                .append("  from tb_parametro ")
                .append(" where para_chave = :chave");
        final Map<String,Object> param = new HashMap();
        param.put("chave",chave);
        try {
            return Optional.ofNullable(jdbc.queryForObject(sql.toString(),param, this::mapRow));
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public void insert(final Parametro parametro) throws InfraException {
        try {
            final Map params = new HashMap();
            params.put("para_chave",     parametro.getChave());
            params.put("para_descricao", parametro.getDescricao());
            params.put("para_valor",     parametro.getValor());
            jdbcInsert.execute(params);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public void update(final Parametro parametro) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("update tb_parametro set ")
                .append("       para_descricao  = :descricao,")
                .append("       para_valor = :valor ")
                .append(" where para_chave    = :chave");
        final Map<String,Object> param = new HashMap();
        param.put("descricao",parametro.getDescricao());
        param.put("valor",    parametro.getValor());
        param.put("chave",       parametro.getChave());
        try {
            jdbc.update(sql.toString(), param);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }
    }

    public void delete(final String chave) throws InfraException{
        final String sql = "delete tb_parametro where para_chave = :chave";
        final Map<String,Object> param = new HashMap();
        param.put("chave",chave);
        try {
            jdbc.update(sql.toString(), param);
        }catch(Exception e){
            log.error(e);
            throw new InfraException(e);
        }

    }

    private Parametro mapRow(final ResultSet rs, final int i) throws SQLException {
        final Parametro parametro = new Parametro();
        parametro.setChave     (rs.getString   ("para_chave"));
        parametro.setDescricao (rs.getString   ("para_descricao"));
        parametro.setValor     (rs.getString   ("para_valor"));
        return parametro;
    }

}