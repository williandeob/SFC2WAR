/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

/**
 *
 * @author willian
 */
public class Config {
    
    protected String host = "";
    protected String usuario = "";
    protected String senha = "";
    protected String cnpj = "";

    public Config() {
    }

    public Config(String host, String usuario, String senha) {
        this.host = host;
        this.usuario = usuario;
        this.senha = senha;
    }
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
}
