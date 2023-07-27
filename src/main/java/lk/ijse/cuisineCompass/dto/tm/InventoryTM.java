package lk.ijse.cuisineCompass.dto.tm;

import lombok.*;

@Data
@AllArgsConstructor

public class InventoryTM {
    String iCode;
    String des;
    String unit;
    double par;
    double qty;
    String date;

    public InventoryTM(String iCode, String des) {
        this.iCode = iCode;
        this.des = des;
    }
}
