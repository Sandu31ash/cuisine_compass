package lk.ijse.cuisineCompass.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Menu {
    private String mCode;
    private String dish;
    private String des;
    private String meal;
    private double price;
    private String cCode;
    private String userName;


    public Menu(String dish, double price) {
        this.dish = dish;
        this.price = price;
    }
}
