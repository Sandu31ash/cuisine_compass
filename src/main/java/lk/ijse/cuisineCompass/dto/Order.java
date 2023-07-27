package lk.ijse.cuisineCompass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class Order {
    private String code;
    private String id;
    private String date;
    private String orderBy;
    private String supplier;
    private String iCode;
    private double price;
    private double qty;
    private double tot;

    public Order(String iCode, double price, double qty, double tot) {
        this.iCode = iCode;
        this.price = price;
        this.qty = qty;
        this.tot = tot;
    }

    public Order(String code, String id, String date, String orderBy) {
        this.code = code;
        this.id = id;
        this.date = date;
        this.orderBy = orderBy;
    }

    public Order(String date, String orderBy, String supplier) {
        this.date = date;
        this.orderBy = orderBy;
        this.supplier = supplier;
    }

    public Order(String code, String iCode, double price, double qty, double tot) {
        this.code = code;
        this.iCode = iCode;
        this.price = price;
        this.qty = qty;
        this.tot =tot;
    }
}
