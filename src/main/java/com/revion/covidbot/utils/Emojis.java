package com.revion.covidbot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@AllArgsConstructor
public enum Emojis {

    INFO(EmojiParser.parseToUnicode(":information_source:")),
    PLUS(EmojiParser.parseToUnicode(":heavy_plus_sign:")),
    MINUS(EmojiParser.parseToUnicode(":heavy_minus_sign:")),
    ERROR(EmojiParser.parseToUnicode(":x:")),
    ASK(EmojiParser.parseToUnicode(":question:")),
    BULB(EmojiParser.parseToUnicode(":bulb:")),
    SUCCESS_MARK(EmojiParser.parseToUnicode(":white_check_mark:")),
    NOTIFICATION_MARK_FAILED(EmojiParser.parseToUnicode(":exclamation:"));

    private final String emojiName;

    @Override
    public String toString() {
        return emojiName;
    }
}
