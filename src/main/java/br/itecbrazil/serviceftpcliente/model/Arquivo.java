/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import java.util.Date;

/**
 *
 * @author willian
 */
public class Arquivo {
    private int tipo;
    private String nome;
    private Date dataDeTransmissao;

    public Arquivo(int tipo, String nome, Date dataDeTransmissao) {
        this.tipo = tipo;
        this.nome = nome;
        this.dataDeTransmissao = dataDeTransmissao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataDeTransmissao() {
        return dataDeTransmissao;
    }

    public void setDataDeTransmissao(Date dataDeTransmissao) {
        this.dataDeTransmissao = dataDeTransmissao;
    }
    
    
}
