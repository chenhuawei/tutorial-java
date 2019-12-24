# tutorial-java
Tutorial


## Could not get a resource from the pool

```
Caused by: redis.clients.jedis.exceptions.JedisException: Could not get a resource from the pool
	at redis.clients.util.Pool.getResource(Pool.java:51)
	at redis.clients.jedis.ShardedJedisPool.getResource(ShardedJedisPool.java:36)
Caused by: java.util.NoSuchElementException: Unable to validate object
	at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:506)
	at org.apache.commons.pool2.impl.GenericObjectPool.borrowObject(GenericObjectPool.java:363)
	at redis.clients.util.Pool.getResource(Pool.java:49)
```

```java
    @Override
    public boolean validateObject(PooledObject<ShardedJedis> pooledShardedJedis) {
      try {
        ShardedJedis jedis = pooledShardedJedis.getObject();
        for (Jedis shard : jedis.getAllShards()) {
          if (!shard.ping().equals("PONG")) {
            return false;
          }
        }
        return true;
      } catch (Exception ex) {
        /*
        MISCONF Redis is configured to save RDB snapshots, but it is currently not able to persist on disk. Commands that may modify the data set are disabled, because this instance is configured to report errors during writes if RDB snapshotting fails (stop-writes-on-bgsave-error option). Please check the Redis logs for details about the RDB error.
         */
        return false;
      }
    }
```
