package mpay.com.paybill.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import mpay.com.paybill.Model.Favorite;
import mpay.com.paybill.Model.MyShortcuts;
import mpay.com.paybill.R;
import mpay.com.paybill.Model.Wallet;

/**
 * Created by stephineosoro on 31/08/16.
 */
public class AddWallet extends AppCompatActivity {
    @Bind(R.id.btn_submit)
    Button submit;
    @Bind(R.id.input_title)
    EditText etName;
    @Bind(R.id.detail)
    EditText etDescription;
//    @Bind(R.id.number)
//    EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_addwallet);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Wallet");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wallet wallet = new Wallet();
                if (validate()) {
                    wallet.setName(etName.getText().toString());
                    wallet.setDescription(etDescription.getText().toString());
//                    wallet.setNumber(number.getText().toString());
                    /*if (number.getText().toString().isEmpty()) {
                        wallet.setNumber("No account number specified!");
                    } else {

                    }*/
                    wallet.save();
                    MyShortcuts.showToast("Saved! ", getBaseContext());
                    Intent intent = new Intent(getBaseContext(), mpay.com.paybill.Activities.Wallet.class);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean validate() {

        /*ImageView imageView = (ImageView) findViewById(R.id.img);
        imageView.setVisibility(View.INVISIBLE);
        _signupButton.setEnabled(false);
        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.startAnim();
        mProgressView.bringToFront();*/

//        getWindow().getDecorView().setBackgroundColor(Color.DKGRAY);
        boolean valid = true;

        String name = etName.getText().toString();
        String desc = etDescription.getText().toString();

//        if (number.getText().toString().isEmpty()) {
//            number.setError("No account number specified");
//        }

        if (name.isEmpty()) {
            etName.setError("Enter atleast 3 characters");
            valid = false;
        } else {
            etName.setError(null);
        }


        if (desc.isEmpty()) {
            etDescription.setError("Enter some description");
            valid = false;
        } else {
            etDescription.setError(null);
        }


        return valid;
    }

    public class GplayGridCard extends Card {

        protected TextView mTitle;
        protected TextView mSecondaryTitle;
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
            header.setPopupMenu(R.menu.popupmain, new CardHeader.OnClickCardHeaderPopupMenuListener() {
                @Override
                public void onMenuItemClick(BaseCard card, MenuItem item) {
//                    Toast.makeText(getContext(), "Item " + item.getTitle(), Toast.LENGTH_SHORT).show();
                    String selected = card.getId();

                    Toast ToastMessage = Toast.makeText(getApplicationContext(), "No info for " + card.getTitle() + "!", Toast.LENGTH_SHORT);
                    View toastView = ToastMessage.getView();
                    toastView.setBackgroundResource(R.drawable.toast_background_color);
                    ToastMessage.show();
                }
            });
            addCardHeader(header);
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

//            TextView title = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_title);
//            title.setText("I-shop");

            final TextView subtitle = (TextView) view.findViewById(R.id.carddemo_gplay_main_inner_subtitle);
            subtitle.setText(secondaryTitle);
            subtitle.setClickable(true);
            subtitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    subtitle.getText();
                }
            });

           /* final LikeButton likeButton = (LikeButton) view.findViewById(R.id.heart);
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
}
