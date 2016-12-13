/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.enums;

/**
 *
 * @author willian
 */
public enum EnumTipoArquivo {
    
    Envio(0, "envio"),
    Retorno(1, "retorno");

    int tipoDoArquivo;
    String descricao;

    private EnumTipoArquivo(int tipoDoArquivo, String descricao) {
        this.tipoDoArquivo = tipoDoArquivo;
        this.descricao = descricao;
    }

    public int getTipoDoArquivo() {
        return tipoDoArquivo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
}
