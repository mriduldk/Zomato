package in.codingstudio.zomato;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    EditText et_search;
    RecyclerView recyclerView_hotel;
    RecyclerView.Adapter adapter_recyclerView_hotel;
    ArrayList<Hotel> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        et_search = findViewById(R.id.et_search);
//        recyclerView_hotel = findViewById(R.id.recycler_view);

        new FragmentOpener(this).replace(getSupportFragmentManager(), R.id.f_container, new Fragment_bottom_sheet());






    }
}
