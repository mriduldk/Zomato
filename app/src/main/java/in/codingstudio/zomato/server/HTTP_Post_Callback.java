package in.codingstudio.zomato.server;

import com.android.volley.VolleyError;


public interface HTTP_Post_Callback {
    void onSuccess(String string);
    void onError(VolleyError error);
}
