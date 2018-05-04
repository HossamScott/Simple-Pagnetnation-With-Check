package com.m.hossam.hossamgithub.Helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.m.hossam.hossamgithub.R;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


public class Json_Controller {
    private Response.ErrorListener mErrorListener;


    public void GetDataFromServer(final VolleyCallback callback, final Activity context, String url, final boolean Authorization) {
        final String tag_string_req = "string_req";
        ProgressDialog dialog = null;
        if (dialog == null) {
            dialog = createProgressDialog(context);
            dialog.show();
        }

        final ProgressDialog finalDialog = dialog;
        final StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {  finalDialog.dismiss();
                    String encodingString = URLEncoder.encode(response, "ISO-8859-1");
                    response = URLDecoder.decode(encodingString, "UTF-8");

                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                try {
                    callback.onSuccess(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                callback.onError(error);
                finalDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                if (Authorization){

                        params.put("Authorization", "Bearer " + "");

                }

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    public void GetDataFromServer_Without_bar(final VolleyCallback callback, final Activity context, String url, final boolean Authorization) {
        final String tag_string_req = "string_req";


        final StringRequest strReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String encodingString = URLEncoder.encode(response, "ISO-8859-1");
                    response = URLDecoder.decode(encodingString, "UTF-8");


                } catch (UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
                try {
                    callback.onSuccess(response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                callback.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                if (Authorization){
                        params.put("Authorization", "Bearer " + "");
                }

                return params;
            }
        };
        strReq.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (WindowManager.BadTokenException ignored) {
        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog_layout);
        return dialog;
    }
}
