package midianet.latinoware.model;

import java.util.Date;

public class Pessoa {
    private Long   id;
    private Long   idTelegram;
    private String nome;
    private Date   cadastro;
    private Long   inscricao;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getIdTelegram() {
        return idTelegram;
    }

    public void setIdTelegram(final Long id) {
        this.idTelegram = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public Date getCadastro() {
        return cadastro;
    }

    public void setCadastro(final Date data) {
        this.cadastro = data;
    }

}