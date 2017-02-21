package mpay.com.paybill.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.like.LikeButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;
import mpay.com.paybill.Model.Favorite;
import mpay.com.paybill.Model.MyShortcuts;
import mpay.com.paybill.Model.Paybill;
import mpay.com.paybill.Model.Post;
import mpay.com.paybill.R;

public class Delete extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    static CardGridArrayAdapter mCardArrayAdapter;
    protected ArrayList<Card> cards = new ArrayList<Card>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        OnlinePaybill();

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*  @Override
      public boolean onCreateOptionsMenu(Menu menu) {
          // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.delete, menu);
          return true;
      }

      @Override
      public boolean onOptionsItemSelected(MenuItem item) {
          // Handle action bar item clicks here. The action bar will
          // automatically handle clicks on the Home/Up button, so long
          // as you specify a parent activity in AndroidManifest.xml.
          int id = item.getItemId();

          //noinspection SimplifiableIfStatement
          if (id == R.id.action_settings) {
              return true;
          }

          return super.onOptionsItemSelected(item);
      }
  */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(this, AdminLogin.class);
            startActivity(intent);
//            finish();

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);

        } else if (id == R.id.wallet) {
            Intent intent = new Intent(this, Wallet.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class GplayGridCard extends Card {

        protected String mTitle;
        protected TextView mSecondaryTitle;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail = -1;
        protected int count;
        protected String url;

        protected String headerTitle;
        protected String secondaryTitle;
        protected float rating;

        public GplayGridCard(Context context) {
            super(context, R.layout.inner_content_detail);
        }


        public GplayGridCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        private void init() {

            CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(headerTitle);
            header.setPopupMenu(R.menu.delete_item, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
//                    Toast.makeText(getContext(), "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    String selected = card.getId();
                    ;
                   /* Toast ToastMessage = Toast.makeText(getApplicationContext(), "No info for " + card.getTitle() + "!", Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundResource(R.drawable.toast_background_color);
                    ToastMessage.show();
*/
//                    final TextView subtitle = (TextView) findViewById(R.id.carddemo_gplay_main_inner_subtitle);
                    if (item.getTitle().equals("Delete")) {
                        String ID = card.getId();
                        cards.remove(card); //It is an example.
                        mCardArrayAdapter.notifyDataSetChanged();
                        Del(ID);
                    } else {

                    }
//                    ID = card.getId();
                }
            });

            addCardHeader(header);
//            Log.e("URL", url);


//            NetworkImageView thumbnail1 =(NetworkImageView) getActivity().findViewById(R.id.card_thumbnail_image);
//            thumbnail1.setImageUrl(url,imageLoader);
//
//            GplayGridThumb thumbnail = new GplayGridThumb(getContext());
//            thumbnail.setUrlResource(url);
//            if (resourceIdThumbnail > -1)
//                thumbnail.setDrawableResource(resourceIdThumbnail);
//            else
//                thumbnail.setDrawableResource(R.drawable.ic_launcher);
//            addCardThumbnail(thumbnail);


          /*  OnCardClickListener clickListener = new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    //Do something
                }
            };

            addPartialOnClickListener(Card.CLICK_LISTENER_CONTENT_VIEW, clickListener);*/
         /*   setOnClickListener(new OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
//                    Do something
                    String selected= card.getId();
                    Toast.makeText(getBaseContext(), "Item ID is" + selected, Toast.LENGTH_LONG).show();
                   *//* Intent intent =new Intent(getBaseContext(),ProductDetail.class);
                    intent.putExtra("id",selected);
                    intent.putExtra("product_name",card.getTitle());
                    startActivity(intent);*//*
                }
            });*/


        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
            title.setText(mTitle);

            final TextView subtitle = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
            subtitle.setText(secondaryTitle);
//            subtitle.setTextIsSelectable(true);
            subtitle.setClickable(true);
            subtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(subtitle.getText());
//                    Toast.makeText(getContext(), "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                    MyShortcuts.showToast("Copied to clipboard!", getBaseContext());
                /*    Intent intent = new Intent(getContext(), EditPatient.class);
                    intent.putExtra("ID", getId());
                    startActivity(intent);*/
//                    getParentCard().getId();
                    subtitle.getText();
                }
            });


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


    private void OnlinePaybill() {
        Log.e("online", "online");

        Post.getData(MyShortcuts.baseURL() + "fetchPaybill.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String success = null;
                try {
                    Log.e("result string", response);
                    JSONObject jObj = new JSONObject(response);
//                            res = jObj.getJSONArray("All");
                    //successfully gotten matatu data
//                        String regno = jObj.getString("regno");


                    JSONArray res = jObj.getJSONArray("All");


                    if (res.length() < 1) {
                        MyShortcuts.showToast("No paybill! Check later", getBaseContext());
                    }

                    Log.e("result: ", res.toString());
//                    ArrayList<Card> cards = new ArrayList<Card>();

                    // looping through All res
                    for (int i = 0; i < res.length(); i++) {
                        JSONObject c = res.getJSONObject(i);

                        String name = c.getString("name");
                        String paybill = c.getString("paybill_number");
                        String email = c.getString("email");
                        String type = c.getString("type");
                        String id = c.getString("id");


                        GplayGridCard card = new GplayGridCard(getBaseContext());

                        card.headerTitle = name;
                        card.secondaryTitle = paybill;
                        card.mTitle = type;
                        card.setId(id + "");
                        card.setTitle(name);

                        card.init();
                        cards.add(card);

                    }
                    mCardArrayAdapter = new CardGridArrayAdapter(getBaseContext(), cards);

                    CardGridView listView = (CardGridView) findViewById(R.id.carddemo_grid_base1);
                    if (listView != null) {
                        listView.setAdapter(mCardArrayAdapter);
                    }

                } catch (JSONException e) {
                    // JSON error
                    MyShortcuts.showToast("No paybills! Check later", getBaseContext());
                    e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("JSON ERROR", e.toString());
                }


            }

        });

    }


    private void Del(String ID) {
        Log.e("online", "online");
        final String ID1 = ID;
        Post.PostString(MyShortcuts.baseURL() + "delete.php", ID, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                String success = null;
                try {
                    Log.e("result string", response);
                    JSONObject jObj = new JSONObject(response);
                    if (jObj.getString("success").equals("success")) {
                        MyShortcuts.showToast("Successfully deleted the paybill!", getBaseContext());
                        truncatePaybill(getBaseContext(), ID1);


                    }


                } catch (JSONException e) {
                    // JSON error
                    MyShortcuts.showToast("No paybills! Check later", getBaseContext());
                    e.printStackTrace();
//                    Toast.makeText(getBaseContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("JSON ERROR", e.toString());
                }


            }

        });

    }

    public static void truncatePaybill(Context context, String id) {
        Cursor cursor = null;
        Cursor cursor2 = null;
        Boolean ret = false;

        try {

            int rowsAffected = DataSupport.deleteAll(Paybill.class, "id=?",
                    id);
            Toast.makeText(
                    context,
                    "rows " + rowsAffected, Toast.LENGTH_SHORT).show();


            cursor = Connector.getDatabase().rawQuery("Delete from paybill",
                    null);
            cursor2 = Connector.getDatabase().rawQuery("DELETE FROM SQLITE_SEQUENCE WHERE name='paybill'",
                    null);
            Log.e("delete row count", cursor.getCount() + " 1 and 2 is  " + cursor2.getCount());
            if (cursor.getCount() < 1) {

                Log.e("data", "No data");
                ret = true;

            } else {
                ret = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor2 != null) {
                cursor2.close();
            }
        }
    }


}
