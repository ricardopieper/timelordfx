package dao;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;
import model.Projeto;
import model.Tarefa;
import model.TarefaRecurso;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import static utils.DAO.RunQuery;
import utils.HibernateUtil;

public class TarefasDAO extends BaseDAO<Tarefa> {

    public List<Tarefa> GetAllFromProjeto(Projeto p) {

        return (List<Tarefa>) RunQuery(s
                -> s.createCriteria(Tarefa.class)
                .add(Restrictions.eq("projeto", p))
                .addOrder(Order.asc("dataInicio"))
                .list()
        );

    }
    /*
    public void SaveTree(TreeItem<Tarefa> root) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction t = s.beginTransaction();
        try {

            navigateTree(root, s, t);

            t.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

        } finally {
            s.flush();
            s.close();
        }

    }

    void navigateTree(TreeItem<Tarefa> root, Session s, Transaction t) {

        if (root.getValue().getRecursos().size() > 0) {
            List<TarefaRecurso> recursos = new ArrayList<>();

            root.getValue().getRecursos().forEach((it) -> {
                recursos.add(it);
            });
            root.getValue().getRecursos().clear();
            
            recursos.forEach(x -> s.saveOrUpdate(copy(x)));
        
        }

        if (root.getChildren().size() > 0) {
            root.getChildren().forEach(x -> navigateTree(x, s, t));
        }
        s.saveOrUpdate(root.getValue());
    }

    TarefaRecurso copy(TarefaRecurso item) {
        TarefaRecurso t = new TarefaRecurso();

        t.setCusto(item.getCusto());
        t.getPk().setRecurso(item.getPk().getRecurso());
        t.getPk().setTarefa(item.getTarefa());
        return t;
    }*/
}
