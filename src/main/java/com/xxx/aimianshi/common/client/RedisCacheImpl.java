package com.xxx.aimianshi.common.client;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author by 红叶已尽秋
 * @classname RedisCacheImpl
 * @description RedisCacheImpl
 * @date 2025/4/7 10:52
 */

@Component
@RequiredArgsConstructor
public class RedisCacheImpl implements RedisCache {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value));
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long timeout, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, timeout, timeUnit));
    }

    @Override
    public boolean setIfPresent(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfPresent(key, value));
    }

    @Override
    public boolean setIfPresent(String key, Object value, long timeout, TimeUnit timeUnit) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfPresent(key, value, timeout, timeUnit));
    }

    @Override
    public void multiSet(Map<String, ?> map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public <T> Optional<T> get(String key, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .map(clazz::cast);
    }

    @Override
    public <T> List<T> multiGet(Set<String> keys, Class<T> clazz) {
        List<Object> objects = redisTemplate.opsForValue().multiGet(keys);
        if (objects != null) {
            return objects.stream().map(clazz::cast).toList();
        }
        return List.of();
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void delete(String... keys) {
        redisTemplate.delete(List.of(keys));
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public boolean expireAt(String key, Date date) {
        return redisTemplate.expireAt(key, date);
    }

    @Override
    public long getExpire(String key) {
	    return redisTemplate.getExpire(key);
    }

    @Override
    public long getExpire(String key, TimeUnit timeUnit) {
	    return redisTemplate.getExpire(key, timeUnit);
    }

    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public boolean persist(String key) {
        return redisTemplate.persist(key);
    }

    @Override
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long increment(String key, long increment) {
        return redisTemplate.opsForValue().increment(key, increment);
    }

    @Override
    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    @Override
    public Long decrement(String key, long decrement) {
        return redisTemplate.opsForValue().decrement(key, decrement);
    }

    @Override
    public void rename(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    @Override
    public void renameIfAbsent(String oldKey, String newKey) {
        redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    @Override
    public void hashPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public void hashPutAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public <T> Optional<T> hashGet(String key, String hashKey, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForHash().get(key, hashKey))
                .map(clazz::cast);
    }

    @Override
    public Map<Object, ?> hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public List<?> hashMultiGet(String key, List<String> hashKeys) {
        return redisTemplate.opsForHash().multiGet(key, new ArrayList<>(hashKeys));
    }

    @Override
    public Boolean hashPutIfAbsent(String key, String hashKey, Object value) {
        return redisTemplate.opsForHash().putIfAbsent(key, hashKey, value);
    }

    @Override
    public Long hashDelete(String key, String... hashKeys) {
        return redisTemplate.opsForHash().delete(key, (Object[]) hashKeys);
    }

    @Override
    public Boolean hashExists(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Long hashIncrement(String key, String hashKey, long increment) {
        return redisTemplate.opsForHash().increment(key, hashKey, increment);
    }

    @Override
    public Double hashIncrement(String key, String hashKey, double increment) {
        return redisTemplate.opsForHash().increment(key, hashKey, increment);
    }

    @Override
    public <T> Set<T> hashKeys(String key, Class<T> clazz) {
        Set<Object> keys = redisTemplate.opsForHash().keys(key);
        return keys.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    @Override
    public <T> List<T> hashValues(String key, Class<T> clazz) {
        List<Object> values = redisTemplate.opsForHash().values(key);
        return values.stream().map(clazz::cast).toList();
    }

    @Override
    public Cursor<Map.Entry<Object, Object>> hashScan(String key, ScanOptions options) {
        return redisTemplate.opsForHash().scan(key, options);
    }

    @Override
    public <T> Optional<T> listIndex(String key, long index, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForList().index(key, index))
                .map(clazz::cast);
    }

    @Override
    public <T> List<T> listRange(String key, long start, long end, Class<T> clazz) {
        List<Object> range = redisTemplate.opsForList().range(key, start, end);
        if (range == null) {
            return List.of();
        }
        return range.stream().map(clazz::cast).toList();
    }

    @Override
    public Long listLeftPush(String key, Object value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    @Override
    public Long listLeftPushAll(String key, Object... values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    @Override
    public Long listLeftPushAll(String key, Collection<Object> values) {
        return redisTemplate.opsForList().leftPushAll(key, values);
    }

    @Override
    public Long listLeftPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().leftPushIfPresent(key, value);
    }

    @Override
    public Long listLeftPush(String key, Object pivot, Object value) {
        return redisTemplate.opsForList().leftPush(key, pivot, value);
    }

    @Override
    public Long listRightPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public Long listRightPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long listRightPushAll(String key, Collection<Object> values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long listRightPushIfPresent(String key, Object value) {
        return redisTemplate.opsForList().rightPushIfPresent(key, value);
    }

    @Override
    public Long listRightPush(String key, Object pivot, Object value) {
        return redisTemplate.opsForList().rightPush(key, pivot, value);
    }

    @Override
    public void listSet(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    @Override
    public <T> Optional<T> listLeftPop(String key, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForList().leftPop(key))
                .map(clazz::cast);
    }

    @Override
    public <T> Optional<T> listLeftPop(String key, long timeout, TimeUnit timeUnit, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForList().leftPop(key, timeout, timeUnit))
                .map(clazz::cast);
    }

    @Override
    public <T> Optional<T> listRightPop(String key, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForList().rightPop(key))
                .map(clazz::cast);
    }

    @Override
    public <T> Optional<T> listRightPop(String key, long timeout, TimeUnit timeUnit, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForList().rightPop(key, timeout, timeUnit))
                .map(clazz::cast);
    }

    @Override
    public <T> Optional<T> listRightPopAndLeftPush(String sourceKey, String destinationKey, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey))
                .map(clazz::cast);
    }

    @Override
    public <T> Optional<T> listRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit timeUnit, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, destinationKey, timeout, timeUnit))
                .map(clazz::cast);
    }

    @Override
    public Long listRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
    @Override
    public Long listRemoveAll(String key, Object value) {
        return redisTemplate.opsForList().remove(key, 0, value);
    }

    @Override
    public Long listLeftRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public Long listRightRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, -1 * count, value);
    }

    @Override
    public void listTrim(String key, long start, long end) {
        redisTemplate.opsForList().trim(key, start, end);
    }

    @Override
    public Long listLength(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public Long setAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Long setRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public <T> Optional<T> setPop(String key, Class<T> clazz) {
        return Optional.ofNullable(redisTemplate.opsForSet().pop(key))
                .map(clazz::cast);
    }

    @Override
    public <T> Boolean setMove(String key, T value, String destKey) {
        return redisTemplate.opsForSet().move(key, value, destKey);
    }

    @Override
    public Long setSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public Boolean setIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key,  value);
    }

    @Override
    public <T> Set<T> setIntersect(String key, String otherKey, Class<T> clazz) {
        Set<Object> intersect = redisTemplate.opsForSet().intersect(key, otherKey);
        if (intersect == null) {
            return Collections.emptySet();
        }
        return intersect.stream()
                .map(clazz::cast)
                .collect(Collectors.toSet());
    }

    @Override
    public <T> Set<T> setIntersect(String key, Collection<String> otherKeys, Class<T> clazz) {
        Set<Object> intersect = redisTemplate.opsForSet().intersect(key, otherKeys);
        if (intersect == null) {
            return Collections.emptySet();
        }
        return intersect.stream()
                .map(clazz::cast)
                .collect(Collectors.toSet());
    }

    @Override
    public Long setIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKey, destKey);
    }

    @Override
    public Long setIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().intersectAndStore(key, otherKeys, destKey);
    }

    @Override
    public <T> Set<T> setUnion(String key, String otherKey, Class<T> clazz) {
        Set<Object> union = redisTemplate.opsForSet().union(key, otherKey);
        if (union == null) {
            return Collections.emptySet();
        }
        return union.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public <T> Set<T> setUnion(String key, Collection<String> otherKeys, Class<T> clazz) {
        Set<Object> union = redisTemplate.opsForSet().union(key, otherKeys);
        if (union == null) {
            return Collections.emptySet();
        }
        return union.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public Long setUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKey, destKey);
    }

    @Override
    public Long setUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    @Override
    public <T> Set<T> setDifference(String key, String otherKey, Class<T> clazz) {
        Set<Object> difference = redisTemplate.opsForSet().difference(key, otherKey);
        if (difference == null) {
            return Collections.emptySet();
        }
        return difference.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public <T> Set<T> setDifference(String key, Collection<String> otherKeys, Class<T> clazz) {
        Set<Object> difference = redisTemplate.opsForSet().difference(key, otherKeys);
        if (difference == null) {
            return Collections.emptySet();
        }
        return difference.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public Long setDifferenceAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKey, destKey);
    }

    @Override
    public Long setDifferenceAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForSet().differenceAndStore(key, otherKeys, destKey);
    }

    @Override
    public <T> Set<T> setMembers(String key, Class<T> clazz) {
        Set<Object> members = redisTemplate.opsForSet().members(key);
        if (members == null) {
            return Collections.emptySet();
        }
        return members.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public <T> Optional<T> setRandomMember(String key, Class<T> clazz) {
        return Optional.of(redisTemplate.opsForSet().randomMember(key))
                .map(clazz::cast);
    }

    @Override
    public <T> Set<T> setRandomMembers(String key, long count, Class<T> clazz) {
        List<Object> objects = redisTemplate.opsForSet().randomMembers(key, count);
        if (objects == null) {
            return Collections.emptySet();
        }
        return objects.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public <T> Set<T> setDistinctRandomMembers(String key, long count, Class<T> clazz) {
        Set<Object> objects = redisTemplate.opsForSet().distinctRandomMembers(key, count);
        if (objects == null) {
            return Collections.emptySet();
        }
        return objects.stream().map(clazz::cast).collect(Collectors.toSet());
    }

    @Override
    public Cursor<Object> setScan(String key, ScanOptions options) {
	    return redisTemplate.opsForSet().scan(key, options);
    }

    @Override
    public Boolean zSetAdd(String key, Object value, double score) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    @Override
    public Long zSetRemove(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    @Override
    public Double zSetIncrementScore(String key, Object value, double delta) {
        return redisTemplate.opsForZSet().incrementScore(key, value, delta);
    }

    @Override
    public Long zSetRanK(String key, Object value) {
        return redisTemplate.opsForZSet().rank(key, value);
    }

    @Override
    public <T> Set<T> zSetRange(String key, long start, long end, Class<T> clazz) {
        Set<Object> range = redisTemplate.opsForZSet().range(key, start, end);
        if (range == null) {
            return Collections.emptySet();
        }
        return range.stream()
                .map(clazz::cast)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<ZSetOperations.TypedTuple<T>> zSetRangeWithScores(String key, long start, long end, Class<T> clazz) {
        Set<ZSetOperations.TypedTuple<Object>> rangeWithScores = redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        if (rangeWithScores == null) {
            return Collections.emptySet();
        }
        return rangeWithScores.stream()
                .map(tuple -> {
                    T value = clazz.cast(tuple.getValue());
                    Double score = tuple.getScore();
                    return new DefaultTypedTuple<>(value, score);
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<T> zSetRangeByScore(String key, double min, double max, Class<T> clazz) {
        Set<Object> rangeByScore = redisTemplate.opsForZSet().rangeByScore(key, min, max);
        if (rangeByScore == null) {
            return Collections.emptySet();
        }
        return rangeByScore.stream()
                .map(clazz::cast)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<ZSetOperations.TypedTuple<T>> zSetRangeByScoreWithScores(String key, double min, double max, Class<T> clazz) {
        Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores = redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        if (rangeByScoreWithScores == null) {
            return Collections.emptySet();
        }
        return rangeByScoreWithScores.stream()
                .map(tuple -> {
                    T value = clazz.cast(tuple.getValue());
                    Double score = tuple.getScore();
                    return new DefaultTypedTuple<>(value, score);
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<ZSetOperations.TypedTuple<T>> zSetRangeByScoreWithScores(String key, double min, double max, long start, long end, Class<T> clazz) {
        Set<ZSetOperations.TypedTuple<Object>> rangeByScoreWithScores = redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
        if (rangeByScoreWithScores == null) {
            return Collections.emptySet();
        }
        return rangeByScoreWithScores.stream()
                .map(tuple -> {
                    T value = clazz.cast(tuple.getValue());
                    Double score = tuple.getScore();
                    return new DefaultTypedTuple<>(value, score);
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<T> zSetReverseRange(String key, long start, long end, Class<T> clazz) {
        Set<Object> reverseRange = redisTemplate.opsForZSet().reverseRange(key, start, end);
        if (reverseRange == null) {
            return Collections.emptySet();
        }
        return reverseRange.stream()
                .map(clazz::cast)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<ZSetOperations.TypedTuple<T>> zSetReverseRangeWithScores(String key, long start, long end, Class<T> clazz) {
        Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        if (reverseRangeWithScores == null) {
            return Collections.emptySet();
        }
        return reverseRangeWithScores.stream()
                .map(tuple -> {
                    T value = clazz.cast(tuple.getValue());
                    Double score = tuple.getScore();
                    return new DefaultTypedTuple<>(value, score);
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<T> zSetReverseRangeByScore(String key, double min, double max, Class<T> clazz) {
        Set<Object> reverseRangeByScore = redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        if (reverseRangeByScore == null) {
            return Collections.emptySet();
        }
        return reverseRangeByScore.stream()
                .map(clazz::cast)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<ZSetOperations.TypedTuple<T>> zSetReverseRangeByScoreWithScores(String key, double min, double max, Class<T> clazz) {
        Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores = redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
        if (reverseRangeByScoreWithScores == null) {
            return Collections.emptySet();
        }
        return reverseRangeByScoreWithScores.stream()
                .map(tuple -> {
                    T value = clazz.cast(tuple.getValue());
                    Double score = tuple.getScore();
                    return new DefaultTypedTuple<>(value, score);
                })
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public <T> Set<T> zSetReverseRangeByScore(String key, double min, double max, long start, long end, Class<T> clazz) {
        Set<Object> reverseRangeByScore = redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
        if (reverseRangeByScore == null) {
            return Collections.emptySet();
        }
        return reverseRangeByScore.stream()
                .map(clazz::cast)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Long zSetCount(String key, double min, double max) {
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    @Override
    public Long zSetSize(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    @Override
    public Long zSetZCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public Double zSetScore(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    @Override
    public Long zSetRemoveRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    @Override
    public Long zSetRemoveRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
    }

    @Override
    public Long zSetUnionAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    @Override
    public Long zSetUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    @Override
    public Long zIntersectAndStore(String key, String otherKey, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    @Override
    public Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }

    @Override
    public Cursor<ZSetOperations.TypedTuple<Object>> zScan(String key, ScanOptions options) {
        return redisTemplate.opsForZSet().scan(key, options);
    }

}