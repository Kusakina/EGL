package egl.client.repository.core;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import egl.client.model.core.DatabaseEntity;

public abstract class EntityManagerRepository<T extends DatabaseEntity> implements EntityRepository<T> {

    protected abstract EntityManager getEntityManager();

    protected abstract Class<T> getEntityClass();

    protected String getTableName() {
        return getEntityClass().getSimpleName();
    }

    @Override
    public <S extends T> S save(S value) {
        return getEntityManager().merge(value);
    }

    @Override
    public void delete(T value) {
        getEntityManager().remove(value);
    }

    @Override
    public Optional<T> findById(Long id) {
        var result = getEntityManager().find(
                getEntityClass(), id
        );

        return Optional.ofNullable(result);
    }

    protected <Field> Optional<T> findByField(String fieldName, Field field) {
        return findByFields(Map.of(fieldName, field));
    }

    protected Optional<T> findByFields(Map<String, ?> fields) {
        var condition = fields.keySet().stream()
                .map(fieldName -> String.format("e.%s = :%s", fieldName, fieldName))
                .collect(Collectors.joining(" and "));

        var query = getEntityManager().createQuery(
                String.format("select e from %s e where %s", getTableName(), condition),
                getEntityClass()
        );

        fields.forEach(query::setParameter);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<T> findAll() {
        throw new UnsupportedOperationException();
    }
}