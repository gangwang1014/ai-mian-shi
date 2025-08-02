package com.xxx.aimianshi.common.client;

import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 该类用于操作 redis 缓存 对于 redis 的 list, set 等集合类型, 统一视为只存入同一种类型, 在 get 的时候提供 clazz 参数
 *
 * @author 红叶已尽秋
 * @version 0.1.0
 * @date 2025/4/7 17:11
 * @since 0.1.0
 */
public interface RedisCache {

    /**
     * 存入值
     *
     * @param key   键
     * @param value 值
     * @author 红叶已尽秋
     * @date 2025/4/7 10:57
     * @since 0.1.0
     */
    void set(String key, Object value);

    /**
     * 存入值并设置过期时间
     *
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @author 红叶已尽秋
     * @date 2025/4/7 10:58
     * @since 0.1.0
     */
    void set(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 批量存入值
     *
     * @param map 键值对
     * @author 红叶已尽秋
     * @date 2025/4/9 21:08
     * @since 0.1.0
     */
    void multiSet(Map<String, ?> map);

    /**
     * 仅当key不存在添加
     *
     * @param key   键
     * @param value 值
     * @return boolean 是否添加成功
     * @author 红叶已尽秋
     * @date 2025/4/9 21:47
     * @since 0.1.0
     */
    boolean setIfAbsent(String key, Object value);

    /**
     * 仅当key不存在添加并设置过期时间
     *
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return boolean 是否添加成功
     * @author 红叶已尽秋
     * @date 2025/4/9 21:49
     * @since 0.1.0
     */
    boolean setIfAbsent(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 存在值时修改
     *
     * @param key   键
     * @param value 值
     * @return boolean 是否修改成功
     * @author 红叶已尽秋
     * @date 2025/4/9 21:57
     * @since 0.1.0
     */
    boolean setIfPresent(String key, Object value);

    /**
     * 存在值时修改并设置过期时间
     *
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return boolean 是否修改成功
     * @author 红叶已尽秋
     * @date 2025/4/9 21:57
     * @since 0.1.0
     */
    boolean setIfPresent(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 获取值
     *
     * @param key   键
     * @param clazz 值类型
     * @return Optional<T> 值的Optional包装
     * @author 红叶已尽秋
     * @date 2025/4/7 11:00
     * @since 0.1.0
     */
    <T> Optional<T> get(String key, Class<T> clazz);

    /**
     * 批量获取值
     *
     * @param keys  键集合
     * @param clazz 值类型
     * @return List<T> 值集合
     * @author 红叶已尽秋
     * @date 2025/4/9 21:10
     * @since 0.1.0
     */
    <T> List<T> multiGet(Set<String> keys, Class<T> clazz);

    /**
     * 删除值
     *
     * @param key 键
     * @author 红叶已尽秋
     * @date 2025/4/7 11:07
     * @since 0.1.0
     */
    void delete(String key);

    /**
     * 批量删除值
     *
     * @param keys 键数组
     * @author 红叶已尽秋
     * @date 2025/4/9 20:22
     * @since 0.1.0
     */
    void delete(String... keys);

    /**
     * 判断是否存在key
     *
     * @param key 键
     * @return boolean 是否存在
     * @author 红叶已尽秋
     * @date 2025/4/9 20:21
     * @since 0.1.0
     */
    boolean exists(String key);

    /**
     * 设置过期时间
     *
     * @param key      键
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return boolean 是否设置成功
     * @author 红叶已尽秋
     * @date 2025/4/9 20:23
     * @since 0.1.0
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);

    /**
     * 设置过期时间点
     *
     * @param key  键
     * @param date 过期时间点
     * @return boolean 是否设置成功
     * @author 红叶已尽秋
     * @date 2025/4/9 20:40
     * @since 0.1.0
     */
    boolean expireAt(String key, Date date);

    /**
     * 获取过期时间
     *
     * @param key 键
     * @return long 过期时间（秒）
     * @author 红叶已尽秋
     * @date 2025/4/9 20:26
     * @since 0.1.0
     */
    long getExpire(String key);

    /**
     * 获取过期时间
     *
     * @param key      键
     * @param timeUnit 时间单位
     * @return long 过期时间
     * @author 红叶已尽秋
     * @date 2025/4/9 20:31
     * @since 0.1.0
     */
    long getExpire(String key, TimeUnit timeUnit);

    /**
     * 查找匹配的 key
     *
     * @param pattern 匹配规则
     * @return Set<String> 匹配的key集合
     * @author 红叶已尽秋
     * @date 2025/4/9 20:42
     * @since 0.1.0
     */
    Set<String> keys(String pattern);

    /**
     * 移除 key 过期时间
     *
     * @param key 键
     * @return boolean 是否移除成功
     * @author 红叶已尽秋
     * @date 2025/4/9 20:43
     * @since 0.1.0
     */
    boolean persist(String key);

    /**
     * 增长值（默认增长1）
     *
     * @param key 键
     * @return Long 增长后的值
     * @author 红叶已尽秋
     * @date 2025/4/9 21:36
     * @since 0.1.0
     */
    Long increment(String key);

    /**
     * 增长值
     *
     * @param key       键
     * @param increment 增长量
     * @return Long 增长后的值
     * @author 红叶已尽秋
     * @date 2025/4/9 21:34
     * @since 0.1.0
     */
    Long increment(String key, long increment);

    /**
     * 减少值（默认减少1）
     *
     * @param key 键
     * @return Long 减少后的值
     * @author 红叶已尽秋
     * @date 2025/4/9 21:41
     * @since 0.1.0
     */
    Long decrement(String key);

    /**
     * 减少值
     *
     * @param key       键
     * @param decrement 减少量
     * @return Long 减少后的值
     * @author 红叶已尽秋
     * @date 2025/4/9 21:41
     * @since 0.1.0
     */
    Long decrement(String key, long decrement);

    /**
     * 修改 key 的名称
     *
     * @param oldKey 旧的key
     * @param newKey 新的key
     * @author 红叶已尽秋
     * @date 2025/4/9 20:45
     * @since 0.1.0
     */
    void rename(String oldKey, String newKey);

    /**
     * 仅当 newKey 不存在时修改 oldKey 的名称为 newKey
     *
     * @param oldKey 旧的key
     * @param newKey 新的key
     * @author 红叶已尽秋
     * @date 2025/4/9 20:47
     * @since 0.1.0
     */
    void renameIfAbsent(String oldKey, String newKey);


    /* -----------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------------- hash --------------------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------*/

    /**
     * 添加或者修改hash类型 key 的 field 的值
     *
     * @param key     键
     * @param hashKey hashKey
     * @param value   值
     * @author 红叶已尽秋
     * @date 2025/4/9 22:05
     * @since 0.1.0
     */
    void hashPut(String key, String hashKey, Object value);

    /**
     * 批量设置hash类型key中的多个字段值
     *
     * @param key 键
     * @param map 字段-值映射集合（key为字段名，value为对应值，支持对象类型自动序列化）
     * @author 红叶已尽秋
     * @date 2025/4/9 22:16
     * @since 0.1.0
     */
    void hashPutAll(String key, Map<String, ?> map);

    /**
     * 获取hash类型key中指定字段的值
     *
     * @param key     键
     * @param hashKey 要获取的字段名
     * @param clazz   返回值目标类型（用于反序列化）
     * @return Optional<T> 字段值的Optional包装
     * @author 红叶已尽秋
     * @date 2025/4/9 22:16
     * @since 0.1.0
     */
    <T> Optional<T> hashGet(String key, String hashKey, Class<T> clazz);

    /**
     * 获取所有的给定字段的值
     *
     * @param key 键
     * @return Map<Object, ?> 字段-值映射集合
     * @author 红叶已尽秋
     * @date 2025/4/10 13:33
     * @since 0.1.0
     */
    Map<Object, ?> hashGetAll(String key);

    /**
     * 批量获取hash类型key中指定字段的值
     *
     * @param key      键
     * @param hashKeys 要获取的字段名集合
     * @return List<?> 字段值列表
     * @author 红叶已尽秋
     * @date 2025/4/10 13:34
     * @since 0.1.0
     */
    List<?> hashMultiGet(String key, List<String> hashKeys);

    /**
     * 仅当 hashkey 不存在才 put
     *
     * @param key     键
     * @param hashKey 字段
     * @param value   值
     * @return Boolean 是否添加成功
     * @author 红叶已尽秋
     * @date 2025/6/6 22:02
     * @since 0.1.0
     */
    Boolean hashPutIfAbsent(String key, String hashKey, Object value);

    /**
     * 删除一个或者多个hash表字段
     *
     * @param key      键
     * @param hashKeys 字段名
     * @return Long 删除个数
     * @author 红叶已尽秋
     * @date 2025/6/6 22:04
     * @since 0.1.0
     */
    Long hashDelete(String key, String... hashKeys);

    /**
     * 判断hash表 key 中 指定字段是否存在
     *
     * @param key     键
     * @param hashKey 字段名
     * @return Boolean 是否存在
     * @author 红叶已尽秋
     * @date 2025/6/6 22:06
     * @since 0.1.0
     */
    Boolean hashExists(String key, String hashKey);

    /**
     * 为 key 中的 hashKey 字段的值增加 increment
     *
     * @param key       键
     * @param hashKey   字段名
     * @param increment 增量
     * @return Long 增加后的值
     * @author 红叶已尽秋
     * @date 2025/6/6 22:08
     * @since 0.1.0
     */
    Long hashIncrement(String key, String hashKey, long increment);

    /**
     * 为 key 中的 hashKey 字段的值增加 increment
     *
     * @param key       键
     * @param hashKey   字段名
     * @param increment 增量
     * @return Double 增加后的值
     * @author 红叶已尽秋
     * @date 2025/6/6 22:08
     * @since 0.1.0
     */
    Double hashIncrement(String key, String hashKey, double increment);

    /**
     * 获取所有的 hash 表字段
     *
     * @param key   键
     * @param clazz 字段类型
     * @return Set<T> 字段集合
     * @author 红叶已尽秋
     * @date 2025/6/6 22:13
     * @since 0.1.0
     */
    <T> Set<T> hashKeys(String key, Class<T> clazz);

    /**
     * 获取 hash 表中字段数量
     *
     * @param key 键
     * @return Long 字段数量
     * @author 红叶已尽秋
     * @date 2025/6/6 22:14
     * @since 0.1.0
     */
    Long hashSize(String key);

    /**
     * 获取 hash 表所有值
     *
     * @param key   键
     * @param clazz 值类型
     * @return List<T> 值集合
     * @author 红叶已尽秋
     * @date 2025/6/6 22:15
     * @since 0.1.0
     */
    <T> List<T> hashValues(String key, Class<T> clazz);

    /**
     * 迭代哈希表中的键值对
     *
     * @param key     键
     * @param options 迭代选项
     * @return Cursor<Map.Entry < Object, Object>> 迭代器
     * @author 红叶已尽秋
     * @date 2025/6/6 22:21
     * @since 0.1.0
     */
    Cursor<Map.Entry<Object, Object>> hashScan(String key, ScanOptions options);


    /* -----------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------------- list --------------------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------*/

    /**
     * 通过索引获取列表中的元素
     *
     * @param key   键
     * @param index 索引
     * @param clazz 元素类型
     * @return Optional<T> 元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/6 22:25
     * @since 0.1.0
     */
    <T> Optional<T> listIndex(String key, long index, Class<T> clazz);

    /**
     * 获取列表指定范围内的元素
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @param clazz 元素类型
     * @return List<T> 元素集合
     * @author 红叶已尽秋
     * @date 2025/6/6 22:25
     * @since 0.1.0
     */
    <T> List<T> listRange(String key, long start, long end, Class<T> clazz);

    /**
     * 左插入到列表
     *
     * @param key   列表key
     * @param value 值
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/4/9 20:56
     * @since 0.1.0
     */
    Long listLeftPush(String key, Object value);

    /**
     * 左插入多个值到列表
     *
     * @param key    列表key
     * @param values 值数组
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/4/9 20:57
     * @since 0.1.0
     */
    Long listLeftPushAll(String key, Object... values);

    /**
     * 左插入集合到列表
     *
     * @param key    键
     * @param values 值集合
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 9:56
     * @since 0.1.0
     */
    Long listLeftPushAll(String key, Collection<Object> values);

    /**
     * 仅当列表存在时左插入值
     *
     * @param key   键
     * @param value 值
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 9:59
     * @since 0.1.0
     */
    Long listLeftPushIfPresent(String key, Object value);

    /**
     * 如果 pivot 存在, 在 pivot 前面添加值
     *
     * @param key   键
     * @param pivot 参考值
     * @param value 要插入的值
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 10:01
     * @since 0.1.0
     */
    Long listLeftPush(String key, Object pivot, Object value);

    /**
     * 列表右插入值
     *
     * @param key   键
     * @param value 值
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 10:05
     * @since 0.1.0
     */
    Long listRightPush(String key, Object value);

    /**
     * 列表右插入多个值
     *
     * @param key    键
     * @param values 值数组
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 10:08
     * @since 0.1.0
     */
    Long listRightPushAll(String key, Object... values);

    /**
     * 列表右插入集合
     *
     * @param key    键
     * @param values 值集合
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 10:10
     * @since 0.1.0
     */
    Long listRightPushAll(String key, Collection<Object> values);

    /**
     * 仅当列表存在时右插入值
     *
     * @param key   键
     * @param value 值
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 11:36
     * @since 0.1.0
     */
    Long listRightPushIfPresent(String key, Object value);

    /**
     * 如果 pivot 存在, 在 pivot 后面添加值
     *
     * @param key   键
     * @param pivot 参考值
     * @param value 要插入的值
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 13:22
     * @since 0.1.0
     */
    Long listRightPush(String key, Object pivot, Object value);

    /**
     * 通过索引设置列表元素的值
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @author 红叶已尽秋
     * @date 2025/6/7 13:26
     * @since 0.1.0
     */
    void listSet(String key, long index, Object value);

    /**
     * 删除并返回存储在 key 列表中的第一个元素
     *
     * @param key   键
     * @param clazz 元素类型
     * @return Optional<T> 第一个元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/7 13:32
     * @since 0.1.0
     */
    <T> Optional<T> listLeftPop(String key, Class<T> clazz);

    /**
     * 移出并获取列表的第一个元素，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key      键
     * @param timeout  等待时间
     * @param timeUnit 时间单位
     * @param clazz    元素类型
     * @return Optional<T> 第一个元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/7 15:53
     * @since 0.1.0
     */
    <T> Optional<T> listLeftPop(String key, long timeout, TimeUnit timeUnit, Class<T> clazz);

    /**
     * 移除并获取列表最后一个元素
     *
     * @param key   键
     * @param clazz 元素类型
     * @return Optional<T> 最后一个元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/7 15:55
     * @since 0.1.0
     */
    <T> Optional<T> listRightPop(String key, Class<T> clazz);

    /**
     * 移出并获取列表的最后一个元素，如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param key      键
     * @param timeout  等待时间
     * @param timeUnit 时间单位
     * @param clazz    元素类型
     * @return Optional<T> 最后一个元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/7 15:54
     * @since 0.1.0
     */
    <T> Optional<T> listRightPop(String key, long timeout, TimeUnit timeUnit, Class<T> clazz);

    /**
     * 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     *
     * @param sourceKey      源列表
     * @param destinationKey 目标列表
     * @param clazz          元素类型
     * @return Optional<T> 移动的元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/7 16:03
     * @since 0.1.0
     */
    <T> Optional<T> listRightPopAndLeftPush(String sourceKey, String destinationKey, Class<T> clazz);

    /**
     * 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它；如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止
     *
     * @param sourceKey      源列表
     * @param destinationKey 目标列表
     * @param timeout        等待时间
     * @param timeUnit       时间单位
     * @param clazz          元素类型
     * @return Optional<T> 移动的元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/7 16:04
     * @since 0.1.0
     */
    <T> Optional<T> listRightPopAndLeftPush(String sourceKey, String destinationKey, long timeout, TimeUnit timeUnit, Class<T> clazz);

    /**
     * 删除集合中等于 value 的元素
     *
     * @param key   键
     * @param count 索引 count = 0, 删除所有等于 value 的元素 count > 0, 从头部开始删除等于 value 的元素 count < 0, 从尾部开始删除等于 value 的元素
     * @param value 值
     * @return Long 删除的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 16:08
     * @since 0.1.0
     */
    Long listRemove(String key, long count, Object value);

    /**
     * 删除 list 里面所有等于 value 的元素
     *
     * @param key   键
     * @param value 值
     * @return Long 删除的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 16:12
     * @since 0.1.0
     */
    Long listRemoveAll(String key, Object value);

    /**
     * 从列表头部开始删除指定数量的指定值
     *
     * @param key   键
     * @param count 个数 count >= 0 , 若 count < 0, 则从尾部开始删除
     * @param value 值
     * @return Long 删除的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 16:25
     * @since 0.1.0
     */
    Long listLeftRemove(String key, long count, Object value);

    /**
     * 从列表尾部开始删除指定数量的指定值
     *
     * @param key   键
     * @param count 个数 count >=0, 若 count < 0, 则从头部开始删除
     * @param value 值
     * @return Long 删除的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 16:25
     * @since 0.1.0
     */
    Long listRightRemove(String key, long count, Object value);

    /**
     * 对一个列表进行修剪(trim)，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @author 红叶已尽秋
     * @date 2025/6/7 16:31
     * @since 0.1.0
     */
    void listTrim(String key, long start, long end);

    /**
     * 获取列表长度
     *
     * @param key 键
     * @return Long 列表长度
     * @author 红叶已尽秋
     * @date 2025/6/7 16:32
     * @since 0.1.0
     */
    Long listLength(String key);

    /* -----------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------------- set --------------------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------*/

    /**
     * set 添加元素
     *
     * @param key    键
     * @param values 值数组
     * @return Long 添加成功的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 16:38
     * @since 0.1.0
     */
    Long setAdd(String key, Object... values);

    /**
     * set 移除元素
     *
     * @param key    键
     * @param values 值数组
     * @return Long 移除成功的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 16:42
     * @since 0.1.0
     */
    Long setRemove(String key, Object... values);

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param key   键
     * @param clazz 元素类型
     * @return Optional<T> 随机元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/7 16:49
     * @since 0.1.0
     */
    <T> Optional<T> setPop(String key, Class<T> clazz);

    /**
     * 将元素value从一个集合移到另一个集合
     *
     * @param key     原 set key
     * @param value   要移动的值
     * @param destKey 目标 set key
     * @return Boolean 是否移动成功
     * @author 红叶已尽秋
     * @date 2025/6/7 16:55
     * @since 0.1.0
     */
    <T> Boolean setMove(String key, T value, String destKey);

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return Long 集合大小
     * @author 红叶已尽秋
     * @date 2025/6/7 16:57
     * @since 0.1.0
     */
    Long setSize(String key);

    /**
     * 判断集合是否包含值 value
     *
     * @param key   键
     * @param value 值
     * @return Boolean 是否包含
     * @author 红叶已尽秋
     * @date 2025/6/7 16:58
     * @since 0.1.0
     */
    Boolean setIsMember(String key, Object value);

    /**
     * 获取两个集合的交集
     *
     * @param key      第一个集合 key
     * @param otherKey 第二个集合 key
     * @param clazz    元素类型
     * @return Set<T> 交集元素集合
     * @author 红叶已尽秋
     * @date 2025/6/7 17:09
     * @since 0.1.0
     */
    <T> Set<T> setIntersect(String key, String otherKey, Class<T> clazz);

    /**
     * 获取多个集合的交集
     *
     * @param key       该集合的 key
     * @param otherKeys 其他集合的 key
     * @param clazz     元素类型
     * @return Set<T> 交集元素集合
     * @author 红叶已尽秋
     * @date 2025/6/7 17:16
     * @since 0.1.0
     */
    <T> Set<T> setIntersect(String key, Collection<String> otherKeys, Class<T> clazz);

    /**
     * key 集合与 otherKey 集合的交集存入 destKey 集合中
     *
     * @param key      键
     * @param otherKey 键
     * @param destKey  键
     * @return Long 交集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 17:19
     * @since 0.1.0
     */
    Long setIntersectAndStore(String key, String otherKey, String destKey);

    /**
     * key 集合与其他 otherKeys 多个集合的交集存入 destKey 集合中
     *
     * @param key       键
     * @param otherKeys 键集合
     * @param destKey   键
     * @return Long 交集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 17:19
     * @since 0.1.0
     */
    Long setIntersectAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * 获取 key 集合 和 otherKey 集合的并集
     *
     * @param key      键
     * @param otherKey 键
     * @param clazz    元素类型
     * @return Set<T> 并集元素集合
     * @author 红叶已尽秋
     * @date 2025/6/7 17:22
     * @since 0.1.0
     */
    <T> Set<T> setUnion(String key, String otherKey, Class<T> clazz);

    /**
     * 获取 key 集合 和 多个集合 otherKeys 的并集
     *
     * @param key       键
     * @param otherKeys 键集合
     * @param clazz     元素类型
     * @return Set<T> 并集元素集合
     * @author 红叶已尽秋
     * @date 2025/6/7 17:23
     * @since 0.1.0
     */
    <T> Set<T> setUnion(String key, Collection<String> otherKeys, Class<T> clazz);

    /**
     * key 集合 和 otherKey 集合的并集存入 destKey 集合中
     *
     * @param key      键
     * @param otherKey 键
     * @param destKey  键
     * @return Long 并集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 17:24
     * @since 0.1.0
     */
    Long setUnionAndStore(String key, String otherKey, String destKey);

    /**
     * key 集合 和 多个集合 otherKeys 的并集存入 destKey 集合中
     *
     * @param key       键
     * @param otherKeys 键集合
     * @param destKey   键
     * @return Long 并集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/7 17:24
     * @since 0.1.0
     */
    Long setUnionAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * 获取 key 集合 和 otherKey 集合的差集
     *
     * @param key      键
     * @param otherKey 键
     * @param clazz    元素类型
     * @return Set<T> 差集元素集合
     * @author 红叶已尽秋
     * @date 2025/6/8 15:20
     * @since 0.1.0
     */
    <T> Set<T> setDifference(String key, String otherKey, Class<T> clazz);

    /**
     * 获取 key 集合 和 集合 otherKeys 的差集
     *
     * @param key       键
     * @param otherKeys 键集合
     * @param clazz     元素类型
     * @return Set<T> 差集元素集合
     * @author 红叶已尽秋
     * @date 2025/6/8 15:20
     * @since 0.1.0
     */
    <T> Set<T> setDifference(String key, Collection<String> otherKeys, Class<T> clazz);

    /**
     * key 集合 和 otherKey 集合的差集存入 destKey 集合中
     *
     * @param key      键
     * @param otherKey 键
     * @param destKey  键
     * @return Long 差集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/8 15:21
     * @since 0.1.0
     */
    Long setDifferenceAndStore(String key, String otherKey, String destKey);

    /**
     * key 集合 和 集合 otherKeys 的差集存入 destKey 集合中
     *
     * @param key       键
     * @param otherKeys 键集合
     * @param destKey   键
     * @return Long 差集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/8 15:21
     * @since 0.1.0
     */
    Long setDifferenceAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * 获取 key 集合的所有元素
     *
     * @param key   键
     * @param clazz 元素类型
     * @return Set<T> 元素集合
     * @author 红叶已尽秋
     * @date 2025/6/8 15:22
     * @since 0.1.0
     */
    <T> Set<T> setMembers(String key, Class<T> clazz);

    /**
     * 获取 key 集合的随机元素
     *
     * @param key   键
     * @param clazz 元素类型
     * @return Optional<T> 随机元素的Optional包装
     * @author 红叶已尽秋
     * @date 2025/6/8 15:22
     * @since 0.1.0
     */
    <T> Optional<T> setRandomMember(String key, Class<T> clazz);

    /**
     * 获取 key 集合的 count 个随机元素
     *
     * @param key   键
     * @param count 数量
     * @param clazz 元素类型
     * @return Set<T> 随机元素集合
     * @author 红叶已尽秋
     * @date 2025/6/8 15:23
     * @since 0.1.0
     */
    <T> Set<T> setRandomMembers(String key, long count, Class<T> clazz);

    /**
     * 获取 key 集合的 count 个去重后的随机元素
     *
     * @param key   键
     * @param count 数量
     * @param clazz 元素类型
     * @return Set<T> 去重后的随机元素集合
     * @author 红叶已尽秋
     * @date 2025/6/8 15:23
     * @since 0.1.0
     */
    <T> Set<T> setDistinctRandomMembers(String key, long count, Class<T> clazz);

    /**
     * 迭代 key 集合中的元素
     *
     * @param key     键
     * @param options 扫描参数
     * @return Cursor<Object> 迭代器
     * @author 红叶已尽秋
     * @date 2025/6/8 15:23
     * @since 0.1.0
     */
    Cursor<Object> setScan(String key, ScanOptions options);



    /* -----------------------------------------------------------------------------------------------------------------*/
    /* --------------------------------------------------- SortedSet(ZSet) --------------------------------------------------------*/
    /* -----------------------------------------------------------------------------------------------------------------*/

    /**
     * 添加元素, ZSet 游戏集合是按照元素的 score 排序的, 从小到大, 如果已存在则更新
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     * @return Boolean 是否添加成功
     * @author 红叶已尽秋
     * @date 2025/6/8 15:40
     * @since 0.1.0
     */
    Boolean zSetAdd(String key, Object value, double score);

    void zSetAdd(String key, Object value, double score, long timeout, TimeUnit timeUnit);

    /**
     * 移除元素
     *
     * @param key    键
     * @param values 值数组
     * @return Long 移除成功的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/8 16:35
     * @since 0.1.0
     */
    Long zSetRemove(String key, Object... values);

    /**
     * 增加元素 score 的值, 并返回增加后的值
     *
     * @param key   键
     * @param value 值
     * @param delta 增加的值
     * @return Double 增加后的分数
     * @author 红叶已尽秋
     * @date 2025/6/8 16:36
     * @since 0.1.0
     */
    Double zSetIncrementScore(String key, Object value, double delta);

    /**
     * 获取元素在集合中的排名, 有序集合是按照元素的 score 值由小到大排列的
     *
     * @param key   key
     * @param value 值
     * @return Long 排名 0 表示第一位
     * @author 红叶已尽秋
     * @date 2025/6/8 16:38
     * @since 0.1.0
     */
    Long zSetRanK(String key, Object value);

    /**
     * 获取集合中指定区间的元素, 有序集合是按照元素的 score 值由小到大排列的
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置 -1 查询所有
     * @param clazz 元素类型
     * @return Set<T> 元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:50
     * @since 0.1.0
     */
    <T> Set<T> zSetRange(String key, long start, long end, Class<T> clazz);

    /**
     * 获取集合元素, 并且把score值也获取 值由小到大排列的
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置 -1 获取所有
     * @param clazz 元素类型
     * @return Set<ZSetOperations.TypedTuple < T>> 带分数的元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:53
     * @since 0.1.0
     */
    <T> Set<ZSetOperations.TypedTuple<T>> zSetRangeWithScores(String key, long start, long end, Class<T> clazz);

    /**
     * 根据Score值查询集合元素
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param clazz 元素类型
     * @return Set<T> 元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:54
     * @since 0.1.0
     */
    <T> Set<T> zSetRangeByScore(String key, double min, double max, Class<T> clazz);

    /**
     * 根据Score值查询集合元素, 并且把score值也获取
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param clazz 元素类型
     * @return Set<ZSetOperations.TypedTuple < T>> 带分数的元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:55
     * @since 0.1.0
     */
    <T> Set<ZSetOperations.TypedTuple<T>> zSetRangeByScoreWithScores(String key, double min, double max, Class<T> clazz);

    /**
     * 根据Score值查询集合元素, 并且把score值也获取, 指定范围
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始位置
     * @param end   结束位置
     * @param clazz 元素类型
     * @return Set<ZSetOperations.TypedTuple < T>> 带分数的元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:56
     * @since 0.1.0
     */
    <T> Set<ZSetOperations.TypedTuple<T>> zSetRangeByScoreWithScores(String key, double min, double max, long start, long end, Class<T> clazz);

    /**
     * 获取集合中指定区间的元素, 有序集合是按照元素的 score 值由大到小排列的
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @param clazz 元素类型
     * @return Set<T> 元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:57
     * @since 0.1.0
     */
    <T> Set<T> zSetReverseRange(String key, long start, long end, Class<T> clazz);

    /**
     * 获取集合元素, 并且把score值也获取 值由大到小排列的
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @param clazz 元素类型
     * @return Set<ZSetOperations.TypedTuple < T>> 带分数的元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:58
     * @since 0.1.0
     */
    <T> Set<ZSetOperations.TypedTuple<T>> zSetReverseRangeWithScores(String key, long start, long end, Class<T> clazz);

    /**
     * 根据Score值查询集合元素, 值由大到小排列的
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param clazz 元素类型
     * @return Set<T> 元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:59
     * @since 0.1.0
     */
    <T> Set<T> zSetReverseRangeByScore(String key, double min, double max, Class<T> clazz);

    /**
     * 根据Score值查询集合元素, 并且把score值也获取, 值由大到小排列的
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param clazz 元素类型
     * @return Set<ZSetOperations.TypedTuple < T>> 带分数的元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 13:59
     * @since 0.1.0
     */
    <T> Set<ZSetOperations.TypedTuple<T>> zSetReverseRangeByScoreWithScores(String key, double min, double max, Class<T> clazz);

    /**
     * 根据Score值查询集合元素, 指定范围, 值由大到小排列的
     *
     * @param key   键
     * @param min   最小值
     * @param max   最大值
     * @param start 开始位置
     * @param end   结束位置
     * @param clazz 元素类型
     * @return Set<T> 元素集合
     * @author 红叶已尽秋
     * @date 2025/6/14 14:01
     * @since 0.1.0
     */
    <T> Set<T> zSetReverseRangeByScore(String key, double min, double max, long start, long end, Class<T> clazz);

    /**
     * 获取指定分数范围内的元素个数
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return Long 元素个数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:01
     * @since 0.1.0
     */
    Long zSetCount(String key, double min, double max);

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return Long 集合大小
     * @author 红叶已尽秋
     * @date 2025/6/14 14:01
     * @since 0.1.0
     */
    Long zSetSize(String key);

    /**
     * 获取集合大小
     *
     * @param key 键
     * @return Long 集合大小
     * @author 红叶已尽秋
     * @date 2025/6/14 14:01
     * @since 0.1.0
     */
    Long zSetZCard(String key);

    /**
     * 获取元素的分数
     *
     * @param key   键
     * @param value 值
     * @return Double 分数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:02
     * @since 0.1.0
     */
    Double zSetScore(String key, Object value);

    /**
     * 移除指定排名范围内的元素
     *
     * @param key   键
     * @param start 开始位置
     * @param end   结束位置
     * @return Long 移除的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:02
     * @since 0.1.0
     */
    Long zSetRemoveRange(String key, long start, long end);

    /**
     * 移除指定分数范围内的元素
     *
     * @param key 键
     * @param min 最小值
     * @param max 最大值
     * @return Long 移除的元素个数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:03
     * @since 0.1.0
     */
    Long zSetRemoveRangeByScore(String key, double min, double max);

    /**
     * 计算两个有序集合的并集, 并存储到目标集合中
     *
     * @param key      键
     * @param otherKey 其他集合的键
     * @param destKey  目标集合的键
     * @return Long 并集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:04
     * @since 0.1.0
     */
    Long zSetUnionAndStore(String key, String otherKey, String destKey);

    /**
     * 计算多个有序集合的并集, 并存储到目标集合中
     *
     * @param key       键
     * @param otherKeys 其他集合的键集合
     * @param destKey   目标集合的键
     * @return Long 并集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:20
     * @since 0.1.0
     */
    Long zSetUnionAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * 计算两个有序集合的交集, 并存储到目标集合中
     *
     * @param key      键
     * @param otherKey 其他集合的键
     * @param destKey  目标集合的键
     * @return Long 交集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:20
     * @since 0.1.0
     */
    Long zSetIntersectAndStore(String key, String otherKey, String destKey);

    /**
     * 计算多个有序集合的交集, 并存储到目标集合中
     *
     * @param key       键
     * @param otherKeys 其他集合的键集合
     * @param destKey   目标集合的键
     * @return Long 交集元素个数
     * @author 红叶已尽秋
     * @date 2025/6/14 14:20
     * @since 0.1.0
     */
    Long zSetIntersectAndStore(String key, Collection<String> otherKeys, String destKey);

    /**
     * 迭代有序集合中的元素
     *
     * @param key     键
     * @param options 扫描参数
     * @return Cursor<ZSetOperations.TypedTuple < Object>> 迭代器
     * @author 红叶已尽秋
     * @date 2025/6/14 14:20
     * @since 0.1.0
     */
    Cursor<ZSetOperations.TypedTuple<Object>> zSetScan(String key, ScanOptions options);

    /**
     * 弹出并返回有序集合中的最小元素 忽略返回值
     *
     * @param key   键
     */
    void zSetPopMin(String key);

    /**
     * 弹出并返回有序集合中的最小元素
     *
     * @param key   键
     * @param clazz 元素类型
     */
    <T> Optional<T> zSetPopMin(String key, Class<T> clazz);

    /**
     * 弹出并返回有序集合中的最小 count 个元素
     *
     * @param key   键
     * @param clazz 元素类型
     * @param count 弹出的元素个数
     */
    <T> Set<T> zSetPopMin(String key, long count, Class<T> clazz);

    /**
     * 弹出并返回有序集合中的最大元素
     *
     * @param key   键
     * @param clazz 元素类型
     */
    <T> Optional<T> zSetPopMax(String key, Class<T> clazz);


    /**
     * 弹出并返回有序集合中的最大 count 个元素
     *
     * @param key   键
     * @param count 弹出的元素个数
     * @param clazz 元素类型
     */
    <T> Set<T> zSetPopMax(String key, long count, Class<T> clazz);
}

