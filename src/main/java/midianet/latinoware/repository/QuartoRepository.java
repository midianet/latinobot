package midianet.latinoware.repository;

import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Parametro;
import midianet.latinoware.model.Quarto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

    public Optional<Quarto> findById(final Long id) throws InfraException{
        final StringBuilder sql = new StringBuilder();
        sql.append("select quar_id,")
                .append("       quar_tipo,")
                .append("       quar_sexo ")
                .append("  from tb_quarto ")
                .append(" where quar_id = :id");
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



    private Quarto mapRow(final ResultSet rs, final int i) throws SQLException {
        final Quarto q = new Quarto();
        q.setId(rs.getLong("quar_id"));
        q.setTipo(rs.getInt("quar_tipo"));
        q.setSexo(rs.getInt("quar_sexo"));
        return q;
    }

}