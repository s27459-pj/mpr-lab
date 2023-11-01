package lab3.model;

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

    public Car(String make, String model, Integer vin, Class carClass, Status carStatus) {
        this.make = make;
        this.model = model;
        this.vin = vin;
        this.carClass = carClass;
        this.carStatus = carStatus;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public Integer getVin() {
        return vin;
    }

    public Class getCarClass() {
        return carClass;
    }

    public Status getCarStatus() {
        return carStatus;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVin(Integer vin) {
        this.vin = vin;
    }

    public void setCarClass(Class carClass) {
        this.carClass = carClass;
    }

    public void setCarStatus(Status carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return String.format(
                "Car(id=%d, make=%s, model=%s, vin=%d, carClass=%s, carStatus=%s)",
                id, make, model, vin, carClass, carStatus);
    }
}
