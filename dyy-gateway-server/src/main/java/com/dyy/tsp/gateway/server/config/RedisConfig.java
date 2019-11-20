package com.dyy.tsp.gateway.server.config;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis分库连接配置
 * 分库提高Redis缓存命中率
 * created by dyy
 */
@Configuration
@SuppressWarnings("all")
public class RedisConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisConfig.class);

    @Value("${spring.redis.host}")
    private String hostName;

    @Value("${spring.redis.port}")
    private Integer port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.jedis.maxActive}")
    private Integer maxTotal;

    @Value("${spring.redis.jedis.maxIdle}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.maxWait}")
    private Long maxWaitMillis;

    @Value("${spring.redis.jedis.onBorrow}")
    private Boolean testOnBorrow;

    @Bean(name = "vehicleRedisTemplate")
    public RedisTemplate deviceRedisTemplate(@Value("${spring.redis.database.vehicle}") Integer database ) {
        RedisTemplate deviceRedisTemplate = new StringRedisTemplate();
        RedisSerializer stringSerializer = new StringRedisSerializer();
        deviceRedisTemplate.setKeySerializer(stringSerializer);
        deviceRedisTemplate.setValueSerializer(stringSerializer);
        deviceRedisTemplate.setHashKeySerializer(stringSerializer);
        deviceRedisTemplate.setHashValueSerializer(stringSerializer);
        deviceRedisTemplate.setConnectionFactory(connectionFactory(hostName, port, password, maxIdle, maxTotal, database, maxWaitMillis, testOnBorrow));
        return deviceRedisTemplate;
    }

    /**
     *
     * @param hostName
     * @param port
     * @param password
     * @param maxIdle
     * @param maxTotal
     * @param database
     * @param maxWaitMillis
     * @param testOnBorrow
     * @return
     */
    public RedisConnectionFactory connectionFactory(String hostName, Integer port, String password, Integer maxIdle, Integer maxTotal, Integer database, Long maxWaitMillis, Boolean testOnBorrow) {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(hostName);
        jedis.setPort(port);
        if (StringUtils.isNotBlank(password)) {
            jedis.setPassword(password);
        }
        jedis.setDatabase(database);
        jedis.setPoolConfig(poolCofig(maxIdle, maxTotal, maxWaitMillis, testOnBorrow));
        //初始化连接池
        jedis.afterPropertiesSet();
        RedisConnectionFactory factory = jedis;
        LOGGER.debug("init redis {}:{} database:{} success", hostName, port, database);
        return factory;
    }

    /**
     * 加载Redis连接池配置
     * @param maxIdle
     * @param maxTotal
     * @param maxWaitMillis
     * @param testOnBorrow
     * @return
     */
    public JedisPoolConfig poolCofig(int maxIdle, int maxTotal, long maxWaitMillis, boolean testOnBorrow) {
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        poolCofig.setMaxIdle(maxIdle);
        poolCofig.setMaxTotal(maxTotal);
        poolCofig.setMaxWaitMillis(maxWaitMillis);
        poolCofig.setTestOnBorrow(testOnBorrow);
        return poolCofig;
    }

    public Integer getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(Integer maxTotal) {
        this.maxTotal = maxTotal;
    }

    public Integer getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(Integer maxIdle) {
        this.maxIdle = maxIdle;
    }

    public Long getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public void setMaxWaitMillis(Long maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
