package dao;

import java.time.LocalDate;
import java.util.List;
import model.Cliente;
import model.Setor;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import static utils.DAO.RunQuery;

public class SetoresDAO extends BaseDAO<Setor> {

    public List<Setor> GetFiltered(String nome, Boolean status) {

        return (List<Setor>) RunQuery(s -> {
            Criteria c = s.createCriteria(Setor.class);
            if (nome != null && !nome.isEmpty()) {
                c.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE));
            }
            c.add(Restrictions.eq("status", status));

            return c.list();

        });

    }
    
     public List<Setor> GetAtivos() {

        return (List<Setor>) RunQuery(s -> {
            Criteria c = s.createCriteria(Setor.class);
            
            c.add(Restrictions.eq("status", true));

            return c.list();

        });

    }

    public void Ativar(Setor c) {
        c.setStatus(true);
        this.Update(c);
    }

    public void Desativar(Setor c) {
        c.setStatus(false);
        this.Update(c);
    }
}
