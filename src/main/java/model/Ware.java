package model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ware {

    @Id
    private int id;
    private Integer barcode;
    private String name;
    private int price;
    private int onStorage;

    public Ware(int id, Integer barcode, String name, int price, int onStorage) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.onStorage = onStorage;
    }

    public Ware() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getBarcode() {
        return barcode;
    }

    public void setBarcode(Integer barcode) {
        this.barcode = barcode;
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

    public int getOnStorage() {
        return onStorage;
    }

    public void setOnStorage(int onStorage) {
        this.onStorage = onStorage;
    }
}
