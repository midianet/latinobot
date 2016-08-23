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

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LatinowareBot extends TelegramLongPollingBot {
    private Logger log = Logger.getLogger(LatinowareBot.class);
    private static final String user  = "Latinoware Goias Bot";
    private static final String token = "206834104:AAF-N-BJH1iuzrE14JtHD-JzZdVtknYQCjs";

    private static final String CMD_START = "/start";
    private static final String CMD_LIST = "/list";
    private static final String CMD_PAYMENT = "/payment";
    private static final String CMD_ACCOUNT = "/account";
    private static final String CMD_NOTICE = "/notice";
    private static final String CMD_PROFILE = "/profile";

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
            actionPayment(update);
        }else if(CMD_ACCOUNT.equals(update.getMessage().getText())){
            actionAccount(update);
        }else if (update.getMessage().getText().indexOf(CMD_NOTICE) != -1 ){
            actionNotice(update);
        }else if(CMD_PROFILE.equals(update.getMessage().getText())){
            actionProfile(update);
        }
    }

    private void actionProfile(final Update update){
        try{
            final StringBuilder ret = new StringBuilder();
            final Optional<Pessoa> pessoa = bussinesPessoa.findByIdTelegram(update.getMessage().getChatId());
            pessoa.ifPresent(p ->{
                ret.append("Nome: ")         .append(p.getNome()).append("\n");
                ret.append("Status: ")       .append(p.isPagou()        ? "Pagou Entrada":"Sem Pagamentos").append("\n");
                ret.append("Cerveja: ")      .append(p.isCerveja()      ? "Sim":"Não").append("\n");
                ret.append("Refrigerante: ") .append(p.isRefrigerante() ? "Sim":"Não").append("\n");
                ret.append("Energetico: ")   .append(p.isEnergetico()   ? "Sim":"Não").append("\n");
                ret.append("Suco: ")         .append(p.isSuco()         ? "Sim":"Não").append("\n");
                ret.append("Ice: ")          .append(p.isIce()          ? "Sim":"Não").append("\n");
                ret.append("Toddynho: ")     .append(p.isToddynho()     ? "Sim":"Não").append("\n");
                ret.append("Água de Coco : ").append(p.isAguaCoco()     ? "Sim":"Não").append("\n");

                ret.append("Acomodação:");
                List<Pessoa>

            });

        }catch(Exception e){
            log.error(e);
        }
    }

    private void actionNotice(final Update update){
        try {
            final List<Pessoa> list = bussinesPessoa.listAll();
            list.forEach(p -> send(p.getIdTelegram().toString(),update.getMessage().getText().replace(CMD_NOTICE,"")));
        }catch(Exception e){
            log.error(e);
        }
    }

    private void actionStart(final Update update){
        try {
            final String chatId = update.getMessage().getChatId().toString();
            final String nome = String.format("%s %s", update.getMessage().getFrom().getFirstName(),  update.getMessage().getFrom().getLastName() == null  ? "" :update.getMessage().getFrom().getLastName());
            final Optional<Pessoa> old = bussinesPessoa.findByIdTelegram(update.getMessage().getChatId());
            final Pessoa yong = new Pessoa();
            old.ifPresent(p -> yong.setId(p.getId()));
            yong.setNome(nome);
            yong.setIdTelegram(update.getMessage().getChatId());
            List<Pessoa> list = null;
            if(yong.getId() == null){
                list = bussinesPessoa.listAll();
            }
            bussinesPessoa.save(yong);
            final String message = "Bem vindo ".concat(nome);
            send(chatId,message);
            if(list != null){
                list.forEach(pessoa -> send(pessoa.getIdTelegram().toString(), pessoa.getNome().concat(" um novo companheiro de caravana foi adicionado ").concat(yong.getNome())));
            }
        }catch(Exception e){
            log.error(e);
        }
    }

    private void actionList(final Update update){
        try {
            final String chatId = update.getMessage().getChatId().toString();
            final StringBuilder names = new StringBuilder();
            final List<Pessoa> listInscritos = bussinesPessoa.listAll().stream().filter(p -> p.isPagou()).collect(Collectors.toList());
            final List<Pessoa> listEspera    = bussinesPessoa.listAll().stream().filter(p -> !p.isPagou()).collect(Collectors.toList());
            names.append("Inscritos: ").append(listInscritos.size()).append("\n");
            listInscritos.forEach(p -> names.append(p.getNome().concat("\n")));
            names.append("\n");
            names.append("Em lista: ").append(listEspera.size()).append("\n");
            listEspera.forEach(p -> names.append(p.getNome().concat("\n")));
            names.append("\n");
            names.append("Total: ").append(listInscritos.size() + listEspera.size());
            send(chatId,names.toString());
        }catch(Exception e){
            log.error(e);
        }
    }

    private void actionAccount(final Update update){
        final String chatId = update.getMessage().getChatId().toString();
        send(chatId,"  Banco Santander \n Agência 2032 \n Conta Poupança 60 000695-5 \n Marcos Fernando da Costa \n CPF 854.024.191-91 \n Email midianet@gmail.com");
    }

    private void actionPayment(final Update update){
        final String chatId = update.getMessage().getChatId().toString();
        final StringBuilder retorno = new StringBuilder();
        final Parametro p = bussinesParametro.findByChave("CARAVANA").get();
        double valor = Double.parseDouble(p.getValor());
        double saldo = 0;
        retorno.append(p.getDescricao()).append(" ").append(p.getValor());
        final Optional<Pessoa> pes = bussinesPessoa.findByIdTelegram(update.getMessage().getChatId());
        if(pes.isPresent()){
            final List<Pagamento> pagamentos = bussinesFinanceiro.listByPessoa(pes.get());
            final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for(Pagamento pg : pagamentos){
                saldo = saldo + pg.getValor();
                retorno.append("\n").append(sdf.format(pg.getData())).append(" ").append(pg.getValor());
            }
            saldo = saldo - valor;
            retorno.append("\nSaldo ").append(saldo);
        }
        send(chatId,retorno.toString());
    }

    private void send(final String chatId, final String message){
        try {
            final SendMessage m = new SendMessage();
            m.setChatId(chatId);
            m.setText(message);
            sendMessage(m);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

}