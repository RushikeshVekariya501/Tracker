package com.tracker.ModelClasses;

import java.io.Serializable;
import java.util.List;

public class TransactionModel {


    private int code;
    private String message;
    private String description;
    private List<DataBean> data;

    public TransactionModel() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {

        private Long lngTransactionID;
        private Long lngTransactionGroupID;
        private String strTransactionName;
        private int intTrascationTypeID;
        private String strTransactionTypeName;
        private Long lngDateAndTime;
        private String strPartyName;
        private String strTitle;
        private Float intAmount;
        private String strBankName;
        private String strMonthYear;
        private String strImageURL;
        private String strGroupName;
        private String strCategoryName;
        private String strDay;
        private String strMonth;
        private String strYear;

        public Long getLngTransactionID() {
            return lngTransactionID;
        }

        public void setLngTransactionID(Long lngTransactionID) {
            this.lngTransactionID = lngTransactionID;
        }

        public String getStrTransactionName() {
            return strTransactionName;
        }

        public void setStrTransactionName(String strTransactionName) {
            this.strTransactionName = strTransactionName;
        }

        public int getIntTrascationTypeID() {
            return intTrascationTypeID;
        }

        public void setIntTrascationTypeID(int intTrascationTypeID) {
            this.intTrascationTypeID = intTrascationTypeID;
        }

        public String getStrTransactionTypeName() {
            return strTransactionTypeName;
        }

        public void setStrTransactionTypeName(String strTransactionTypeName) {
            this.strTransactionTypeName = strTransactionTypeName;
        }

        public Long getLngDateAndTime() {
            return lngDateAndTime;
        }

        public void setLngDateAndTime(Long lngDateAndTime) {
            this.lngDateAndTime = lngDateAndTime;
        }

        public String getStrPartyName() {
            return strPartyName;
        }

        public void setStrPartyName(String strPartyName) {
            this.strPartyName = strPartyName;
        }

        public String getStrTitle() {
            return strTitle;
        }

        public void setStrTitle(String strTitle) {
            this.strTitle = strTitle;
        }

        public Float getIntAmount() {
            return intAmount;
        }

        public void setIntAmount(Float intAmount) {
            this.intAmount = intAmount;
        }

        public String getStrBankName() {
            return strBankName;
        }

        public void setStrBankName(String strBankName) {
            this.strBankName = strBankName;
        }

        public String getStrMonthYear() {
            return strMonthYear;
        }

        public void setStrMonthYear(String strMonthYear) {
            this.strMonthYear = strMonthYear;
        }

        public String getStrImageURL() {
            return strImageURL;
        }

        public void setStrImageURL(String strImageURL) {
            this.strImageURL = strImageURL;
        }

        public String getStrGroupName() {
            return strGroupName;
        }

        public void setStrGroupName(String strGroupName) {
            this.strGroupName = strGroupName;
        }

        public String getStrCategoryName() {
            return strCategoryName;
        }

        public void setStrCategoryName(String strCategoryName) {
            this.strCategoryName = strCategoryName;
        }

        public String getStrDay() {
            return strDay;
        }

        public void setStrDay(String strDay) {
            this.strDay = strDay;
        }

        public String getStrMonth() {
            return strMonth;
        }

        public void setStrMonth(String strMonth) {
            this.strMonth = strMonth;
        }

        public String getStrYear() {
            return strYear;
        }

        public void setStrYear(String strYear) {
            this.strYear = strYear;
        }

        public Long getLngTransactionGroupID() {
            return lngTransactionGroupID;
        }

        public void setLngTransactionGroupID(Long lngTransactionGroupID) {
            this.lngTransactionGroupID = lngTransactionGroupID;
        }
    }
}
