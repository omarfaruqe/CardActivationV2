
package bd.gov.activation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Application {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("card_type_id")
    @Expose
    private String applicationTypeId;

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    @SerializedName("case")
    @Expose
    private String caseType;

    @SerializedName("registration_id")
    @Expose
    private String registrationId;

    @SerializedName("tracking_id")
    @Expose
    private String trackingId;

    @SerializedName("fee_type")
    @Expose
    private String feeType;

    @SerializedName("approvals")
    @Expose
    private String approvals;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("registration")
    @Expose
    private Registration registration;

    @SerializedName("autoreg")
    @Expose
    private Autoreg autoreg;
    @SerializedName("delivery")
    @Expose
    private Delivery delivery;
    @SerializedName("card")
    @Expose
    private Card card;
    @SerializedName("payment")
    @Expose
    private Payment payment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationTypeId() {
        return applicationTypeId;
    }

    public void setApplicationTypeId(String applicationTypeId) {
        this.applicationTypeId = applicationTypeId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getApprovals() {
        return approvals;
    }

    public void setApprovals(String approvals) {
        this.approvals = approvals;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Autoreg getAutoreg() {
        return autoreg;
    }

    public void setAutoreg(Autoreg autoreg) {
        this.autoreg = autoreg;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

}
