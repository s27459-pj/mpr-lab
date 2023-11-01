package lab3.model;

public class Rental extends Model {
    private Integer carId;
    private Integer customerId;
    private Integer days;
    private Integer price;

    public Rental(Integer carId, Integer customerId, Integer days, Integer price) {
        this.carId = carId;
        this.customerId = customerId;
        this.days = days;
        this.price = price;
    }

    public Integer getCarId() {
        return carId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public Integer getDays() {
        return days;
    }

    public Integer getPrice() {
        return price;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format(
                "Rental(id=%d, carId=%d, customerId=%d, days=%d, price=%d)",
                id, carId, customerId, days, price);
    }
}
