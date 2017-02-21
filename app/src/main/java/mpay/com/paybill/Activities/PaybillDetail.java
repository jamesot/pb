package mpay.com.paybill.Activities;

/**
 * Created by stephineosoro on 10/08/16.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;
import mpay.com.paybill.Model.AppController;
import mpay.com.paybill.Model.MyShortcuts;
import mpay.com.paybill.Model.Paybill;
import mpay.com.paybill.Model.Post;
import mpay.com.paybill.R;

public class PaybillDetail extends AppCompatActivity {

    protected String id;
    Button bt;
    static CardGridArrayAdapter mCardArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Paybill Detail");
        id = getIntent().getStringExtra("id");

        if (MyShortcuts.hasInternetConnected(this)) {
            getPaybill();
        } else {
            getSqlite("paybill", id);
        }
        id = getIntent().getStringExtra("id");
        Log.e("id", id + "");
        bt = (Button) findViewById(R.id.checkout);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getBaseContext(), GetLocation.class);
                startActivity(intent);*/
            }
        });

    }


    public class GplayGridCard extends Card {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail = -1;
        protected int count;


        protected String headerTitle;
        protected String secondaryTitle;
        protected String id;
        protected String email;
        protected float rating;

        public GplayGridCard(Context context) {
            super(context, R.layout.inner_content_pd);
        }


        public GplayGridCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        private void init() {

            CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(secondaryTitle);

            addCardHeader(header);

            setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {

                }
            });
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
            title.setText("");
/*
            TextView subtitle = (TextView) view.findViewById(R.id.description);
            subtitle.setText(email);*/
            TextView id1 = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
            id1.setText(email);
//            NetworkImageView thumbnail =(NetworkImageView)view.findViewById(R.id.card_thumbnail_image);
//            thumbnail.setImageUrl(url,imageLoader);


        }

        class GplayGridThumb extends CardThumbnail {

            public GplayGridThumb(Context context) {
                super(context);
            }

            @Override
            public void setupInnerViewElements(ViewGroup parent, View viewImage) {
                //viewImage.getLayoutParams().width = 196;
                //viewImage.getLayoutParams().height = 196;

            }
        }

    }

    private void getSqlite(String table_name, String query) {

        List<String> stringList = new ArrayList<String>();
        Cursor cursor = null;
        ArrayList<Card> cards = new ArrayList<Card>();
        try {
            cursor = Connector.getDatabase().rawQuery("select * from " + table_name + " where id='" + query + "'  order by name ASC",
                    null);

            if (cursor.getCount() < 1) {
                MyShortcuts.showToast("No info for this paybill", getBaseContext());
            }
            if (cursor.moveToFirst()) {
                do {

                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String paybill = cursor.getString(cursor.getColumnIndex("paybill_number"));
                    String email = cursor.getString(cursor.getColumnIndex("email"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));

                    GplayGridCard card = new GplayGridCard(getBaseContext());

                    card.headerTitle = name;
                    card.secondaryTitle = paybill + " " + email+ " \n"+description;
                    card.email = email;
                    card.setId(email);
                    card.setTitle(name);
                   /* CardThumbnail thumb = new CardThumbnail(getBaseContext());

                    //Set URL resource
                    thumb.setDrawableResource(R.drawable.heart_off);
//                    thumb.setUrlResource(path);


                    //Error Resource ID
                    thumb.setErrorResource(R.drawable.heart_off);

                    //Add thumbnail to a card
                    card.addCardThumbnail(thumb);*/
                    card.init();
                    cards.add(card);

                    Log.e("paybill", paybill);


                    stringList.add(name);

                } while (cursor.moveToNext());
                Log.e("StringList", stringList.toString() + stringList.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }

            mCardArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

            CardGridView listView = (CardGridView) findViewById(R.id.carddemo_grid_base1);
            if (listView != null) {
                listView.setAdapter(mCardArrayAdapter);
            }
        }
    }


    private void getPaybill() {

        Post.getData(MyShortcuts.baseURL() + "fetchPaybill.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String success = null;
                try {
                    JSONObject jObj = new JSONObject(response);
//                            res = jObj.getJSONArray("All");
                    //successfully gotten matatu data
//                        String regno = jObj.getString("regno");

                    JSONArray res = jObj.getJSONArray("All");

                    Log.e("result: ", res.toString());
                    ArrayList<Card> cards = new ArrayList<Card>();

                    // looping through All res
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject c = res.getJSONObject(i);

                        // Storing each json item in variable

                        String name = c.getString("name");
//                    String description = c.getString("description");
                        //                                children1 = c.getJSONArray("children");
                        Log.e("CategoryFragment", name);
//                    Items items = new Items();
////                    items.setTitle(name);
////                    items.setTheID(c.getString("uuid"));
//                    JSONObject a = c.getJSONObject("primaryImage");
//                    String path = "https://www.oneshoppoint.com/images" + a.getString("path");
//                    items.setThumbnailUrl("https://www.oneshoppoint.com/images" + a.getString("path"));
//                    itemsList.add(items);
                        Log.e("id server",c.getString("id"));
                        if (id.equals(c.getString("id"))) {
                            Log.e("id intent",id);
                            setTitle(c.getString("name")+" - "+c.getString("paybill_number"));
                            GplayGridCard card = new GplayGridCard(getBaseContext());
//                            card.headerTitle = name;
//                            c.getString("paybill_number")+" - "+
                            card.secondaryTitle = c.getString("description");
//                    card.id = c.getString("price");
                            card.setId(c.getString("id"));
//                    card.url = path;
                            card.setTitle(c.getString("name"));
                            CardThumbnail thumb = new CardThumbnail(getBaseContext());
                            //Set URL resource
//                        thumb.setDrawableResource(R.drawable.heart_off);
                            thumb.setUrlResource(c.getString("image"));
                            Log.e("image url is", c.getString("image")+c.getString("description"));
                            //Error Resource ID
                            thumb.setErrorResource(R.drawable.heart_off);

                            card.addCardThumbnail(thumb);
                            //Add thumbnail to a card
                            card.init();
                            cards.add(card);

                        }


//
                    }
                    if (res.length() == 0) {
                        Toast.makeText(getBaseContext(), "No paybills ", Toast.LENGTH_LONG).show();
                    }
                    mCardArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView listView = (CardGridView) findViewById(R.id.carddemo_grid_base1);
                    if (listView != null) {
                        listView.setAdapter(mCardArrayAdapter);
                    }


                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("JSON ERROR", e.toString());
                }


            }
        });
    }


}
