
package bd.gov.activation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RenewCard {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("card_no")
    @Expose
    private String cardNo;
    @SerializedName("rfid_no")
    @Expose
    private String rfidNo;
    @SerializedName("expiry")
    @Expose
    private String expiry;
    @SerializedName("key_a")
    @Expose
    private String keyA;
    @SerializedName("key_b")
    @Expose
    private String keyB;

    @SerializedName("disabled")
    @Expose
    private Integer disabled;

    @SerializedName("secured")
    @Expose
    private Integer secured;

    @SerializedName("cloned")
    @Expose
    private Integer cloned;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public void setRfidNo(String rfidNo) {
        this.rfidNo = rfidNo;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public void setKeyA(String keyA) {
        this.keyA = keyA;
    }

    public void setKeyB(String keyB) {
        this.keyB = keyB;
    }

    public void setDisabled(Integer disabled) {
        this.disabled = disabled;
    }

    public void setSecured(Integer secured) {
        this.secured = secured;
    }

    public void setCloned(Integer cloned) {
        this.cloned = cloned;
    }

    public Integer getDisabled() {
        return disabled;
    }

    public Integer getSecured() {
        return secured;
    }

    public Integer getCloned() {
        return cloned;
    }

    public String getCardNo() {
        return cardNo;
    }

    public String getRfidNo() {
        return rfidNo;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getKeyA() {
        return keyA;
    }

    public String getKeyB() {
        return keyB;
    }

    public Integer getId() {
        return id;
    }
}
