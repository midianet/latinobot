package midianet.latinoware.model;

import java.util.Date;

/**
 * Created by marcos-fc on 09/06/16.
 */
public class Pagamento {
    private Long id;
    private Pessoa pessoa;
    private Date data;
    private double valor;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(final Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Date getData() {
        return data;
    }

    public void setData(final Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(final double valor) {
        this.valor = valor;
    }

}