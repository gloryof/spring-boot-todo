package jp.glory.framework.web;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Web設定.
 * @author Junki Yamada
 *
 */
@Configuration
@EnableRedisHttpSession
public class WebSetting {

    /**
     * Redisキャッシュ設定.
     * @param redisOperations 
     * @return キャッシュマネージャー
     */
    @Bean
    public CacheManager redisCache(@Qualifier("redisTemplate") RedisOperations<Object, Object> redisOperations) {

        return new RedisCacheManager(redisOperations);
    }
}
