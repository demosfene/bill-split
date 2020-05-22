package ru.filchacov.billsplittest.db.bill;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ru.filchacov.billsplittest.bill.Bill;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = Bill.class, parentColumns = "dateTime", childColumns = "billTime", onDelete = CASCADE))
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String billTime;
    private String name;
    private int quantity;
    private int sum;
    private int price;

    public Item() {
    }

    public Item(String billTime, String name, int quantity, int sum, int price) {
        this.billTime = billTime;
        this.name = name;
        this.quantity = quantity;
        this.sum = sum;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillTime() {
        return billTime;
    }

    public void setBillTime(String billTime) {
        this.billTime = billTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
