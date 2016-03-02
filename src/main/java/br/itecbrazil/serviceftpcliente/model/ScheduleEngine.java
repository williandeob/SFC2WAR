/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;

/**
 *
 * @author itec-desenv-willian
 */
public class ScheduleEngine {

    private static Schedule envio;
    private static Schedule retorno;

    public static Schedule getEnvio() {
        return envio;
    }

    public static void setEnvio(Schedule envio) {
        ScheduleEngine.envio = envio;
    }

    public static Schedule getRetorno() {
        return retorno;
    }

    public static void setRetorno(Schedule retorno) {
        ScheduleEngine.retorno = retorno;
    }

    private static void SchedulerFactory() {
        envio = new ScheduleEnvio(MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes().size());
        retorno = new ScheduleRetorno(MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes().size());
    }

    public static void prepararEIniciarScheduler() {
        SchedulerFactory();
        getEnvio().execute();
        getRetorno().execute();
    }

    public static void pararScheduler() {
        getEnvio().parar();
        getRetorno().parar();
    }

}
