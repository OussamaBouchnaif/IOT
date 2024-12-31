package org.ehei.iot.Telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class WeatherBot extends TelegramLongPollingBot {
    private final String BOTID="6963577443:AAEIdHuOoXiGJ_oA0bQ6KPlsM2J9oWLk8ss";
    private final String USERNAME="Weather_EheiBot";

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOTID;
    }

    public void sendMessage(String text) {
        SendMessage message = new SendMessage();
        message.setChatId("5494117266");
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}
