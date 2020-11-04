package src.main.getway.router;

import src.main.getway.config.GetAwayConfig;

public class NettyRouterHandler {

    /**
     * 获取随机服务
     * @return
     */
    public static String getProxyService() {
        String[] proxyServers = GetAwayConfig.getProxyServers();
        int index=(int)(Math.random()*proxyServers.length);
        System.out.println(index);
        return  proxyServers[index];
    }
}
