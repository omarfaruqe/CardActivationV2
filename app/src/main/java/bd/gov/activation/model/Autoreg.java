
package bd.gov.activation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Autoreg {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("application_id")
    @Expose
    private String applicationId;
    @SerializedName("existing")
    @Expose
    private String existing;
    @SerializedName("reg_no")
    @Expose
    private String regNo;
    @SerializedName("reg_date")
    @Expose
    private String regDate;
    @SerializedName("reg_expires")
    @Expose
    private String regExpires;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("receipt_no")
    @Expose
    private String receiptNo;
    @SerializedName("buyer_name")
    @Expose
    private String buyerName;
    @SerializedName("emergency_contact_name")
    @Expose
    private String emergencyContactName;
    @SerializedName("emergency_contact_relation")
    @Expose
    private String emergencyContactRelation;
    @SerializedName("emergency_contact_address")
    @Expose
    private String emergencyContactAddress;
    @SerializedName("emergency_contact_mobile")
    @Expose
    private String emergencyContactMobile;
    @SerializedName("papers")
    @Expose
    private String papers;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getExisting() {
        return existing;
    }

    public void setExisting(String existing) {
        this.existing = existing;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegExpires() {
        return regExpires;
    }

    public void setRegExpires(String regExpires) {
        this.regExpires = regExpires;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactRelation() {
        return emergencyContactRelation;
    }

    public void setEmergencyContactRelation(String emergencyContactRelation) {
        this.emergencyContactRelation = emergencyContactRelation;
    }

    public String getEmergencyContactAddress() {
        return emergencyContactAddress;
    }

    public void setEmergencyContactAddress(String emergencyContactAddress) {
        this.emergencyContactAddress = emergencyContactAddress;
    }

    public String getEmergencyContactMobile() {
        return emergencyContactMobile;
    }

    public void setEmergencyContactMobile(String emergencyContactMobile) {
        this.emergencyContactMobile = emergencyContactMobile;
    }

    public String getPapers() {
        return papers;
    }

    public void setPapers(String papers) {
        this.papers = papers;
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

}
