package lk.ijse.cuisineCompass.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data

public class Supplier {
    private String id;
    private String name;
    private String email;
    private String contact;

    public Supplier(String id) {
        this.id = id;
    }
}
