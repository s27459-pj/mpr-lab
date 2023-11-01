package lab3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Car extends Model {
    public enum Class {
        Standard,
        Premium,
        Luxury,
    }

    public enum Status {
        Available,
        Rented,
        Repair,
    }

    private String make;
    private String model;
    private Integer vin;
    private Class carClass;
    private Status carStatus;
}
