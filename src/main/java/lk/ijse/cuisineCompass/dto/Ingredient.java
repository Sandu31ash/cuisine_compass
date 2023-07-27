package lk.ijse.cuisineCompass.dto;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor

public class Ingredient {
    private String ingCode;
    private String des;
    private String unit;
    private double qty;
    private String date;

}
