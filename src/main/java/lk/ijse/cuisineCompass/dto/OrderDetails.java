package lk.ijse.cuisineCompass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class OrderDetails {
    private String code;
    private String iCode;
    private double price;
    private double qty;
    private double tot;
}
