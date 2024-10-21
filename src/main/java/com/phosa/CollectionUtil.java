package com.phosa;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.function.Function;

/**
 * @author phosa.gao
 */
@Slf4j
public class CollectionUtil {
    /**
     * 检查集合里的元素是否都满足条件
     */
    public static <T> Boolean review(Collection<T> assets, Function<T, Boolean> reviewAsset) {
        for (T asset : assets) {
            log.info("审核：{}", asset);
            if (!reviewAsset.apply(asset)) {
                log.info("未通过！");
                return false;
            }
            log.info("通过！");
        }
        return true;
    }
}
