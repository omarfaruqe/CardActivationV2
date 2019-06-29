
package bd.gov.activation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelContainer {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("application")
    @Expose
    private Application application;

//    @SerializedName("registration")
//    @Expose
//    private Registration registration;

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

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

//    public Registration getRegistration() {
//        return registration;
//    }
//
//    public void setRegistration(Registration registration) {
//        this.registration = registration;
//    }
}
