package midianet.latinoware;

import midianet.latinoware.bussines.FinanceiroBussines;
import midianet.latinoware.bussines.ParametroBussines;
import midianet.latinoware.bussines.PessoaBussines;
import midianet.latinoware.model.Pagamento;
import midianet.latinoware.model.Parametro;
import midianet.latinoware.model.Pessoa;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LatinowareBot extends TelegramLongPollingBot {
    private Logger log = Logger.getLogger(LatinowareBot.class);
    private static final String user  = "Latinoware Goias Bot";
    private static final String token = "206834104:AAF-N-BJH1iuzrE14JtHD-JzZdVtknYQCjs";

    private static final String CMD_START = "/start";
    private static final String CMD_LIST = "/list";
    private static final String CMD_PAYMENT = "/payment";
    private static final String CMD_ACCOUNT = "/account";

    @Autowired
    private PessoaBussines bussinesPessoa;

    @Autowired
    private ParametroBussines bussinesParametro;

    @Autowired
    private FinanceiroBussines bussinesFinanceiro;

    @Override
    public String getBotToken() {
        return token;
    }
    @Override
    public void onUpdateReceived(Update update) {
        action(update);
    }
    @Override
    public String getBotUsername() {
        return user;
    }

    private void action(final Update update){
        if(CMD_LIST.equals(update.getMessage().getText())){
            actionList(update);
        }else if(CMD_START.equals(update.getMessage().getText())){
            actionStart(update);
        }else if(CMD_PAYMENT.equals(update.getMessage().getText())){

        }else if(CMD_ACCOUNT.equals(update.getMessage().getText())){
            actionAccount(update);
        }
    }

    private void actionStart(final Update update){
        try {
            final String nome = String.format("%s %s", update.getMessage().getFrom().getFirstName(),  update.getMessage().getFrom().getLastName() == null  ? "" :update.getMessage().getFrom().getLastName());
            final Optional<Pessoa> old = bussinesPessoa.findByIdTelegram(update.getMessage().getChatId());
            final Pessoa yong = new Pessoa();
            old.ifPresent(p -> yong.setId(p.getId()));
            yong.setNome(nome);
            yong.setIdTelegram(update.getMessage().getChatId());
            bussinesPessoa.save(yong);
            final String message = "Bem vindo ".concat(nome);
            send(update,message);
        }catch(Exception e){
            log.error(e);
        }
    }

    private void actionList(final Update update){
        try {
            final StringBuilder names = new StringBuilder();
            final List<Pessoa> list = bussinesPessoa.listAll();
            list.forEach(p -> names.append(p.getNome().concat("\n")));
            names.append(list.size()).append( " inscritos");
            send(update,names.toString());
        }catch(Exception e){
            log.error(e);
        }
    }

    private void actionAccount(final Update update){
        send(update," Banco Santander \n Agência 2032 \n Conta Poupança 60 000695-5 \n Marcos Fernando da Costa \n CPF 854.024.191-91 \n Email midianet@gmail.com");
    }

    private void actionPayment(final Update update){
        final StringBuilder retorno = new StringBuilder();
        final Parametro p = bussinesParametro.findByChave("CARAVANA").get();
        double valor = Double.parseDouble(p.getValor());
        retorno.append(p.getDescricao()).append(" ").append(p.getValor());
        final Optional<Pessoa> pes = bussinesPessoa.findByIdTelegram(update.getMessage().getChatId());
        if(pes.isPresent()){
            final List<Pagamento> pagamentos = bussinesFinanceiro.listByPessoa(pes.get());
            for(Pagamento pg : pagamentos){
                valor = valor - pg.getValor();
                retorno.append("\n").append(pg.getValor());
            }
            retorno.append("\nSaldo ").append(valor);
        }

    }

    private void send(final Update update, final String message){
        try {
            final SendMessage m = new SendMessage();
            m.setChatId(update.getMessage().getChatId().toString());
            m.setText(message);
            sendMessage(m);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

}