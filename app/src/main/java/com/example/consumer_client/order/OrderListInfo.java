package com.example.consumer_client.order;

public class OrderListInfo {
    private String storeId;
    private String storeProdImgView;
    private String storeName;
    private String mdName;
    private String storeLocationFromMe;
    private String mdComp;
    private String mdPrice;
    private String mdStatus;
    private String puDate;

    public String getStoreid() {
        return storeId;
    }

    public void setStoreid(String storeid) {
        this.storeId = storeid;
    }

    public String getStoreProdImgView() {
        return storeProdImgView;
    }

    public void setStoreProdImgView(String storeProdImgView) {
        this.storeProdImgView = storeProdImgView;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getMdName() {
        return mdName;
    }

    public void setMdName(String mdName) {
        this.mdName = mdName;
    }

    public String getStoreLocationFromMe() {
        return storeLocationFromMe;
    }

    public void setStoreLocationFromMe(String storeLocationFromMe) {this.storeLocationFromMe = storeLocationFromMe;}

    public String getMdComp() { return mdComp; }

    public void setMdComp(String mdComp) {
        this.mdComp = mdComp;
    }

    public String getMdStatus() {
        return mdStatus;
    }

    public void setMdStatus(String mdStatus) {
        this.mdStatus = mdStatus;
    }

    public String getMdPrice() {
        return mdPrice;
    }

    public void setMdPrice(String mdPrice) {
        this.mdPrice = mdPrice;
    }

    public String getPuDate() {
        return puDate;
    }

    public void setPuDate(String puDate) {
        this.puDate = puDate;
    }

}
