package lk.ijse.cuisineCompass.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class OrderDetailsTM {
    String code;
    String iCode;
    double price;
    double qty;
    double tot;
}
