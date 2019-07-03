package cn.zull.tracing.core.resttemplate;


/**
 * @author zurun
 * @date 2019/3/5 20:52:50
 */
@FunctionalInterface
public interface ThreeArgsConsumer<T, U, V> {

    void accept(T t, U u, V v);
}