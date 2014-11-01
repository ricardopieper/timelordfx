
package dao;
import java.util.List;


public interface IDatabaseOperations<T> {
    
   
    
    public void Insert(T item);
    
    public void Update(T item);
   
    public void Delete(T item);
    
    public T GetById(int id);

    public List<T> GetAll();
}
