package midianet.latinoware.bussines;


import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Pessoa;
import midianet.latinoware.model.Quarto;
import midianet.latinoware.repository.PessoaRepository;
import midianet.latinoware.repository.QuartoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PessoaBussines {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private QuartoRepository quartoRepository;

    public Optional<Pessoa> findById(final Long id) throws InfraException{
        return repository.findById(id);
    }

    public Optional<Pessoa> findByIdTelegram(final Long id) throws InfraException{
        return repository.findByIdTelegram(id);
    }

    public List<Pessoa> listAll() throws InfraException{
        return repository.listAll();
    }

    public Optional<Quarto> lotacaoQuartoby(final Long quartoId)throws InfraException{
        final Optional<Quarto> quarto = quartoRepository.findById(quartoId);
        quarto.ifPresent(q -> {
          quarto.get().setOcupantes(repository.listAll()
                  .stream().filter(p -> p.getIdQuarto() == quartoId)
                  .collect(Collectors.toList()));
        });
        return quarto;
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