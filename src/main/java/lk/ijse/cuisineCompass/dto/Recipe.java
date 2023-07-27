package lk.ijse.cuisineCompass.dto;

import lombok.*;

import java.io.InputStream;

@Data
@AllArgsConstructor

public class Recipe {
    private String recipeCode;
    private String des;
    private String cateCode;
    private String method;
    private String course;
    private String addedBy;

    public Recipe(String description) {
        this.des = description;
    }

}
