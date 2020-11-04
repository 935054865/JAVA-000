package src.main.getway.config;

import java.util.ArrayList;

public class GetAwayConfig {

    public static final int PORT = 8083;

    public static final String PROXY_SERVER = "127.0.0.1";
    public static final String PROXY_SERVER_TWO = "127.0.0.1";
    public static final int PROXY_SERVER_PORT = 8081;

    public static String[] getProxyServers() {
        return new String[]{PROXY_SERVER, PROXY_SERVER_TWO};
    }

}
