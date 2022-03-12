package com.revion.covidbot.utils;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;

/**
 * @author Maxim Negodyuk created on 24.07.2020

 */
@RequiredArgsConstructor
public enum Emojis {

    INFO(EmojiParser.parseToUnicode(":information_source:")),
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
