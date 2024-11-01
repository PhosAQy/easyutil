package com.phosa;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 集合工具类，用于对集合中的元素进行操作。
 * <p>目前提供的方法包括对集合中的元素进行审核，判断是否所有元素都满足特定条件。
 */
@Slf4j
public class CollectionUtil {

    /**
     * 检查集合中的元素是否都满足给定的审核条件。
     *
     * @param assets 待审核的集合
     * @param reviewAsset 审核条件，传入一个函数，接受集合中的元素并返回布尔值，表示是否通过审核
     * @param <T> 集合元素的类型
     * @return 如果所有元素都通过审核，返回true，否则返回false
     */
    public static <T> Boolean review(Collection<T> assets, Function<T, Boolean> reviewAsset) {
        for (T asset : assets) {
            log.info("审核：{}", asset);  // 输出正在审核的元素
            if (!reviewAsset.apply(asset)) {
                log.info("未通过！");  // 如果元素未通过审核，记录日志并返回false
                return false;
            }
            log.info("通过！");  // 如果元素通过审核，记录日志
        }
        return true;  // 如果所有元素都通过审核，返回true
    }
    /**
     * 过滤集合中的元素，返回符合条件的元素列表。
     *
     * @param collection 待过滤的集合
     * @param predicate 过滤条件
     * @param <T> 集合元素的类型
     * @return 符合条件的元素列表
     */
    public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 查找集合中第一个符合条件的元素。
     *
     * @param collection 待查找的集合
     * @param predicate 查找条件
     * @param <T> 集合元素的类型
     * @return 符合条件的第一个元素或null
     */
    public static <T> T findFirst(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).findFirst().orElse(null);
    }

    /**
     * 对集合中的每个元素应用指定的操作。
     *
     * @param collection 待处理的集合
     * @param action 对每个元素的处理操作
     * @param <T> 集合元素的类型
     */
    public static <T> void forEach(Collection<T> collection, Function<T, Void> action) {
        collection.forEach(action::apply);
    }

    /**
     * 将集合中的每个元素映射为另一种类型，并返回映射后的列表。
     *
     * @param collection 待映射的集合
     * @param mapper 映射函数
     * @param <T> 原始集合元素的类型
     * @param <R> 映射后的元素类型
     * @return 映射后的元素列表
     */
    public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 检查集合中是否存在至少一个符合条件的元素。
     *
     * @param collection 待检查的集合
     * @param predicate 检查条件
     * @param <T> 集合元素的类型
     * @return 如果存在符合条件的元素，返回true，否则返回false
     */
    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().anyMatch(predicate);
    }
}
