package jp.glory.framework.interceptors.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * パフォーマンスログインターセプタ
 * @author Junki Yamada
 *
 */
@Aspect
@Component
public class PerformanceLogInterceptor {

    /**
     * メッセージフォーマット.
     */
    private static final String MESSAGE_FORMAT = "[%s][%,dms]";

    /**
     * パフォーマンスロガー.
     */
    private Logger perfomanceLog = LoggerFactory.getLogger("performanceLog");

    /**
     * ユースケースポイントカット.
     */
    @Pointcut("@within(jp.glory.framework.layer.annotation.Usecase)")
    public void pointCutUseCase() { }

    /**
     * パフォーマンスログを出力する.
     * @param joinPoint ジョインポイント
     * @return 戻り値
     * @throws Throwable
     */
    @Around("pointCutUseCase()")
    public Object logging(final ProceedingJoinPoint joinPoint) throws Throwable {

        final long beforeMills = System.currentTimeMillis();

        final Object obj = joinPoint.proceed();

        final long executedMills = System.currentTimeMillis() - beforeMills;

        writeLog(joinPoint, executedMills);

        return obj;
    }

    /**
     * ログを出力する.
     * @param joinPoint ジョインポイント
     * @param executedMills 実行ms
     */
    private void writeLog(final ProceedingJoinPoint joinPoint, final long executedMills) {

        final String targetName = getTargetName(joinPoint);

        final String message = String.format(MESSAGE_FORMAT, targetName, executedMills);

        perfomanceLog.info(message);
    }

    /**
     * ターゲットの名前を取得する.
     * @param joinPoint ジョインポイント
     * @return ターゲットの名前
     */
    private String getTargetName(final ProceedingJoinPoint joinPoint) {

        final Object targetObject = joinPoint.getTarget();
        final String targetClassName = targetObject.getClass().getName();
        final String targetMethodName = joinPoint.getSignature().getName();

        return targetClassName + "#" + targetMethodName;
    }
}
