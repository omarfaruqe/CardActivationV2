
package bd.gov.activation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelContainerRenew {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("secured")
    @Expose
    private Integer secured;

    @SerializedName("card")
    @Expose
    private RenewCard card;

    public Integer getSecured() {
        return secured;
    }

    public void setSecured(Integer secured) {
        this.secured = secured;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RenewCard getRenewCard() {
        return card;
    }

    public void setRenewCard(RenewCard card) {
        this.card = card;
    }
}
