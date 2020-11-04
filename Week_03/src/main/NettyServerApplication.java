package src.main;

import src.main.getway.inbound.HttpInboundServer;

public class NettyServerApplication {



    public static void main(String[] args) {
        HttpInboundServer server = new HttpInboundServer();
        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
