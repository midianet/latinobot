package midianet.latinoware.model;

import java.util.List;

/**
 * Created by marcosfernandocosta on 22/08/16.
 */
public class Quarto {
    private static final int TIPO_DUPLO = 2;
    private static final int TIPO_TRIPLO = 3;
    private static final int TIPO_QUADRUPLO = 4;
    private static final int SEXO_MASCULINO = 1;
    private static final int SEXO_FEMININO = 2;
    private static final int SEXO_MISTO = 3;
    private Long id;
    private int tipo;
    private int sexo;
    private List<Pessoa> ocupantes;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(final int tipo) {
        this.tipo = tipo;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(final int sexo) {
        this.sexo = sexo;
    }

    public void setOcupantes(final List<Pessoa> lista) {
        this.ocupantes = lista;
    }

    public List<Pessoa> getOcupantes() {
        return ocupantes;
    }

    public String getTipoText(){
        if(tipo == TIPO_DUPLO){
            return "Duplo/Casal";
        }else if(tipo == TIPO_TRIPLO){
            return "Triplo";
        }else if (tipo == TIPO_QUADRUPLO){
            return "Quádruplo";
        }else{
            return "Indefinido";
        }
    }

    public boolean isLotado(){
        boolean retorno = false;
        if(ocupantes != null){
            retorno = ocupantes.size() >= tipo;
        }
        return retorno;
    }

}
