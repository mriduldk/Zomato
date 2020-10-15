package in.codingstudio.zomato;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.codingstudio.zomato.server.HTTP_Get;
import in.codingstudio.zomato.server.JSONProvider;

public class Fragment_bottom_sheet extends Fragment {

    View root;
    EditText et_search;
    ArrayList<City> cities;
    ListView listView;
    TextWatcher textWatcher;

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    ArrayList<City> arrayList_city;
    ProgressBar progressBar;
    Context context;


    String url = "https://developers.zomato.com/1b3c8b37ea96785391fa55c288ac385c/v2.1/locations?query=";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.location_bottom_sheet,container,false);

            cities = new ArrayList<>();

            initialise();
        }


        return root;
    }

    private void initialise() {
        context = getActivity();

        et_search = root.findViewById(R.id.et_search_location);
        listView = root.findViewById(R.id.listView);
        progressBar = root.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        arrayList = new ArrayList<>();
        arrayList_city = new ArrayList<>();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList );
        listView.setAdapter(adapter);


        arrayList.add("History");
        arrayList.add("History");
        arrayList.add("History");


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                getData(charSequence.toString());



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                City city = arrayList_city.get(i);


                bundle.putString("ENTITY_TYPE", ""+city.getENTITY_TYPE());
                bundle.putInt("ENTITY_ID", city.getENTITY_ID());
                bundle.putString("CITY", ""+city.getCITY());

                Fragment_Main_Page fragment_main_page = new Fragment_Main_Page();
                fragment_main_page.setArguments(bundle);
                new FragmentOpener(getActivity()).replace(getFragmentManager(), R.id.f_container, fragment_main_page);

            }
        });



    }

    private void getData(String query) {
        progressBar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://developers.zomato.com/api/v2.1/locations?query="+query;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        //Log.e("Response", response);

                        try{
                            JSONObject object = new JSONObject(response);
                            String success = object.getString("status");

                            if (success.equals("success")) {

                                adapter.clear();

                                JSONArray array = object.getJSONArray("location_suggestions");

                                //Log.e("ARRAY", "array: "+array);

                                for (int i=0;i<array.length();i++){

                                    JSONObject jsonObject = array.getJSONObject(i);
                                    Log.e("ARRAY", "array: "+jsonObject.getString("city_name"));

                                    City city = new City();
                                    city.setCITY(jsonObject.getString("city_name"));
                                    city.setENTITY_TYPE(jsonObject.getString("entity_type"));
                                    city.setENTITY_ID(jsonObject.getInt("entity_id"));
                                    city.setCITY_ID(jsonObject.getInt("city_id"));

                                    arrayList_city.add(i,city);
                                    arrayList.add(city.getCITY());

                                }

                                adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, arrayList );
                                listView.setAdapter(adapter);
                                progressBar.setVisibility(View.GONE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Something is wrong !!", Toast.LENGTH_SHORT).show();
                            Log.e("ERROR2", "ERROR: "+e);
                            progressBar.setVisibility(View.VISIBLE);

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("ERROR","error => "+error.toString());
                        progressBar.setVisibility(View.VISIBLE);

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


}
