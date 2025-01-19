package global.kajisaab.core.redis;

import io.lettuce.core.api.StatefulRedisConnection;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class RedisCacheService {

    private final StatefulRedisConnection<String, String> redis;

    @Inject
    public RedisCacheService(StatefulRedisConnection<String, String> redis){
        this.redis = redis;
    }

    public void cacheUser(String userId, int expirationInSeconds){
        redis.sync().setex(userId, expirationInSeconds, "active");
    }

    public boolean isUserCached(String userId){
        String status= redis.sync().get(userId);
        return status != null;
    }

    public void updateCacheExpiration(String userId, int newExpirationInSeconds){
        redis.sync().expire(userId, newExpirationInSeconds);
    };

    public void removeUser(String userId){
        redis.sync().del(userId);
    }
}
