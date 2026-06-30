package com.yub.system.job;

import com.yub.system.service.log.SysAccessLogService;
import com.yub.system.service.log.SysOperaLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 日志数据保留定时任务
 * <p>
 * 每天凌晨 3:00 执行，清理过期日志数据。
 * 访问日志保留 30 天，操作日志保留 90 天。
 * 循环分批删除，防止大事务锁表。
 * </p>
 *
 * @Author: bing.yu
 * @CreateTime: 2026-06-28
 * @Description: 日志数据保留定时任务
 * @Version: 1.0.0
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogCleanupJob {

    private final SysAccessLogService accessLogService;
    private final SysOperaLogService operaLogService;

    /** 访问日志保留天数 */
    @Value("${log.cleanup.access-log-retention-days:30}")
    private int accessLogRetentionDays;

    /** 操作日志保留天数 */
    @Value("${log.cleanup.opera-log-retention-days:90}")
    private int operaLogRetentionDays;

    /**
     * 每天凌晨 3:00 执行日志清理
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanExpiredLogs() {
        log.info("开始清理过期日志（访问日志保留 {} 天，操作日志保留 {} 天）",
                accessLogRetentionDays, operaLogRetentionDays);

        try {
            LocalDateTime accessLogBefore = LocalDateTime.now().minusDays(accessLogRetentionDays);
            int accessDeleted = accessLogService.clean(accessLogBefore);
            log.info("访问日志清理完成，共删除 {} 条", accessDeleted);
        } catch (Exception e) {
            log.error("清理访问日志异常", e);
        }

        try {
            LocalDateTime operaLogBefore = LocalDateTime.now().minusDays(operaLogRetentionDays);
            int operaDeleted = operaLogService.clean(operaLogBefore);
            log.info("操作日志清理完成，共删除 {} 条", operaDeleted);
        } catch (Exception e) {
            log.error("清理操作日志异常", e);
        }

        log.info("过期日志清理任务执行完毕");
    }
}
