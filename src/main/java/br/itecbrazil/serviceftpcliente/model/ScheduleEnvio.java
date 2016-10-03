/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author itec-desenv-willian
 */
public class ScheduleEnvio extends ScheduleImplementation {
    
    public ScheduleEnvio(int tamanhoDoPoolDeThread){
        super(tamanhoDoPoolDeThread);
    }

    @Override
    public void execute() {
        for (Config configThread : MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes()) {
            getExecutorService().scheduleAtFixedRate(new ThreadEnvio(configThread), 30, 90, TimeUnit.SECONDS);
        }

    }

}
