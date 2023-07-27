package lk.ijse.cuisineCompass.dto;

import lombok.*;


@Data
@AllArgsConstructor

public class Inventory {
    private String iCode;
    private String des;
    private String unit;
    private double par;
    private double qty;
    private String date;
}
