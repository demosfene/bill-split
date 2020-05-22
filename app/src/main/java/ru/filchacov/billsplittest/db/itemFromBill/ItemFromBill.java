package ru.filchacov.billsplittest.db.itemFromBill;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.db.friendsBillList.FriendsBillList;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = FriendsBillList.class, parentColumns = "item", childColumns = "id", onDelete = CASCADE))
public class ItemFromBill {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;

    public ItemFromBill(@NonNull String id, String name, int price, int quantity, int sum) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sum = sum;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
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
