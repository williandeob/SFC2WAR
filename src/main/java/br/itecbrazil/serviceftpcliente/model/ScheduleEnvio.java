/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.pedido.api.ftp.Config;
import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.view.PanelDashBoardEnvioRetorno;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author itec-desenv-willian
 */
public class ScheduleEnvio implements Schedule {

    int tamanhoDoPoolDeThread;
    ScheduledExecutorService executorService;

    public ScheduleEnvio(int tamanhoDoPoolDeThread) {
        this.tamanhoDoPoolDeThread = tamanhoDoPoolDeThread;
        executorService = Executors.newScheduledThreadPool(tamanhoDoPoolDeThread);
    }

    public int getTamanhoDoPoolDeThread() {
        return tamanhoDoPoolDeThread;
    }

    public void setTamanhoDoPoolDeThread(int tamanhoDoPoolDeThread) {
        this.tamanhoDoPoolDeThread = tamanhoDoPoolDeThread;
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void execute() {
        for (Config configThread : MainServiceFTPCliente.configuracaoGeral.getListaDeConfiguracoes()) {
            getExecutorService().scheduleAtFixedRate(new ThreadEnvio(configThread, PanelDashBoardEnvioRetorno.controller), 30, 30, TimeUnit.SECONDS);
        }

    }

    @Override
    public void parar() {
        getExecutorService().shutdown();
    }

}
