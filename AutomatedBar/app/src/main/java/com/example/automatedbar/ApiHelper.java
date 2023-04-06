package com.example.automatedbar;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApiHelper {
    private static Context context;
    private static String Authorization;

    public ApiHelper(Context context, String Auth) {
        this.context = context;
        this.Authorization = Auth;
    }

    private static final String BASE_URL = "localhost:5000";

    public static void get_data(Response.Listener<String> successListener, Response.ErrorListener errorListener) {
        String url = BASE_URL + "/data";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, successListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Authorization);
                return headers;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        queue.add(stringRequest);
    }
}
