package com.mysql.demo.handler;

import com.mysql.demo.common.DbTypeEm;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class DbContextHandler {

    private static final ThreadLocal<Optional<DbTypeEm>> dbTypeEmThreadLocal = new ThreadLocal<>();
    private static final AtomicInteger atoCounter = new AtomicInteger(0);

    public static void setDb(DbTypeEm dbTypeEm) {
        dbTypeEmThreadLocal.set(Optional.ofNullable(dbTypeEm));
    }

    public static Optional<DbTypeEm> getDb() {

        return dbTypeEmThreadLocal.get();
    }

    public static void remove() {
        dbTypeEmThreadLocal.remove();
    }

    /**
     * 设置主从库
     *
     * @param isMaster
     */
    public static void setDb(boolean isMaster) {
        System.out.println("啊啊啊啊进入");
        if (isMaster) {
            //主库
            setDb(DbTypeEm.db0);
        } else {
            //从库
            setSlave();
        }
    }

    private static void setSlave() {
        //累加值达到最大时，重置
        if (atoCounter.get() >= 100000) {
            atoCounter.set(0);
        }

        //排除master，选出当前线程请求要使用的db从库 - 从库算法
        int slaveNum = atoCounter.getAndIncrement() % (DbTypeEm.values().length - 1);
        Optional<DbTypeEm> dbTypeEm = DbTypeEm.getDbTypeBySlaveNum(slaveNum);
        if (dbTypeEm.isPresent()) {
            setDb(dbTypeEm.get());
        } else {
            throw new IllegalArgumentException("从库未匹配");
        }
    }
}
