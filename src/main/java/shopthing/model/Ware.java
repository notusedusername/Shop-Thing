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
    @Column(unique = true)
    private Integer barcode;

    @Column(unique = true)
    @NotNull
    private String name;

    @Column()
    private Integer price;

    private Integer onStorage;

    public Ware(Integer barcode, String name, Integer price, Integer onStorage) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.onStorage = onStorage;
    }

    public Ware() {
    }

    public Ware(Ware original) {
        this.barcode = original.barcode;
        this.name = original.name;
        this.price = original.price;
        this.onStorage = original.onStorage;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getOnStorage() {
        return onStorage;
    }

    public void setOnStorage(Integer onStorage) {
        this.onStorage = onStorage;
    }

    @Override
    public String toString() {
        return ", barcode=" + barcode +
                ", name=" + name + '\'' +
                ", price=" + price +
                ", onStorage=" + onStorage +
                '}';
    }
}
