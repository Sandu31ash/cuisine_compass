package lk.ijse.cuisineCompass.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class OrderTM {
    String code;
    String id;
    String date;
    String orderBy;
    String supplier;
    String iCode;
    double price;
    double qty;
    double tot;

    public OrderTM(String iCode, double price, double qty, double tot) {
        this.iCode = iCode;
        this.price = price;
        this.qty = qty;
        this.tot = tot;
    }

    public OrderTM(String code, String id, String date, String orderBy) {
        this.code = code;
        this.id = id;
        this.date = date;
        this.orderBy = orderBy;
    }

    public OrderTM(String date, String orderBy, String supplier) {
        this.date = date;
        this.orderBy = orderBy;
        this.supplier = supplier;
    }

    public OrderTM(String code, String iCode, double price, double qty, double tot) {
        this.code = code;
        this.iCode = iCode;
        this.price = price;
        this.qty = qty;
        this.tot = tot;
    }
}
