package in.codingstudio.zomato;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.codingstudio.zomato.server.HTTP_Get;
import in.codingstudio.zomato.server.JSONProvider;

public class Fragment_Main_Page extends Fragment {

    TextView et_search, tv_result;
    RecyclerView recyclerView_hotel;
    RecyclerView.Adapter adapter_recyclerView_hotel;
    ArrayList<Hotel> arrayList;
    View root;

    Bundle bundle;
    String entity_type;
    int entity_id;
    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.fragment_main_page,container,false);

            arrayList = new ArrayList<>();

            initialise();
        }

        return root;
    }

    private void initialise() {

        bundle  = getArguments();

        et_search = root.findViewById(R.id.et_search);
        tv_result = root.findViewById(R.id.tv_result);
        recyclerView_hotel = root.findViewById(R.id.recycler_view);

        if (bundle != null){
            entity_id = bundle.getInt("ENTITY_ID");
            entity_type = bundle.getString("ENTITY_TYPE");
            tv_result.setText("Showing Hotels of "+bundle.getString("CITY"));
        }

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        Get_Data();


        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new FragmentOpener(getActivity()).replace_With_BackStack(getFragmentManager(), R.id.f_container, new Fragment_Search_Page());
            }
        });


    }

    private void Get_Data() {


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "https://developers.zomato.com/api/v2.1/location_details?entity_id=" + entity_id + "&entity_type="+entity_type;
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        //Log.e("Response", response);

                        try{
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("best_rated_restaurant");

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
                            progressDialog.dismiss();

                        }


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.e("ERROR","error => "+error.toString());
                        progressDialog.dismiss();

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
        progressDialog.dismiss();
        //relativeLayout_cake.setVisibility(View.VISIBLE);
        //progressBar.setVisibility(View.GONE);

    }


}
