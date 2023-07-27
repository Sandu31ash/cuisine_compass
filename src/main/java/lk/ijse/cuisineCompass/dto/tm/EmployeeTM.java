package lk.ijse.cuisineCompass.dto.tm;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class EmployeeTM {
    String id;
    String name;
    String jobRole;
    String email;
    String contact;
}
