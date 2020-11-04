package src.main.getway.filter;

import okhttp3.Request;

public class OkHttpFilterHandler {

    public static void headerAddNameHandler(Request.Builder requestBuilder) {
        requestBuilder.addHeader("add-info", "经过一层过滤");
    }
}
