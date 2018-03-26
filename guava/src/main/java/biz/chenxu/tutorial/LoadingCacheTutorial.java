package biz.chenxu.tutorial;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LoadingCacheTutorial {

    private LoadingCache<String, String> loadingCache;

    private int counter;

    private static LoadingCacheTutorial instance = new LoadingCacheTutorial();

    public static LoadingCacheTutorial getInstance() {
        return instance;
    }

    private LoadingCacheTutorial() {
        loadingCache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .build(new CacheLoader<String, String>() {

                    @Override
                    public String load(String key) throws Exception {
                        System.out.println(String.format("loading not exist key [%s]", key));
                        return String.format("cache value %d", counter++);
                    }
                });
    }

    public String getCacheValueIfExist(String key) {
        return loadingCache.getIfPresent(key);
    }

    public String getCacheValue(String key) {
        try {
            return loadingCache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void dump() {
        System.out.println("***begin dump cache content***");
        for (Map.Entry<String, String> entry : loadingCache.asMap().entrySet()) {
            System.out.println(String.format("%s = %s", entry.getKey(), entry.getValue()));
        }
        System.out.println("***end dump cache content***");
    }

    public static void main(String[] args) {

        // load notExistKey1
        String notExistKey1 = "notExistKey1";
        String notExistValue1 = LoadingCacheTutorial.getInstance().getCacheValue(notExistKey1);
        System.out.println(String.format("1.%s = %s", notExistKey1, notExistValue1));

        // don't load notExistKey2
        String notExistKey2 = "notExistKey2";
        String notExistValue2 = LoadingCacheTutorial.getInstance().getCacheValueIfExist(notExistKey2);
        System.out.println(String.format("2.%s = %s", notExistKey2, notExistValue2));

        // LRU
        // load notExistKey2
        LoadingCacheTutorial.getInstance().getCacheValue(notExistKey2);
        LoadingCacheTutorial.getInstance().dump();

        System.err.println("[Warn] the key2 will be removed by LRU strategy ......");
        // use key1
        LoadingCacheTutorial.getInstance().getCacheValue(notExistKey1);
        LoadingCacheTutorial.getInstance().getCacheValue(notExistKey1);

        // load notExistKey3
        LoadingCacheTutorial.getInstance().getCacheValue("notExistKey3");

        // load notExistKey4
        LoadingCacheTutorial.getInstance().getCacheValue("notExistKey4");

        LoadingCacheTutorial.getInstance().dump();
    }
}
