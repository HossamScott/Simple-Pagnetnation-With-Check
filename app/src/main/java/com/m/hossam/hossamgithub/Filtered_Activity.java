package com.m.hossam.hossamgithub;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.m.hossam.hossamgithub.Adapters.Main_Activity_Adapter;
import com.m.hossam.hossamgithub.Helpers.ConnectivityReceiver;
import com.m.hossam.hossamgithub.Helpers.Json_Controller;
import com.m.hossam.hossamgithub.Helpers.VolleyCallback;
import com.m.hossam.hossamgithub.Items.Main_Activity_Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Filtered_Activity extends AppCompatActivity {

    private RecyclerView recycler;
    private ArrayList<Main_Activity_Item> items;
    private Main_Activity_Adapter adapter;
    LinearLayoutManager layoutManager;


    private SwipeRefreshLayout refreshLayout;
    private String new_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered);


        SharedPreferences notif_array = getSharedPreferences("items_array", Context.MODE_PRIVATE);
        new_result = notif_array.getString("id", null);


        recycler = findViewById(R.id.main_list);
        recycler.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 1);
        recycler.setLayoutManager(layoutManager);
        items = new ArrayList<>();
        adapter = new Main_Activity_Adapter(items, Filtered_Activity.this);
        recycler.setAdapter(adapter);


        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (items != null) {
                    items.clear();
                }
                load_data();
                refreshLayout.setRefreshing(false);
            }
        });


        if (new_result != null) {/// to avoid crash
            load_data();
        }

    }///////////////////// End of on create


    private void load_data() {
        if (ConnectivityReceiver.isConnected()) {

            new Json_Controller().GetDataFromServer(new VolleyCallback() {
                @Override
                public void onSuccess(String result) throws JSONException {
                    JSONArray array = new JSONArray(result);

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        Main_Activity_Item model = new Main_Activity_Item();
                        model.setId(object.getString("id"));
                        model.setTitle(object.getString("title"));
                        model.setAlbum_id(object.getString("albumId"));
                        model.setPhoto(object.getString("thumbnailUrl"));
                        model.setFiltered("hide");
                        items.add(model);
                    }
                    adapter = new Main_Activity_Adapter(items, Filtered_Activity.this);
                    recycler.setAdapter(adapter);

                }

                @Override
                public void onError(VolleyError error) {
                }
            }, this, "https://jsonplaceholder.typicode.com/photos?" + new_result, false);

        } else {
            Toast.makeText(Filtered_Activity.this, R.string.turn_net, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        SharedPreferences.Editor clear = Filtered_Activity.this.getSharedPreferences("items_array", Context.MODE_PRIVATE).edit();
        clear.clear();
        clear.apply();
        super.onPause();
    }

}
