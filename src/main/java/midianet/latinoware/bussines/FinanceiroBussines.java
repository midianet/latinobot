package midianet.latinoware.bussines;

import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Pagamento;
import midianet.latinoware.model.Pessoa;
import midianet.latinoware.repository.PagamentoRepository;
import midianet.latinoware.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by marcos-fc on 09/06/16.
 */
@Service
public class FinanceiroBussines {

    @Autowired
    private ParametroRepository parametroRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    public List<Pagamento> listByPessoa(final Pessoa pessoa) throws InfraException{
        return pagamentoRepository.listByPessoa(pessoa.getId());
    }

}