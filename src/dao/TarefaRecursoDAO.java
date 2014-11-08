
package dao;

import java.util.List;
import model.Tarefa;
import model.TarefaRecurso;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import static utils.DAO.RunQuery;


public class TarefaRecursoDAO extends BaseDAO<TarefaRecurso> {

    public boolean Exists(int rec, int tar){
        
        return (boolean) RunQuery(s->
        
                s.createCriteria(TarefaRecurso.class)
                    .add(Restrictions.eq("pk.recurso", rec))
                    .add(Restrictions.eq("pk.tarefa", tar))
                    .list().size()>0
        
        );
        
    }
    
}
