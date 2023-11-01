package lab3.model;

public abstract class Model {
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public Integer setId(Integer id) {
        return this.id = id;
    }

    public String toString() {
        return String.format("%s(id=%d)", getClass().getSimpleName(), id);
    }
}
