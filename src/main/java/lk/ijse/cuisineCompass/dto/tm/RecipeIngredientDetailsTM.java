package lk.ijse.cuisineCompass.dto.tm;

import lombok.*;

@Data
@AllArgsConstructor

public class RecipeIngredientDetailsTM {
    String recipeCode;
    String ingCode;
    String unit;
    Double qty;

    public RecipeIngredientDetailsTM(String ingCode, String unit, Double qty) {
        this.ingCode = ingCode;
        this.unit = unit;
        this.qty = qty;
    }
}
