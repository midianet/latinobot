package midianet.latinoware.bussines;

import midianet.latinoware.exception.InfraException;
import midianet.latinoware.model.Pagamento;
import midianet.latinoware.model.Pessoa;
import midianet.latinoware.repository.PagamentoRepository;
import midianet.latinoware.repository.ParametroRepository;
import midianet.latinoware.repository.PessoaRepository;

import java.util.List;

/**
 * Created by marcos-fc on 09/06/16.
 */
public class FinanceiroBussines {
    private ParametroRepository parametroRepository;
    private PagamentoRepository pagamentoRepository;

    private List<Pagamento> listByPessoa(final Pessoa pessoa) throws InfraException{
        return pagamentoRepository.l
    }

}