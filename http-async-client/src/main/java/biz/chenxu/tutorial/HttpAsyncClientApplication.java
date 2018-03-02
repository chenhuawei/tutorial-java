package biz.chenxu.tutorial;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HttpAsyncClientApplication {

    public static void main(String[] args) {

        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        try {
            // start reactorThread
            httpClient.start();

            HttpPost post = new HttpPost("http://www.baidu.com");
            Future<HttpResponse> future = httpClient.execute(post, new FutureCallback<HttpResponse>() {

                @Override
                public void completed(HttpResponse httpResponse) {
                    System.out.println("completed");
                }

                @Override
                public void failed(Exception e) {
                    e.printStackTrace();
                }

                @Override
                public void cancelled() {
                    System.out.println("cancelled");
                }
            });
            HttpResponse httpResponse = future.get();
            String content = IOUtils.toString(httpResponse.getEntity().getContent(), "utf-8");
            System.out.println(content);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // join reactorThread
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
