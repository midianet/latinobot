package midianet.latinoware.model;

import java.util.Date;

public class Pessoa {
    private Long   id;
    private Long   idTelegram;
    private String nome;
    private Date   cadastro;
    private boolean pagou;
    private boolean cerveja;
    private boolean refrigerante;
    private boolean ice;
    private boolean suco;
    private boolean energetico;
    private boolean toddynho;
    private boolean aguaCoco;
    private Long    idQuarto;

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

    public void setPagou(final boolean flag) {
        this.pagou = flag;
    }

    public boolean isPagou() {
        return pagou;
    }

    public boolean isCerveja() {
        return cerveja;
    }

    public void setCerveja(final boolean flag) {
        this.cerveja = flag;
    }

    public boolean isRefrigerante() {
        return refrigerante;
    }

    public void setRefrigerante(final boolean flag) {
        this.refrigerante = flag;
    }

    public boolean isEnergetico() {
        return energetico;
    }

    public void setEnergetico(final boolean flag) {
        this.energetico = flag;
    }

    public boolean isIce() {
        return ice;
    }

    public void setIce(final boolean flag) {
        this.ice = flag;
    }

    public boolean isSuco() {
        return suco;
    }

    public void setSuco(final boolean flag) {
        this.suco = flag;
    }

    public boolean isToddynho() {
        return toddynho;
    }

    public void setToddynho(final boolean flag) {
        this.toddynho = flag;
    }

    public boolean isAguaCoco() {
        return aguaCoco;
    }

    public void setAguaCoco(boolean flag) {
        this.aguaCoco = flag;
    }

    public Long getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(final Long id) {
        this.idQuarto = id;
    }

}