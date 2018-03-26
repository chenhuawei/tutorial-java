package biz.chenxu.tutorial;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;

import java.io.IOException;
import java.util.Date;

public class MinimalHttpAsyncClientApplication {

    static void sendRequest(CloseableHttpAsyncClient closeableHttpAsyncClient) {
        HttpHost httpHost = HttpHost.create("http://www.biadu.com");
        HttpPost postRequest = new HttpPost("/query");
        String data = "{\"keyword\":\"test\"}]}";
        StringEntity s = new StringEntity(data, ContentType.create("application/json", "utf-8"));
        postRequest.setEntity(s);
        closeableHttpAsyncClient.execute(httpHost, postRequest, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                try {
                    String content = IOUtils.toString(result.getEntity().getContent(), "utf-8");
                    System.out.println(new Date() + ", " + content);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Exception ex) {
                ex.printStackTrace();
            }

            @Override
            public void cancelled() {
                System.err.println("cancelled");
            }
        });
    }

    public static void main(String[] args) throws Exception {

        PoolingNHttpClientConnectionManager clientConnectionManager;
        CloseableHttpAsyncClient closeableHttpAsyncClient;

        clientConnectionManager = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(
                IOReactorConfig.custom().setConnectTimeout(30000).setSoTimeout(30000).build()
        ));
        closeableHttpAsyncClient = HttpAsyncClients.createMinimal(clientConnectionManager, false);

        closeableHttpAsyncClient.start();

        int counter = 3;
        while (--counter >= 0) {
            sendRequest(closeableHttpAsyncClient);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            closeableHttpAsyncClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
