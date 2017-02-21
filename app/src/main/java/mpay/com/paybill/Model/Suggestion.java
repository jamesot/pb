package mpay.com.paybill.Model;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by stephineosoro on 04/08/16.
 */
public class Suggestion implements SearchSuggestion {

    private String PaybillName;
    private String mIsHistory;

    public Suggestion(String suggestion) {
        this.PaybillName = suggestion;
    }

    public Suggestion(Parcel source) {
        this.PaybillName = source.readString();
        this.mIsHistory = source.readString();
    }

    public void setIsHistory(String isHistory) {
        this.mIsHistory = isHistory;
    }

    public String getIsHistory() {
        return this.mIsHistory;
    }

    public static final Creator<Suggestion> CREATOR = new Creator<Suggestion>() {
        @Override
        public Suggestion createFromParcel(Parcel in) {
            return new Suggestion(in);
        }

        @Override
        public Suggestion[] newArray(int size) {
            return new Suggestion[size];
        }
    };


    @Override
    public String getBody() {
        return PaybillName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PaybillName);
        dest.writeString(mIsHistory);
    }
}
