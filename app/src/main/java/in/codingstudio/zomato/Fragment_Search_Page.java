package in.codingstudio.zomato;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_Search_Page extends Fragment {

    EditText et_search;
    RecyclerView recyclerView_hotel;
    RecyclerView.Adapter adapter_recyclerView_hotel;
    ArrayList<Hotel> arrayList;
    View root;

    Bundle bundle;
    String entity_type;
    int entity_id;

    ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.fragment_search_page,container,false);

            arrayList = new ArrayList<>();

            initialise();
        }

        return root;
    }

    private void initialise() {

        bundle  = getArguments();

        if (bundle != null){
            entity_id = bundle.getInt("ENTITY_ID");
            entity_type = bundle.getString("ENTITY_TYPE");
        }

        et_search = root.findViewById(R.id.et_search);
        recyclerView_hotel = root.findViewById(R.id.recycler_view);
        progressBar = root.findViewById(R.id.progress_bar);

        et_search.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        progressBar.setVisibility(View.GONE);


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Get_Data(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void Get_Data(String res_name) {

        progressBar.setVisibility(View.VISIBLE);


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://developers.zomato.com/api/v2.1/search?q="+res_name;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        //Log.e("Response", response);

                        try{
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("restaurants");

                            //Log.e("ARRAY", "array: "+array);

                            for (int i=0;i<array.length();i++){

                                JSONObject jsonObject = array.getJSONObject(i);
                                JSONObject jsonObject2 = jsonObject.getJSONObject("restaurant");


                                Hotel hotel = new Hotel();
                                hotel.setHOTEL_NAME(jsonObject2.getString("name"));
                                hotel.setHOTEL_DESC(jsonObject2.getString("cuisines"));
                                hotel.setIMAGE(jsonObject2.getString("thumb"));
                                hotel.setPRICE(jsonObject2.getString("average_cost_for_two"));
                                hotel.setRATING(jsonObject2.getJSONObject("user_rating").getString("aggregate_rating"));


                                arrayList.add(i, hotel);
                                Log.e("POSITION","P "+i);
                            }

                            RecyclerView_();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Something is wrong !!", Toast.LENGTH_SHORT).show();
                            Log.e("ERROR2", "ERROR: "+e);
                            progressBar.setVisibility(View.GONE);

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("ERROR","error => "+error.toString());
                        progressBar.setVisibility(View.GONE);

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("user-key", "953975b23d9cc91e369dffcd1c29bea1");

                return params;
            }
        };
        queue.add(getRequest);


    }

    private void RecyclerView_() {


        adapter_recyclerView_hotel =  new Adapter_for_Hotel(getActivity(),arrayList);
        recyclerView_hotel.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_hotel.setAdapter(adapter_recyclerView_hotel);

        progressBar.setVisibility(View.GONE);

        //relativeLayout_cake.setVisibility(View.VISIBLE);
        //progressBar.setVisibility(View.GONE);

    }


}
