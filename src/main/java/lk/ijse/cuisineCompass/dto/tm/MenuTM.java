package lk.ijse.cuisineCompass.dto.tm;

import lombok.*;

@Data
@AllArgsConstructor

public class MenuTM {
    String mCode;
    String dish;
    String des;
    String meal;
    double price;
    String cCode;
    String userName;

    public MenuTM(String dish, double price) {
        this.dish = dish;
        this.price = price;
    }
}
