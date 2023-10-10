package com.example.cemetery_payment_system;

public class DataIrimbi {
    private String IrimbiID;
    private String IrimbiTitle;
    private String IrimbiCategory;

    public DataIrimbi(String irimbiID, String irimbiTitle, String IrimbiCategory) {
        this.IrimbiID = irimbiID;
        this.IrimbiTitle = irimbiTitle;
        this.IrimbiCategory = IrimbiCategory;
    }

    public String getIrimbiID() {
        return IrimbiID;
    }

    public String getIrimbiTitle() {
        return IrimbiTitle;
    }

    public String getIrimbiCategory() {
        return IrimbiCategory;
    }
}
