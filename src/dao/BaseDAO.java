package dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Criteria;
import static utils.DAO.*;

public abstract class BaseDAO<T> implements IDatabaseOperations<T> {

    Class entityType;

    public BaseDAO() {

        this.entityType = ((Class) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public void Insert(T item) {
        SaveObject(item);
    }

    @Override
    public void Update(T item) {
        UpdateObject(item);
    }

    public void SaveOrUpdate(T item) {
        SaveOrUpdateObject(item);
    }

    @Override
    public void Delete(T item) {
        DeleteObject(item);
    }

    @Override
    public T GetById(int id) {
        return (T) RunQuery(s -> s.load(entityType, id));
    }

    @Override
    public List<T> GetAll() {
        return (List<T>) RunQuery(s -> {
            Criteria c = s.createCriteria(entityType);
            return c.list();
        });
    }

}
