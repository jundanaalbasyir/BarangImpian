package com.competition.bukalapak.barangimpian.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.competition.bukalapak.barangimpian.R;
import com.competition.bukalapak.barangimpian.adapter.cobaAdapter;
import com.competition.bukalapak.barangimpian.controller.data_timeline;
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class cobaFragment extends Fragment {

    private static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;
    private RecyclerView mRVKos;
    private cobaAdapter mAdapter;
    SwipeRefreshLayout swLayout;

    @BindView(R.id.timeline_list_coba)
    RecyclerView mRecyclerView;

    public static cobaFragment newInstance() {
        return new cobaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Toast.makeText(getActivity(),"ini percobaan",Toast.LENGTH_SHORT).show();
        new AsyncFetch().execute();
        return inflater.inflate(R.layout.fragment_coba, container, false);
    }

    private class AsyncFetch extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(getActivity());
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your json file resides
                // Even you can make call to php file which returns json data

//                url = new URL("http://barangimpian.com/api/timeline.php");
                url = new URL("http://barangimpian.com/api/dummy_tl.json");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return e.toString();
            }
            try {
                String user_id = "32216311";
                String token = "NrrX9XsHWWqqzZeKz";
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

//                setheader
                String credentials = user_id+":"+token;
                conn.setRequestProperty("Authorization","Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP));

                // setDoOutput to true as we recieve data from json file
                conn.setDoOutput(true);

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return e1.toString();
            }

            try {

                int response_code = conn.getResponseCode();

                // Checkif successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();
            List<data_timeline> data=new ArrayList<>();

            pdLoading.dismiss();
            try {

                JSONArray jArray = new JSONArray(result);

                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);

                    JSONObject impian=json_data.getJSONObject("impian");
                    data_timeline data_tl = new data_timeline();
                    data_tl.ti_name= impian.getString("name");
                    data_tl.ti_idbarang= impian.getString("id_barang");
                    JSONArray ti_small_image = impian.getJSONArray("small_image");
                    data_tl.ti_smallimage=ti_small_image.getString(0);

                    JSONObject dimiliki=json_data.getJSONObject("dimiliki");
                    data_tl.td_name= dimiliki.getString("name");
                    data_tl.td_idbarang= dimiliki.getString("id_barang");
                    JSONArray td_small_image = dimiliki.getJSONArray("small_image");
                    data_tl.td_smallimage=td_small_image.getString(0);

//                    data_tl.ti_image_ids= impian.getString("image_ids");


//                    Toast.makeText(getActivity(), "nama: "+data_tl.ti_name, Toast.LENGTH_LONG).show();
//                    Toast.makeText(getActivity(), "idbarang: "+data_tl.ti_idbarang, Toast.LENGTH_LONG).show();
//                    Toast.makeText(getActivity(), "image: "+data_tl.ti_smallimage, Toast.LENGTH_LONG).show();
                    data.add(data_tl);
                }

                // Setup and Handover data to recyclerview
                mRVKos = (RecyclerView)getView().findViewById(R.id.timeline_list_coba);
                mAdapter = new cobaAdapter(getActivity(), data);

                mRVKos.setAdapter(mAdapter);

                mRVKos.setLayoutManager(new LinearLayoutManager(getActivity()));

            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
            }

        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        final List<data_timeline> items = new ArrayList<>();

        for (int i = 0; i < ITEM_COUNT; ++i) {
            items.add(new data_timeline());
        }

        //setup materialviewpager

        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);
//
        //Use this now
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
        mRecyclerView.setAdapter(new cobaAdapter(getActivity(),items));
    }
}