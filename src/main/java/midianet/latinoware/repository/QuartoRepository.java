package midianet.latinoware.repository;

import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Parametro;
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
public class QuartoRepository {

    private Logger log = Logger.getLogger(QuartoRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Optional<Parametro> findById(final Long id) throws InfraException{
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



    private Parametro mapRow(final ResultSet rs, final int i) throws SQLException {
        final Parametro parametro = new Parametro();
        parametro.setChave     (rs.getString   ("para_chave"));
        parametro.setDescricao (rs.getString   ("para_descricao"));
        parametro.setValor     (rs.getString   ("para_valor"));
        return parametro;
    }

}