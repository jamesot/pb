package mpay.com.paybill.Activities;

/**
 * Created by stephineosoro on 31/08/16.
 */

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.like.LikeButton;
import com.like.OnLikeListener;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.view.CardGridView;
import mpay.com.paybill.Model.Favorite;
import mpay.com.paybill.Model.MyShortcuts;
import mpay.com.paybill.R;

public class Wallet extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static CardGridArrayAdapter mCardArrayAdapter;
    protected ArrayList<Card> cards = new ArrayList<Card>();
    /* @Bind(R.id.floating_search_view)
     FloatingSearchView mSearchView;*/
    private String mLastQuery;
    /*LinearLayout searchContainer;
    EditText toolbarSearchView;
    ImageView searchClearButton;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Wallet");
        getSqlite("wallet");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddWallet.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }


    public class GplayGridCard extends Card {

        protected TextView mTitle;
        protected String mNumber;
        protected RatingBar mRatingBar;
        protected int resourceIdThumbnail = -1;
        protected int count;
        protected String url;

        protected String headerTitle;
        protected String secondaryTitle;
        protected float rating;

        public GplayGridCard(Context context) {
            super(context, R.layout.inner_content_wallet);
        }


        public GplayGridCard(Context context, int innerLayout) {
            super(context, innerLayout);
        }

        private void init() {

            CardHeader header = new CardHeader(getContext());
            header.setButtonOverflowVisible(true);
            header.setTitle(headerTitle);
            header.setPopupMenu(R.menu.delete, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
//                    Toast.makeText(getContext(), "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    String selected = card.getTitle();

                    cards.remove(card); //It is an example.
                    mCardArrayAdapter.notifyDataSetChanged();
                    Delete(selected);
                    Toast ToastMessage = Toast.makeText(getApplicationContext(), "deleted " + card.getTitle() + "!", Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundResource(R.drawable.toast_background_color);
                    ToastMessage.show();
                }
            });
            addCardHeader(header);
        }



        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
            title.setText(headerTitle);
            /*final TextView number = (TextView) view.findViewById(R.id.number);
            number.setText(mNumber);
            number.setClickable(true);
            number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(number.getText());
//                    Toast.makeText(getContext(), "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                    MyShortcuts.showToast("Copied to clipboard!", getBaseContext());
                *//*    Intent intent = new Intent(getContext(), EditPatient.class);
                    intent.putExtra("ID", getId());
                    startActivity(intent);*//*
//                    getParentCard().getId();

                }
            });
*/
            final TextView subtitle = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
            subtitle.setText(secondaryTitle);
            subtitle.setClickable(true);
            subtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    cm.setText(subtitle.getText());
//                    Toast.makeText(getContext(), "Copied to clipboard!", Toast.LENGTH_SHORT).show();
                    MyShortcuts.showToast("Copied to clipboard!", getBaseContext());
                    subtitle.getText();
                }
            });

         /*   final LikeButton likeButton = (LikeButton) view.findViewById(R.id.heart);
            likeButton.setLiked(true);
            likeButton.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    MyShortcuts.showToast(getCardHeader().getTitle() + " added to my Favorites!", getBaseContext());
                    Favorite favorite = new Favorite();
                    favorite.setName(getCardHeader().getTitle());
                    favorite.setEmail(getCardView().getCard().getId());
                    favorite.setFavorite("true");
                    favorite.setSent("true");
                    favorite.setPaybill_number(subtitle.getText().toString());
                    favorite.save();
                }

                @Override
                public void unLiked(LikeButton likeButton) {
//                    TODO implement Context menu to verify if the user really wants to unlike
                }
            });*/
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

    private void Delete(String selected) {
        DataSupport.deleteAll(Wallet.class,"name=?",selected);
    }


    private void getSqlite(String table_name) {
        List<String> stringList = new ArrayList<String>();
        Cursor cursor = null;

        try {
            cursor = Connector.getDatabase().rawQuery("select * from " + table_name + " order by id",
                    null);
            if (cursor.getCount() < 1) {
                MyShortcuts.showToast("You don't have any Wallet info. Once you add a wallet it would appear here", getBaseContext());
            }
            if (cursor.moveToFirst()) {
                do {

                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String number = cursor.getString(cursor.getColumnIndex("number"));


                    GplayGridCard card = new GplayGridCard(getBaseContext());

                    card.headerTitle = name;
                    card.secondaryTitle = description;
                    card.mNumber=number;

                    card.setTitle(name);

                    card.init();
                    cards.add(card);

                    Log.e("name", name);


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            this.slidingMenu.toggle();
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.slidingMenu.toggle();
//                return true;

            case R.id.login:
                Intent intent = new Intent(getBaseContext(), SavePaybill.class);
                startActivity(intent);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
//            finish();

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(this, Favorites.class);
            startActivity(intent);

        } else if (id == R.id.wallet) {
            Intent intent = new Intent(this, Wallet.class);
            startActivity(intent);

        } else if (id == R.id.contactus) {
            Intent intent = new Intent(this, ContactUs.class);
            startActivity(intent);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
