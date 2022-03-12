package com.revion.covidbot.aop;

import com.revion.covidbot.utils.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.revion.covidbot.objects.logging.LogMessage.*;

/**
 * @author Maxim Negodyuk created on 22.08.2020
 */
@Component
@Aspect
@Slf4j
public class SchedulerServiceAspect {

    @Around("execution(* com.revion.covidbot.demon.ExtractorScheduler.startExtractorJob())")
    public Object logSchedulerService(ProceedingJoinPoint joinPoint) {
        return logTimeAndServiceName(joinPoint);
    }

    private Object logTimeAndServiceName(ProceedingJoinPoint joinPoint) {
        String joinPointName = joinPoint.getSignature().getDeclaringType().getName();

        log.info(SCHEDULER_START, joinPointName, CommonUtils.DATE_FORMAT.format(new Date()));

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            log.info(SCHEDULER_FINISH, joinPointName, CommonUtils.DATE_FORMAT.format(new Date()));
        } catch (Throwable thr) {
            log.error(SCHEDULER_ERROR, joinPointName, thr.getMessage(), CommonUtils.DATE_FORMAT.format(new Date()));
        }
        return proceed;
    }

}
