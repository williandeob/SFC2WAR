/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.enums;

import java.io.File;

/**
 *
 * @author itec-desenv-willian
 *
 * Enum responsavel por guardar o caminho do diretorio de configuracao do
 * servico FTP.
 */
public enum EnumDiretorio {

    Configuracao(System.getProperty("user.home").concat(File.separator).concat("Itecbrazil").concat(File.separator)
            .concat("ServiceFTPCliente").concat(File.separator).concat("Config"));
    String diretorio;

    private EnumDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getDiretorio() {
        return diretorio;
    }

}
