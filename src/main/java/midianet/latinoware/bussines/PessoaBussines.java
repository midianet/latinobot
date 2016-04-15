package midianet.latinoware.bussines;


import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Pessoa;
import midianet.latinoware.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaBussines {

    @Autowired
    private PessoaRepository repository;

    public Optional<Pessoa> findById(final Long id) throws InfraException{
        return repository.findById(id);
    }

    public Optional<Pessoa> findByIdTelegram(final Long id) throws InfraException{
        return repository.findByIdTelegram(id);
    }

    public List<Pessoa> listAll() throws InfraException{
        return repository.listAll();
    }

    @Transactional
    public void save(final Pessoa pessoa) throws InfraException{
        if(pessoa.getId() == null){
            pessoa.setCadastro(new Date());
            repository.insert(pessoa);
        }else{
            repository.update(pessoa);
        }
    }

    @Transactional
    public void delete(final Pessoa pessoa) throws InfraException{
        repository.delete(pessoa.getId());
    }

}