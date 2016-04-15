package midianet.latinoware;

import midianet.latinoware.bussines.PessoaBussines;
import midianet.latinoware.model.Pessoa;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.List;
import java.util.Optional;

@Component
public class LatinowareBot extends TelegramLongPollingBot {
    private Logger log = Logger.getLogger(LatinowareBot.class);
    private static final String user  = "Latinoware Goias Bot";
    private static final String token = "206834104:AAF-N-BJH1iuzrE14JtHD-JzZdVtknYQCjs";

    private static final String CMD_START = "/start";
    private static final String CMD_LIST = "/list";

    @Autowired
    private PessoaBussines bussines;

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
        }
    }

    private void actionStart(final Update update){
        try {
            final String nome = String.format("%s %s", update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getLastName());
            final Optional<Pessoa> old = bussines.findByIdTelegram(update.getMessage().getChatId());
            final Pessoa yong = new Pessoa();
            old.ifPresent(p -> yong.setId(p.getId()));
            yong.setNome(nome);
            yong.setIdTelegram(update.getMessage().getChatId());
            bussines.save(yong);
            final String message = "Bem vindo ".concat(nome);
            send(update,message);
        }catch(Exception e){
            log.error(e);
        }
    }

    private void actionList(final Update update){
        try {
            final StringBuilder names = new StringBuilder();
            final List<Pessoa> list = bussines.listAll();
            list.forEach(p -> names.append(p.getNome().concat("\n")));
            names.append(list.size()).append( " inscritos");
            send(update,names.toString());
        }catch(Exception e){
            log.error(e);
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
