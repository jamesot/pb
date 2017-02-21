package mpay.com.paybill.Model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by stephineosoro on 28/06/16.
 */
public class Favorite extends DataSupport {

    @Column(unique = true)
    private long id;

    private String name;


    private String email;

    private String paybill_number;

    private String sent;

    private String favorite;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getPaybill_number() {
        return paybill_number;
    }

    public void setPaybill_number(String paybill_number) {
        this.paybill_number = paybill_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

