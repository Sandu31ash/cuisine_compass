package lk.ijse.cuisineCompass.dto;

import lombok.*;

@Data
@AllArgsConstructor

public class User {
    private String id;
    private String jobRole;
    private String userName;
    private String password;
}
