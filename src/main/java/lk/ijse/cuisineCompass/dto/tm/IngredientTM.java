package lk.ijse.cuisineCompass.dto.tm;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Data

public class IngredientTM {
    String ingCode;
    String des;
    String unit;
    double qty;
    String date;

    public IngredientTM(String ingCode, String des) {
        this.ingCode = ingCode;
        this.des = des;
    }
}
