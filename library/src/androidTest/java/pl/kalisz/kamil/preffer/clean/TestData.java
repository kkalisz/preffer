package pl.kalisz.kamil.preffer.clean;

import java.io.Serializable;
import java.util.Date;

public class TestData implements Serializable{

    private Date data;
    private String id;
    private Boolean isActive;

    public TestData(Date data, String id, Boolean isActive) {
        this.data = data;
        this.id = id;
        this.isActive = isActive;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public Boolean getActive() {
        return isActive;
    }
}
