package ru.filchacov.billsplittest.db.ItemFromBill;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ru.filchacov.billsplittest.db.FriendsBillList.FriendsBillList;

@Entity(foreignKeys = @ForeignKey(entity = FriendsBillList.class, parentColumns = "item", childColumns = "id"))
public class ItemFromBill {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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

    private int price;
    private int quantity;
    private int sum;
}
