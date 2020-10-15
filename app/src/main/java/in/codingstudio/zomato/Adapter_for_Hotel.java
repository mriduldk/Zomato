package in.codingstudio.zomato;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class Adapter_for_Hotel extends RecyclerView.Adapter<Adapter_for_Hotel.View_Holder> {

    private Context context;
    private ArrayList<Hotel> hotel_details_ArrayList;

    public Adapter_for_Hotel(Context context, ArrayList<Hotel> hotel_details_ArrayList) {
        this.context = context;
        this.hotel_details_ArrayList = hotel_details_ArrayList;

    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.hotel_layout_recycler_view, parent, false);

        return new View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder holder, int position) {


        Hotel hotel = hotel_details_ArrayList.get(position);

        holder.hotenName.setText(hotel.getHOTEL_NAME());
        holder.desc.setText(hotel.getHOTEL_DESC());
        holder.price.setText(hotel.getPRICE());
        holder.rating.setText(hotel.getRATING());
        Log.e("POSITION","P "+position);
        Log.e("POSITION","image "+hotel.getIMAGE());

        if (hotel.getIMAGE() != null && !hotel.getIMAGE().equals("")){

            Picasso.get().load(hotel.getIMAGE())
                    .fit()
                    .into(holder.hotelImage);
        }



    }

    @Override
    public int getItemCount() {
        return hotel_details_ArrayList.size();
    }

    class View_Holder extends RecyclerView.ViewHolder {

        TextView hotenName, desc, rating, price;
        ImageView hotelImage;

        View_Holder(@NonNull View itemView) {
            super(itemView);

            hotelImage = itemView.findViewById(R.id.image_hotel);
            hotenName = itemView.findViewById(R.id.tv_hotel_name);
            desc = itemView.findViewById(R.id.tv_description);
            rating = itemView.findViewById(R.id.tv_rating);
            price = itemView.findViewById(R.id.tv_price);

        }

    }


}
