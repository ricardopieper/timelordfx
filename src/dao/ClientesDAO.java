package dao;

import java.time.LocalDate;
import java.util.List;
import model.Cliente;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import static utils.DAO.RunQuery;

public class ClientesDAO extends BaseDAO<Cliente> {

    public List<Cliente> GetAtivos() {

        return (List<Cliente>) RunQuery(s -> {
            Criteria c = s.createCriteria(Cliente.class);

            c.add(Restrictions.eq("status", true));

            return c.list();

        });

    }

    public List<Cliente> GetFiltered(String nome, String cpf, LocalDate cadastradosApos, Boolean status) {

        return (List<Cliente>) RunQuery(s -> {
            Criteria c = s.createCriteria(Cliente.class);
            if (nome != null && !nome.isEmpty()) {
                c.add(Restrictions.like("nome", nome, MatchMode.ANYWHERE));
            }

            if (cpf != null && !cpf.isEmpty()) {
                c.add(Restrictions.eq("cpf", cpf));
            }
            if (cadastradosApos != null) {
                c.add(Restrictions.ge("dataCadastro", cadastradosApos));
            }
            c.add(Restrictions.eq("status", status));

            return c.list();

        });

    }

    public void Ativar(Cliente c) {
        c.setStatus(true);
        this.Update(c);
    }

    public void Desativar(Cliente c) {
        c.setStatus(false);
        this.Update(c);
    }

}
