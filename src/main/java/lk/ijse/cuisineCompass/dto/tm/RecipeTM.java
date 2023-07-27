package lk.ijse.cuisineCompass.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class RecipeTM {
    String recipeCode;
    String des;
    String cateCode;
    String method;
    String course;
    String addedBy;

    public RecipeTM(String des) {
        this.des = des;
    }
}
