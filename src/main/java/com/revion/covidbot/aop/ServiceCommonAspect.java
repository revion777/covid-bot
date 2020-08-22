package com.revion.covidbot.aop;

import com.revion.covidbot.objects.logging.LogMessage;
import com.revion.covidbot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Maxim Negodyuk created on 22.08.2020
 * @project covid19-statistic-bot
 */
@Aspect
@Component
@Slf4j
public class ServiceCommonAspect {

    @Around("execution(* com.revion.covidbot.schedulers.ExtractorScheduler.startExtractorJob())")
    public void logSchedulerService(ProceedingJoinPoint joinPoint) {
        logTimeAndServiceName(joinPoint);
    }

    private void logTimeAndServiceName(ProceedingJoinPoint joinPoint) {
        String joinPointName = joinPoint.getSignature().getDeclaringType().getName();

        log.info(LogMessage.SCHEDULER_START, joinPointName, CommonUtils.DATE_FORMAT.format(new Date()));

        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(LogMessage.SCHEDULER_ERROR, joinPointName, throwable);
        }

        log.info(LogMessage.SCHEDULER_FINISH, joinPointName, CommonUtils.DATE_FORMAT.format(new Date()));
    }

}
