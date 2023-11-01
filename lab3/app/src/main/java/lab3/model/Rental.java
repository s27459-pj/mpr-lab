package lab3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Rental extends Model {
    private Integer carId;
    private Integer customerId;
    private Integer days;
    private Integer price;
}
