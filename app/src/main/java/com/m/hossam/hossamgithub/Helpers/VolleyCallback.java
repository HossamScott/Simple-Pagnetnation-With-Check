package com.m.hossam.hossamgithub.Helpers;

import com.android.volley.VolleyError;

import org.json.JSONException;


public interface VolleyCallback {
     void onSuccess(String result) throws JSONException;
     void onError(VolleyError error);
}