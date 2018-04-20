package intentdemo.deayea.com.intentdemo;

import java.util.HashMap;

/**
 * Created by danish on 2018/4/19.
 */

public class YhdSingleton {

    //单例模式实例
    private static YhdSingleton instance = null;

    //synchronized 用于线程安全，防止多线程同时创建实例
    public synchronized static YhdSingleton getInstance(){
        if (instance == null) {
            instance = new YhdSingleton();
        }
        return instance;
    }

    final HashMap<String, Object> mMap;
    private YhdSingleton() {
        mMap = new HashMap<String, Object>();
    }

    public void put(String key,Object value) {
        mMap.put(key, value);
    }

    public Object get(String key) {
        return mMap.get(key);
    }

}
