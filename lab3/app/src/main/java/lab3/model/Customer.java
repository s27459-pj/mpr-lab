package lab3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Customer extends Model {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
