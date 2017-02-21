package mpay.com.paybill.Model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephineosoro on 28/06/16.
 */
public class Wallet extends DataSupport {

    @Column(unique = true)
    private long id;

    private String name;

    private String description;

    private String number;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
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

