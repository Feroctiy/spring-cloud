package com.lpg.user.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author lPG
 * @version 1.0
 * @Description 订阅频道
 * @date 2020-06-12 10:37
 */
public class SubThread extends Thread{
    private final JedisPool jedisPool;
    private final Subscriber subscriber = new Subscriber();

    public SubThread(JedisPool jedisPool) {
        super("SubThread");
        this.jedisPool = jedisPool;
    }

    @Override
    public void run() {
        System.out.println(String.format("subscribe redis, channel %s, thread will be blocked", RedisChannelEnum.USER.getChannel()));
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();   //取出一个连接
            jedis.subscribe(subscriber, RedisChannelEnum.USER.getChannel());    //通过subscribe 的api去订阅，入参是订阅者和频道名
        } catch (Exception e) {
            System.out.println(String.format("subsrcibe channel error, %s", e));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
