package midianet.latinoware;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


//@Component
public class TesteBot extends TelegramLongPollingBot {
    private Logger log = Logger.getLogger(TesteBot.class);
    private static final String user  = "MidianetBot";
    private static final String token = "193114577:AAGVvy-HeqaqYz9wvzmg-fjkvljwOvlEpOA";

    @Override
    public String getBotToken() {
        return token;
    }
    @Override
    public void onUpdateReceived(Update update) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<List<String>> kbd = new ArrayList<>();
        String[] botoes = {"A","B","C"};
        kbd.add(Arrays.asList(botoes));
        markup.setKeyboard(kbd);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboad(true);
        try{
            SendMessage send = new SendMessage();
            send.setChatId(update.getMessage().getChatId().toString());
            send.setText("Escolha a opção");
            send.setReplayMarkup(markup);
            sendMessage(send);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

//        try {
//            SendDocument send = new SendDocument();
//            send.setChatId(update.getMessage().getChatId().toString());
//            send.setNewDocument("/Users/marcosfernandocosta/Desktop/Recibo.pdf" ,"contracheque.pdf");
//            sendDocument(send);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
    }
    @Override
    public String getBotUsername() {
        return user;
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