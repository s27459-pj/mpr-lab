package lab3.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lab3.model.Model;

class DummyModel extends Model {
    private int dummyField;

    public DummyModel(int dummyField) {
        this.dummyField = dummyField;
    }

    public int getDummyField() {
        return dummyField;
    }

    public void setDummyField(int dummyField) {
        this.dummyField = dummyField;
    }
}

class DummyRepository extends Repository<DummyModel> {
    @Override
    public Optional<DummyModel> update(DummyModel object) {
        var modelToUpdate = findById(object.getId());
        if (modelToUpdate.isEmpty())
            return Optional.empty();
        return modelToUpdate.map(m -> {
            m.setDummyField(object.getDummyField());
            return m;
        });
    }
}

class RepositoryTest {
    private DummyRepository repository;

    @BeforeEach
    void setup() {
        repository = new DummyRepository();
    }

    @Test
    void repositoryInitializes() {
        assertNotNull(repository);
    }

    @Test
    void create() {
        var obj = new DummyModel(123);
        var createdModel = repository.create(obj);
        assertNotNull(createdModel);
        assertNotNull(createdModel.getId());
        assertEquals(obj, createdModel);
    }

    @Test
    void count() {
        assertEquals(0, repository.count());
        repository.create(new DummyModel(123));
        assertEquals(1, repository.count());
    }

    @Test
    void findById() {
        var obj = new DummyModel(123);
        var createdModel = repository.create(obj);
        var foundModel = repository.findById(createdModel.getId());
        assertTrue(foundModel.isPresent());
        assertEquals(createdModel.getId(), foundModel.get().getId());
    }

    @Test
    void findAll() {
        repository.create(new DummyModel(123));
        repository.create(new DummyModel(321));
        var objs = repository.findAll();
        assertEquals(2, objs.size());
    }

    @Test
    void deleteById() {
        var obj = repository.create(new DummyModel(123));
        assertEquals(1, repository.count());
        repository.deleteById(obj.getId());
        assertEquals(0, repository.count());
        assertFalse(repository.findById(obj.getId()).isPresent());
    }

    @Test
    void deleteAll() {
        repository.create(new DummyModel(123));
        repository.create(new DummyModel(321));
        assertEquals(2, repository.count());
        repository.deleteAll();
        assertEquals(0, repository.count());
    }

    @Test
    void update() {
        var obj = repository.create(new DummyModel(123));
        var updatedModel = new DummyModel(321);
        updatedModel.setId(obj.getId());
        var result = repository.update(updatedModel);
        assertTrue(result.isPresent());
        assertEquals(updatedModel.getDummyField(), result.get().getDummyField());
    }
}
