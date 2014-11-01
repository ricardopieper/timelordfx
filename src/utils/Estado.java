package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Estado {

    public String sigla;
    public String nome;

    public Estado(String _sigla, String _nome) {
        this.sigla = _sigla;
        this.nome = _nome;
    }

    @Override
    public String toString() {
        return nome + " - " + sigla;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Estado) {
            Estado e = (Estado) obj;

            return e.sigla.equals(this.sigla);
        }
        if (obj instanceof String){
            return obj.equals(this.sigla);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.sigla);
        hash = 89 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    private static List<Estado> _statesCache;

    public static List<Estado> getAllBrazillianStates() {

        if (_statesCache == null) {

            List<Estado> ls = new ArrayList<>();

            ls.add(new Estado("AC", "Acre"));
            ls.add(new Estado("AL", "Alagoas"));
            ls.add(new Estado("AP", "Amapá"));
            ls.add(new Estado("AM", "Amazonas"));
            ls.add(new Estado("BA", "Bahia"));
            ls.add(new Estado("CE", "Ceará"));
            ls.add(new Estado("DF", "Distrito Federal"));
            ls.add(new Estado("ES", "Espírito Santo"));
            ls.add(new Estado("GO", "Goiás"));
            ls.add(new Estado("MA", "Maranhão"));
            ls.add(new Estado("MT", "Mato Grosso"));
            ls.add(new Estado("MS", "Mato Grosso do Sul"));
            ls.add(new Estado("MG", "Minas Gerais"));
            ls.add(new Estado("PA", "Pará"));
            ls.add(new Estado("PB", "Paraíba"));
            ls.add(new Estado("PR", "Paraná"));
            ls.add(new Estado("PE", "Pernambuco"));
            ls.add(new Estado("PI", "Piauí"));
            ls.add(new Estado("RJ", "Rio de Janeiro"));
            ls.add(new Estado("RN", "Rio Grande do Norte"));
            ls.add(new Estado("RS", "Rio Grande do Sul"));
            ls.add(new Estado("RO", "Rondônia"));
            ls.add(new Estado("RR", "Roraima"));
            ls.add(new Estado("SC", "Santa Catarina"));
            ls.add(new Estado("SP", "São Paulo"));
            ls.add(new Estado("SE", "Sergipe"));
            ls.add(new Estado("TO", "Tocantins"));
            _statesCache = Collections.unmodifiableList(ls);
        }
        return _statesCache;

    }
}
