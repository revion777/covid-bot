package com.revion.covidbot.objects;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 */
@Getter
@RequiredArgsConstructor
public enum BotCommand {

    ADD_USER("/start", BotState.ADD_USER),
    SHOW_REGIONS("/regions", BotState.SHOW_REGIONS),
    SHOW_STATISTIC("Показать рассылку", BotState.SHOW_STATISTIC),
    ADD_REGION("Добавить регион", BotState.ASK_TO_CHOOSE_REGION_TO_ADD),
    DELETE_REGION("Удалить регион", BotState.ASK_TO_CHOOSE_REGION_TO_DELETE),
    UNSUBSCRIBE("Отписаться от рассылки", BotState.UNSUBSCRIBE),
    HELP("Помощь", BotState.HELP);

    private final String command;
    private final BotState state;

    private static final Map<String, BotCommand> items;

    static {
        items = Stream.of(BotCommand.values()).collect(Collectors.toMap(BotCommand::getCommand, item -> item));
    }

    public static BotState getBotStateByCommand(String command) {
        BotCommand botCommand = items.get(command);
        return botCommand != null ? botCommand.getState() : null;
    }

}
