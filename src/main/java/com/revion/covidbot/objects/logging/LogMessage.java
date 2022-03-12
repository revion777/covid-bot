package com.revion.covidbot.objects.logging;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
public class LogMessage {

//    public static final String BOT_OPTIONS = "Default bot options. ProxyHost = {}, proxyPort = {}, proxyType = {}";
    public static final String BOT_CONFIG = "Bot configuration = {}";
    public static final String BOT_PING = "Ping response code {}";
    public static final String BOT_NEW_INPUT_MESSAGE = "Новое сообщение от пользователя: {}";
    public static final String BOT_SEND_MSG_SUCCESS = "Сообщение успешно отправлено пользователю. Текст: {}";
    public static final String BOT_SEND_MSG_ERROR = "Произошла ошибка при передаче сообщения боту! Пользователь: {}, {}";
    public static final String BOT_HANDLE_INPUT_MSG_ERROR = "Произошла ошибка при обработке пользовательского сообщения!";

    public static final String SCHEDULER_START = "Начало работы планировщика для сервиса {}, время = {}";
    public static final String SCHEDULER_FINISH = "Завершение работы планировщика для сервиса {}, время = {}";
    public static final String SCHEDULER_ERROR = "Произошла ошибка во время работы планировщика для сервиса {}, " +
            "ошибка: {}, время = {}";

    public static final String OVERALL_INFO_SUCCESSFULLY_SAVED = "Шапка статистики успешно сохранена/обновлена";

    public static final String REGIONS_SUCCESSFULLY_SAVED = "Регионы успешно сохранены/обновлены";
    public static final String REGIONS_NOT_FOUND = "Статистика для отправки не найдена. UserId: {}";
    public static final String REGIONS_FOUND = "Найдена статистика для отправки. UserId: {}";
    public static final String REGION_INVALID_INPUT = "Неверный ввод региона. UserId: {}, текст: {}";
    public static final String REGION_SUCCESSFULLY_UPDATED = "Регион успешно обновлен. UserId: {}, регион: {}";
    public static final String REGION_ALREADY_UPDATED = "Регион уже обновлен. UserId: {}, регион: {}";

    public static final String USER_NOT_FOUND = "Пользователь не найден. UserId: {}";
    public static final String USER_SUCCESSFULLY_SAVED = "Пользователь успешно сохранен = {}";
    public static final String USER_ALREADY_SAVED = "Пользователь уже сохранен = {}";
    public static final String USER_DELETED = "Пользователь успешно удален = {}";
    public static final String USER_ALREADY_DELETED = "Пользователь уже удален. UserId: {}";

}
