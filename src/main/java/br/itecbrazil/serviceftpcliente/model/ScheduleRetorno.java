/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.view.PanelDashBoardEnvioRetorno;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author itec-desenv-willian
 */
public class ScheduleRetorno extends ScheduleImplementation {

     public ScheduleRetorno(int tamanhoDoPoolDeThread){
        super(tamanhoDoPoolDeThread);
    }
    
    @Override
    public void execute() {
        for (Config configThread : MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes()) {
            getExecutorService().scheduleAtFixedRate(new ThreadRetorno(configThread, PanelDashBoardEnvioRetorno.getController()), 30, 90, TimeUnit.SECONDS);
        }

    }

}
