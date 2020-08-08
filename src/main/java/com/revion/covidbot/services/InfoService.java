package com.revion.covidbot.services;

import com.revion.covidbot.cache.UserDataCache;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.utils.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class InfoService {

    private final UserDataCache userDataCache;

    public InfoService(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    public SendMessage handleInputMessage(Message message) {
        userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
        return BotUtils.getMessage(message.getChatId(), getReplyText().toString());
    }

    private StringBuilder getReplyText() {
        return new StringBuilder()
                .append("*Порядок работы с ботом:*\n")
                .append("1. Для того, чтобы начать получать рассылку добавьте регионы в подписки.\n")
                .append("2. Бот автоматически будет рассылать актуальную информацию о пандемии в 12:00 по мск.\n\n")
                .append("*Функционал бота:*\n")
                .append("_Добавить регион_ - добавить регион в подписки\n")
                .append("_Удалить регион_ - удалить регион из подписок\n")
                .append("_Показать рассылку_ - показать сгенерированную статистику для рассылки\n")
                .append("_Отписаться от рассылки_ - удалить пользователя со всеми подписками\n")
                .append("_Помощь_ - информация по работе с ботом\n")
                .append("_/regions_ - список субъектов РФ, по которым проводится выборка\n\n")
                .append("стопкоронавирус.рф - официальный сайт\n")
                .append("Вопросы, замечания и пожелания пишите -> @revion");
    }
}