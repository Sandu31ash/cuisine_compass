package lk.ijse.cuisineCompass.dto;

import lombok.*;

import java.io.InputStream;

@Data
@AllArgsConstructor

public class RecipeIngredientDetails {
    private String recipeCode;
    private String ingCode;
    private String unit;
    private Double qty;

    public RecipeIngredientDetails(String ingCode, String unit, double qty) {
        this.ingCode = ingCode;
        this.unit = unit;
        this.qty = qty;
    }

}
