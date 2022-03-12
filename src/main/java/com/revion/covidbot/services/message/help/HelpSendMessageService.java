package com.revion.covidbot.services.message.help;

import com.revion.covidbot.botapi.cache.UserDataCache;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.services.message.SendMessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author Maxim Negodyuk created on 12.03.2022
 */
@RequiredArgsConstructor
@Service
public class HelpSendMessageService {

    private final UserDataCache userDataCache;
    private final SendMessageBuilder sendMessageBuilder;

    public SendMessage init(Message message) {
        userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.SHOW_MAIN_MENU);
        return sendMessageBuilder.getPlainTextMessage(String.valueOf(message.getChatId()), getHelpText());
    }

    private String getHelpText() {
        return """
                *Порядок работы с ботом:*
                 1. Для того, чтобы начать получать рассылку добавьте регионы в подписки.
                2. Бот автоматически будет рассылать актуальную информацию о пандемии в 12:00 по мск.

                *Функционал бота:*
                _Добавить регион_ - добавить регион в подписки
                _Удалить регион_ - удалить регион из подписок
                _Показать рассылку_ - показать сгенерированную статистику для рассылки
                _Отписаться от рассылки_ - удалить пользователя со всеми подписками
                _Помощь_ - информация по работе с ботом
                _/regions_ - список субъектов РФ, по которым проводится выборка

                стопкоронавирус.рф - официальный сайт
                Вопросы, замечания и пожелания пишите -> @revion""";
    }
}