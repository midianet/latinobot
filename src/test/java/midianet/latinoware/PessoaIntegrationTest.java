package midianet.latinoware;

import midianet.latinoware.bussines.PessoaBussines;
import midianet.latinoware.exception.NotFoundException;
import midianet.latinoware.model.Pessoa;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring-test.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
                         TransactionalTestExecutionListener.class,
                         DirtiesContextTestExecutionListener.class})
@Transactional()
public class PessoaIntegrationTest {

    @Autowired
    private PessoaBussines bo;

    private Pessoa pessoa;

    @Before
    public void init(){
        pessoa = new Pessoa();
        pessoa.setNome("Congressista");
        pessoa.setCadastro(new Date());
        pessoa.setIdTelegram(1234567890L);
    }

    @Test
    public void listAllTest(){
        insertTest();
        assertFalse(bo.listAll().isEmpty());
    }

    @Test
    public void insertTest(){
        pessoa.setId(null);
        bo.save(pessoa);
        assertNotNull(pessoa.getId());
    }

    @Test
    public void alterarTest(){
        insertTest();
        final List<Pessoa> list = bo.listAll();
        final String nome = "Congressista alterado";
        if(!list.isEmpty()) {
            pessoa = list.get(0);
            pessoa.setNome(nome);
            bo.save(pessoa);
            pessoa = bo.findById(pessoa.getId()).orElseThrow(() -> new NotFoundException());
            assertEquals(pessoa.getNome(),nome);
        }
    }

   // @Test
    public void excluirTest(){
        insertTest();
        final List<Pessoa> list = bo.listAll();
        if(!list.isEmpty()) {
            pessoa = list.get(0);
            bo.delete(pessoa);
            pessoa = bo.findById(pessoa.getId()).orElseThrow(() -> new NotFoundException());
        }
    }

    @Test
    public void findByIdTest(){
        insertTest();
        final List<Pessoa> list = bo.listAll();
        if(!list.isEmpty()){
            pessoa = list.get(0);
            pessoa = bo.findById(pessoa.getId()).orElseThrow(() -> new NotFoundException());
        }
    }

    @Test
    public void findByIdTelegram(){
        insertTest();
        final List<Pessoa> list = bo.listAll();
        if(!list.isEmpty()){
            pessoa = list.get(0);
            pessoa = bo.findByIdTelegram(pessoa.getIdTelegram()).orElseThrow(() -> new NotFoundException());
        }
    }

}