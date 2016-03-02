/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.enums;

/**
 *
 * @author itec-desenv-willian
 */
public enum EnumEnvioRetorno {

    Envio(0), Retorno(1);
    private int valor;

    private EnumEnvioRetorno(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
