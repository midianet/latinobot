package midianet.latinoware.bussines;


import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Parametro;
import midianet.latinoware.model.Pessoa;
import midianet.latinoware.repository.ParametroRepository;
import midianet.latinoware.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ParametroBussines {

    @Autowired
    private ParametroRepository repository;

    public Optional<Parametro> findByChave(final String chave) throws InfraException{
        return repository.findByChave(chave);
    }

    @Transactional
    public void save(final Parametro parametro) throws InfraException{
        final Optional<Parametro> p = repository.findByChave(parametro.getChave());
        if(p.isPresent()){
            repository.update(parametro);
        }else{
            repository.insert(parametro);
        }
    }

    @Transactional
    public void delete(final Parametro parametro) throws InfraException{
        repository.delete(parametro.getChave());
    }

}