package br.com.listadecompras.model;

import io.realm.RealmObject;

public class ProdutoList extends RealmObject {


    private Long id;
    private String dt_criacao;
    private String dt_finalizacao;
    private String status;
    private long qtde_itens;
    private double vl_total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDt_criacao() {
        return dt_criacao;
    }

    public void setDt_criacao(String dt_criacao) {
        this.dt_criacao = dt_criacao;
    }

    public String getDt_finalizacao() {
        return dt_finalizacao;
    }

    public void setDt_finalizacao(String dt_finalizacao) {
        this.dt_finalizacao = dt_finalizacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getQtde_itens() {
        return qtde_itens;
    }

    public void setQtde_itens(long qtde_itens) {
        this.qtde_itens = qtde_itens;
    }

    public double getVl_total() {
        return vl_total;
    }

    public void setVl_total(double vl_total) {
        this.vl_total = vl_total;
    }

    @Override
    public String toString() {
        return "ProdutoList{" +
                "id=" + id +
                ", dt_criacao='" + dt_criacao + '\'' +
                ", dt_finalizacao='" + dt_finalizacao + '\'' +
                ", status='" + status + '\'' +
                ", qtde_itens=" + qtde_itens +
                ", vl_total=" + vl_total +
                '}';
    }
}
