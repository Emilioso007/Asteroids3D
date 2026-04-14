package io.asteroidsjaylib.common.util;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ObjectPool<T extends IPoolable> {

    private final ArrayList<T> pool;
    private final Supplier<T> factory;

    public ObjectPool(Supplier<T> factory, int initialCapacity){
        this.factory = factory;
        this.pool = new ArrayList<>(initialCapacity);

        for (int i = 0; i < initialCapacity; i++){
            pool.add(factory.get());
        }
    }

    public T obtain(){
        if (pool.isEmpty()){
            return factory.get();
        }
        return pool.removeLast();
    }

    public void free(T object){
        object.reset();
        pool.add(object);
    }

}
