package com.revion.covidbot.services;

import com.revion.covidbot.cache.UserDataCache;
import com.revion.covidbot.entities.RegionEntity;
import com.revion.covidbot.entities.UserEntity;
import com.revion.covidbot.objects.BotState;
import com.revion.covidbot.objects.RegionEnum;
import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.repositories.RegionRepo;
import com.revion.covidbot.utils.CommonUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Maxim Negodyuk created on 24.07.2020
 * @project covid19-statistic-bot
 */
@Service
@Slf4j
public class RegionService {

    private final RegionRepo regionRepo;
    private final UserDataCache userDataCache;
    private final UserService userService;
    private final ReplyMessagesService messagesService;

    private Message inputMsg;
    private User inputUser;
    private long chatId;
    @Setter
    private boolean isHandlerTypeAdd;
    @Setter
    private BotState startState;
    @Setter
    private BotState askState;

    public RegionService(RegionRepo regionRepo,
                         UserDataCache userDataCache,
                         UserService userService,
                         ReplyMessagesService messagesService) {
        this.regionRepo = regionRepo;
        this.userDataCache = userDataCache;
        this.userService = userService;
        this.messagesService = messagesService;
    }

    public SendMessage handleInputMessage(Message inputMsg) {
        this.inputMsg = inputMsg;
        inputUser = inputMsg.getFrom();
        chatId = inputMsg.getChatId();
        BotState userCurrentBotState = userDataCache.getUserCurrentBotState(inputUser.getId());

        if (userCurrentBotState == startState) {
            userDataCache.setUserCurrentBotState(inputUser.getId(), askState);
            return messagesService.getQuestionReplyMessage(chatId, "reply.region.choose");
        }

        return processUserInput();
    }

    private SendMessage processUserInput() {
        String userInputText = inputMsg.getText();
        SendMessage replyToUser;

        List<RegionEnum> regionEnumList = findRegionEnumByCodeOrTitle(userInputText);

        if (regionEnumList.size() == 1) {
            RegionEntity region = findRegionByTitle(regionEnumList.get(0).getTitle());

            Optional<UserEntity> savedUser = userService.findById(inputUser.getId());
            if (savedUser.isPresent()) {
                return generateReplyToUser(savedUser.get(), region);
            } else {
                log.info(LogMessage.USER_NOT_FOUND, inputUser.getFirstName());

                UserEntity newUser = userService.initUserSaving(inputMsg);
                return generateReplyToUser(newUser, region);
            }
        } else {
            replyToUser = messagesService.getWarningReplyMessage(chatId, "reply.region.invalidInput");
            log.info(LogMessage.REGION_INVALID_INPUT, inputUser.getFirstName(), userInputText);
        }

        return replyToUser;
    }

    private List<RegionEnum> findRegionEnumByCodeOrTitle(String inputMsg) {
        if (inputMsg == null)
            return Collections.emptyList();

        return Arrays.stream(RegionEnum.values())
                .filter(region -> region.getCode().contains(inputMsg) ||
                        region.getTitle().toLowerCase().contains(inputMsg.toLowerCase()))
                .collect(Collectors.toList());
    }

    private SendMessage generateReplyToUser(UserEntity user, RegionEntity region) {
        if (isHandlerTypeAdd) {
            boolean res = user.getRegions().add(region);
            if (res) {
                updateUserRegions(user, region.getTitle());
                return messagesService.getSuccessReplyMessage(chatId, "reply.region.added");
            } else {
                log.info(LogMessage.REGION_ALREADY_UPDATED, user.getFirstName(), region.getTitle());
                return messagesService.getInfoReplyMessage(chatId, "reply.region.alreadyAdded");
            }
        } else {
            boolean res = user.getRegions().remove(region);
            if (res) {
                updateUserRegions(user, region.getTitle());
                return messagesService.getSuccessReplyMessage(chatId, "reply.region.deleted");
            } else {
                log.info(LogMessage.REGION_ALREADY_UPDATED, user.getFirstName(), region.getTitle());
                return messagesService.getInfoReplyMessage(chatId, "reply.region.alreadyDeleted");
            }
        }
    }

    private void updateUserRegions(UserEntity user, String regionTitle) {
        userService.saveUser(user);
        userDataCache.setUserCurrentBotState(inputUser.getId(), BotState.SHOW_MAIN_MENU);
        log.info(LogMessage.REGION_SUCCESSFULLY_UPDATED, user.getFirstName(), regionTitle);
    }

    public void initSaving(List<RegionEntity> newRegionList) {
        List<RegionEntity> savedRegionList = findAllRegions();

        savedRegionList.forEach(savReg -> newRegionList.stream()
                .filter(newReg -> savReg.getCode().equals(newReg.getCode()))
                .findFirst()
                .ifPresent(newReg -> newReg.setId(savReg.getId())));

        saveAllRegions(newRegionList);
    }

    @Transactional(readOnly = true)
    public List<RegionEntity> findAllRegions() {
        return regionRepo.findAll();
    }

    @Transactional(readOnly = true)
    public RegionEntity findRegionByTitle(String title) {
        return regionRepo.findByTitle(title).orElse(null);
    }

    @Transactional
    public void saveAllRegions(List<RegionEntity> regions) {
        regionRepo.saveAll(regions);
        log.info(LogMessage.REGIONS_SUCCESSFULLY_SAVED);
    }

    public void fillReplyTextByRegionInfo(StringBuilder replyText, RegionEntity region) {
        replyText
                .append("*")
                .append(region.getTitle())
                .append("*:\n_Зараженных_: +")
                .append(CommonUtils.getDecimalFormatter().format(region.getSickIncr()))
                .append("\n_Умерших_: +")
                .append(CommonUtils.getDecimalFormatter().format(region.getDiedIncr()))
                .append("\n_Активных_: ")
                .append(CommonUtils.getDecimalFormatter().format(region.getSick() -
                        region.getHealed() - region.getDied()))
                .append("\n_Всего переболевших_: ")
                .append(CommonUtils.getDecimalFormatter().format(region.getSick()))
                .append("\n_Всего умерших_: ")
                .append(CommonUtils.getDecimalFormatter().format(region.getDied()))
                .append("\n\n");
    }
}

