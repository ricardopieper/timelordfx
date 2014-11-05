
package dao;
import java.util.List;
import model.Projeto;
import model.Tarefa;
import org.hibernate.criterion.Restrictions;
import static utils.DAO.RunQuery;

public class TarefasDAO extends BaseDAO<Tarefa> {

    public List<Tarefa> GetAllFromProjeto(Projeto p){
        
        return (List<Tarefa>) RunQuery(s->
        
                s.createCriteria(Tarefa.class)
                    .add(Restrictions.eq("projeto", p))
                    .list()
        
        );
        
    }
 
}
