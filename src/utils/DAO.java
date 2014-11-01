
package utils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import org.hibernate.Session;
import org.hibernate.Transaction;

public abstract class DAO {
     
    //java8 goods   
    
    public static void RunSession(Consumer<Session> func) {
        
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        func.accept(s);
        
        if (s.isOpen())  s.close();
    }
    
      
     public static void RunTransaction(Consumer<Session> func){
         
         RunSession(s-> {
            
             Transaction t = s.beginTransaction();
             func.accept(s);
             t.commit();
         });
         
     }
      
      
    public static <R> R RunQuery(Function<Session, R> func) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        
        R ret = func.apply(s);
        
        if (s.isOpen()) s.close();
        
        return ret;
    }
    
    public static void SaveObject(Object o){
        RunTransaction(s-> s.save(o));
    }

    public static void UpdateObject(Object o){
        RunTransaction(s -> s.merge(o));
    }
    
    public static List QueryList(String q){
        return RunQuery(s-> s.createQuery(q).list());
    }
    
    public static void DeleteObject(Object o){
        RunTransaction(s -> s.delete(o));
    }
    
    
}
