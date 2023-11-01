package lab3.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lab3.model.Model;

public abstract class Repository<T extends Model> {
    protected final List<T> objects;

    public Repository() {
        objects = new ArrayList<T>();
    }

    public T create(T object) {
        object.setId(objects.size());
        objects.add(object);
        return object;
    }

    public Integer count() {
        return objects.size();
    }

    public Optional<T> findById(Integer id) {
        return objects.stream().filter(object -> object.getId().equals(id)).findFirst();
    }

    public List<T> findAll() {
        return objects;
    }

    public void deleteById(Integer id) {
        objects.removeIf(object -> object.getId().equals(id));
    };

    public Integer deleteAll() {
        int size = objects.size();
        objects.clear();
        return size;
    }

    public abstract Optional<T> update(T object);
}
