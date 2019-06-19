package shopthing.model;

import org.checkerframework.common.aliasing.qual.Unique;
import org.hibernate.annotations.Check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Check(constraints = "PRICE > 0 AND ONSTORAGE >=0")
public class Ware {

    @Id
    private int id;

    @Column(unique = true)
    private Integer barcode;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column()
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

    @Override
    public String toString() {
        return "id=" + id +
                ", barcode=" + barcode +
                ", name=" + name + '\'' +
                ", price=" + price +
                ", onStorage=" + onStorage +
                '}';
    }
}
