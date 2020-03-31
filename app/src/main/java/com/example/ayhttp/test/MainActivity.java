package com.example.ayhttp.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.ayhttp.AYHttpClient;
import com.example.ayhttp.Call;
import com.example.ayhttp.Callback;
import com.example.ayhttp.R;
import com.example.ayhttp.Request;
import com.example.ayhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AYHttpClient client = new AYHttpClient();
                Request request = new Request.Builder()
                        .get()
                        .url("www.baidu.com")
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        InputStream in = response.body().in();
//                        int len;
//                        StringBuffer buf = new StringBuffer();
//                        byte[] b = new byte[1024];
//                        len = in.read(b);
//                        Log.d("aaa", "onResponse1: len:" + len);
//                        buf.append(new String(b, 0, len, "UTF-8"));
                        Log.d("aaa", "onResponse2: "+response.message());
//
//                        Log.d("aaa", "onResponse3: " + buf.toString());
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        }).start();


    }


}
