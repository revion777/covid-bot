package com.revion.covidbot.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Getter
@AllArgsConstructor
public enum RegionEnum {

    _01("01", "Республика Адыгея"),
    _02("02", "Республика Башкортостан"),
    _03("03", "Республика Бурятия"),
    _04("04", "Республика Алтай (Горный Алтай)"),
    _05("05", "Республика Дагестан"),
    _06("06", "Республика Ингушетия"),
    _07("07", "Кабардино-Балкарская Республика"),
    _08("08", "Республика Калмыкия"),
    _09("09", "Республика Карачаево-Черкессия"),
    _10("10", "Республика Карелия"),
    _11("11", "Республика Коми"),
    _12("12", "Республика Марий Эл"),
    _13("13", "Республика Мордовия"),
    _14("14", "Республика Саха (Якутия)"),
    _15("15", "Республика Северная Осетия — Алания"),
    _16("16", "Республика Татарстан"),
    _17("17", "Республика Тыва"),
    _18("18", "Удмуртская Республика"),
    _19("19", "Республика Хакасия"),
    _21("21", "Чувашская Республика"),
    _22("22", "Алтайский край"),
    _23("23", "Краснодарский край"),
    _24("24", "Красноярский край"),
    _25("25", "Приморский край"),
    _26("26", "Ставропольский край"),
    _27("27", "Хабаровский край"),
    _28("28", "Амурская область"),
    _29("29", "Архангельская область"),
    _30("30", "Астраханская область"),
    _31("31", "Белгородская область"),
    _32("32", "Брянская область"),
    _33("33", "Владимирская область"),
    _34("34", "Волгоградская область"),
    _35("35", "Вологодская область"),
    _36("36", "Воронежская область"),
    _37("37", "Ивановская область"),
    _38("38", "Иркутская область"),
    _39("39", "Калининградская область"),
    _40("40", "Калужская область"),
    _41("41", "Камчатский край"),
    _42("42", "Кемеровская область"),
    _43("43", "Кировская область"),
    _44("44", "Костромская область"),
    _45("45", "Курганская область"),
    _46("46", "Курская область"),
    _47("47", "Ленинградская область"),
    _48("48", "Липецкая область"),
    _49("49", "Магаданская область"),
    _50("50", "Московская область"),
    _51("51", "Мурманская область"),
    _52("52", "Нижегородская область"),
    _53("53", "Новгородская область"),
    _54("54", "Новосибирская область"),
    _55("55", "Омская область"),
    _56("56", "Оренбургская область"),
    _57("57", "Орловская область"),
    _58("58", "Пензенская область"),
    _59("59", "Пермский край"),
    _60("60", "Псковская область"),
    _61("61", "Ростовская область"),
    _62("62", "Рязанская область"),
    _63("63", "Самарская область"),
    _64("64", "Саратовская область"),
    _65("65", "Сахалинская область"),
    _66("66", "Свердловская область"),
    _67("67", "Смоленская область"),
    _68("68", "Тамбовская область"),
    _69("69", "Тверская область"),
    _70("70", "Томская область"),
    _71("71", "Тульская область"),
    _72("72", "Тюменская область"),
    _73("73", "Ульяновская область"),
    _74("74", "Челябинская область"),
    _75("75", "Забайкальский край"),
    _76("76", "Ярославская область"),
    _77("77", "Москва"),
    _78("78", "Санкт-Петербург"),
    _79("79", "Еврейская автономная область"),
    _82("82", "Республика Крым"),
    _83("83", "Ненецкий автономный округ"),
    _86("86", "Ханты-Мансийский автономный округ — Югра"),
    _87("87", "Чукотский автономный округ"),
    _89("89", "Ямало-Ненецкий автономный округ"),
    _92("92", "Севастополь"),
    _95("95", "Чеченская республика");

    private final String code;
    private final String title;

}