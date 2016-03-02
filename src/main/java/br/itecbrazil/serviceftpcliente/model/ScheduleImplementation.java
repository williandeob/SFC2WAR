/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 *
 * @author Pedro Teles
 */
public abstract class ScheduleImplementation implements Schedule {

    int tamanhoDoPoolDeThread;
    ScheduledExecutorService executorService;

    public ScheduleImplementation(int tamanhoDoPoolDeThread) {
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
    public abstract void execute();

    @Override
    public void parar() {
        getExecutorService().shutdown();
    }

}
