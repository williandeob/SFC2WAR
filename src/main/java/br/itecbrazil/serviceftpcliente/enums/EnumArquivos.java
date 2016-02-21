/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.enums;

/**
 *
 * @author itec-desenv-willian
 * 
 * Enum que guarda as ifnormacoes sobre arquivos, dentre eles o de configuracao.
 */
public enum EnumArquivos {

    /**
     * Documento que contem um String JSON com os objetos Config
     * que armazenam as configuracoes do FTP.
     */
    Configuracao("config.xml");
    String nomeDoArquivo;

    private EnumArquivos(String nomeDoArquivo) {
        this.nomeDoArquivo = nomeDoArquivo;
    }

    public String getNomeDoArquivo() {
        return nomeDoArquivo;
    }
    
    
    
}
