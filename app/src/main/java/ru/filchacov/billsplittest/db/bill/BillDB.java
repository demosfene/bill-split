package ru.filchacov.billsplittest.db.bill;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ru.filchacov.billsplittest.bill.Bill;

@Entity
public class BillDB extends Bill {
    private int receiptCode;
    private String user; // Дата совершения покупки
    @Ignore
    private List<Item> items; // Лист списка покупок (вся инфа по покупкам тут)
    private int cashTotalSum;
    private int shiftNumber;
    private int totalSum;
    private int ecashTotalSum;
    private int taxationType;
    private int ndsNo;
    @PrimaryKey
    @NonNull
    private String dateTime;
    private String fiscalDriveNumber;
    private int fiscalDocumentNumber;
    private int fiscalSign;
    private String userInn;
    private int operationType;
    private String kktRegId;
    private String rawData;
    private int requestNumber;
    private String operator;

    public BillDB() {
    }

    public BillDB(int receiptCode, String user, ArrayList<Item> items, int cashTotalSum, int shiftNumber, int totalSum, int ecashTotalSum, int taxationType, int ndsNo, @NotNull String dateTime, String fiscalDriveNumber, int fiscalDocumentNumber, int fiscalSign, String userInn, int operationType, String kktRegId, String rawData, int requestNumber, String operator) {
        this.receiptCode = receiptCode;
        this.user = user;
        this.items = items;
        this.cashTotalSum = cashTotalSum;
        this.shiftNumber = shiftNumber;
        this.totalSum = totalSum;
        this.ecashTotalSum = ecashTotalSum;
        this.taxationType = taxationType;
        this.ndsNo = ndsNo;
        this.dateTime = dateTime;
        this.fiscalDriveNumber = fiscalDriveNumber;
        this.fiscalDocumentNumber = fiscalDocumentNumber;
        this.fiscalSign = fiscalSign;
        this.userInn = userInn;
        this.operationType = operationType;
        this.kktRegId = kktRegId;
        this.rawData = rawData;
        this.requestNumber = requestNumber;
        this.operator = operator;
    }


//    public int getReceiptCode() {
//        return receiptCode;
//    }
//
//    public void setReceiptCode(int receiptCode) {
//        this.receiptCode = receiptCode;
//    }
//
//    public String getUser() {
//        return user;
//    }
//
//    public void setUser(String user) {
//        this.user = user;
//    }
//
//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }
//
//    public int getCashTotalSum() {
//        return cashTotalSum;
//    }
//
//    public void setCashTotalSum(int cashTotalSum) {
//        this.cashTotalSum = cashTotalSum;
//    }
//
//    public int getShiftNumber() {
//        return shiftNumber;
//    }
//
//    public void setShiftNumber(int shiftNumber) {
//        this.shiftNumber = shiftNumber;
//    }
//
//    public int getTotalSum() {
//        return totalSum;
//    }
//
//    public void setTotalSum(int totalSum) {
//        this.totalSum = totalSum;
//    }
//
//    public int getEcashTotalSum() {
//        return ecashTotalSum;
//    }
//
//    public void setEcashTotalSum(int ecashTotalSum) {
//        this.ecashTotalSum = ecashTotalSum;
//    }
//
//    public int getTaxationType() {
//        return taxationType;
//    }
//
//    public void setTaxationType(int taxationType) {
//        this.taxationType = taxationType;
//    }
//
//    public int getNdsNo() {
//        return ndsNo;
//    }
//
//    public void setNdsNo(int ndsNo) {
//        this.ndsNo = ndsNo;
//    }
//
//    public String getDateTime() {
//        return dateTime;
//    }
//
//    public void setDateTime(String dateTime) {
//        this.dateTime = dateTime;
//    }
//
//    public String getFiscalDriveNumber() {
//        return fiscalDriveNumber;
//    }
//
//    public void setFiscalDriveNumber(String fiscalDriveNumber) {
//        this.fiscalDriveNumber = fiscalDriveNumber;
//    }
//
//    public int getFiscalDocumentNumber() {
//        return fiscalDocumentNumber;
//    }
//
//    public void setFiscalDocumentNumber(int fiscalDocumentNumber) {
//        this.fiscalDocumentNumber = fiscalDocumentNumber;
//    }
//
//    public int getFiscalSign() {
//        return fiscalSign;
//    }
//
//    public void setFiscalSign(int fiscalSign) {
//        this.fiscalSign = fiscalSign;
//    }
//
//    public String getUserInn() {
//        return userInn;
//    }
//
//    public void setUserInn(String userInn) {
//        this.userInn = userInn;
//    }
//
//    public int getOperationType() {
//        return operationType;
//    }
//
//    public void setOperationType(int operationType) {
//        this.operationType = operationType;
//    }
//
//    public String getKktRegId() {
//        return kktRegId;
//    }
//
//    public void setKktRegId(String kktRegId) {
//        this.kktRegId = kktRegId;
//    }
//
//    public String getRawData() {
//        return rawData;
//    }
//
//    public void setRawData(String rawData) {
//        this.rawData = rawData;
//    }
//
//    public int getRequestNumber() {
//        return requestNumber;
//    }
//
//    public void setRequestNumber(int requestNumber) {
//        this.requestNumber = requestNumber;
//    }
//
//    public String getOperator() {
//        return operator;
//    }
//
//    public void setOperator(String operator) {
//        this.operator = operator;
//    }
}
