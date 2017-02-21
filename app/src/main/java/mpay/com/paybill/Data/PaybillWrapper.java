package mpay.com.paybill.Data;

/**
 * Created by stephineosoro on 07/08/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaybillWrapper implements Parcelable {

    @SerializedName("paybill_number")
    @Expose
    private String paybill_number;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public PaybillWrapper(Parcel in) {
        paybill_number = in.readString();
        name = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(paybill_number);
        dest.writeString(name);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     *
     * @return
     * The paybill_number
     */
    public String getPaybill_number() {
        return paybill_number;
    }

    /**
     *
     * @param paybill_number
     * The paybill_number
     */
    public void setPaybill_number(String paybill_number) {
        this.paybill_number = paybill_number;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public static final Creator<PaybillWrapper> CREATOR = new Creator<PaybillWrapper>() {
        @Override
        public PaybillWrapper createFromParcel(Parcel in) {
            return new PaybillWrapper(in);
        }

        @Override
        public PaybillWrapper[] newArray(int size) {
            return new PaybillWrapper[size];
        }
    };
}