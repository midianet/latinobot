package midianet.latinoware;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;


@Component
public class App {
   // private static ApplicationContext context = null;

    private Logger log = Logger.getLogger(App.class);
    private TelegramBotsApi teleBot;



//    public static ApplicationContext getContext(){
//        return context;
//    }

    @Autowired
    private void initBot(final LatinowareBot latinowareBot){
        try {
            teleBot = new TelegramBotsApi();
            teleBot.registerBot(latinowareBot);
        } catch (TelegramApiException e) {
            log.error(e);
        }
    }

    public App(){

    }

    public static void main(final String[] args) {
       final ApplicationContext context =  new ClassPathXmlApplicationContext("classpath:/spring.xml");
    }

}