/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.controller;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.model.ParseEngineConfig;
import br.itecbrazil.serviceftpcliente.model.ScheduleEngine;
import br.itecbrazil.serviceftpcliente.view.PanelConfigLocalDiretorios;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.JTextField;

/**
 *
 * @author itec-desenv-willian
 */
public class ControllerPanelConfigLocalDiretorios {

    PanelConfigLocalDiretorios view;

    public ControllerPanelConfigLocalDiretorios(PanelConfigLocalDiretorios view) {
        this.view = view;
    }

    public PanelConfigLocalDiretorios getView() {
        return view;
    }

    public void setView(PanelConfigLocalDiretorios view) {
        this.view = view;
    }

    public void preencherPathDiretorios() {
        getView().getjTextFieldDirEnvio().setText(MainServiceFTPCliente.getConfiguracaoGeral().getDiretorioDeEnvio());
        getView().getjTextFieldDirRetorno().setText(MainServiceFTPCliente.getConfiguracaoGeral().getDiretorioDeRetorno());
    }

    public void alterarPathDiretorios(JTextField diretorio) {
        int returnVal;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        returnVal = fc.showOpenDialog(getView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (!compararNovoDiretorioComDiretoriosAnteriores(fc.getSelectedFile().getPath())) {
                diretorio.setText(fc.getSelectedFile().getPath());
                ScheduleEngine.pararScheduler();
                processarAlteracao(diretorio);
            } else {
                JOptionPane.showMessageDialog(getView(), "INFO: Diretório já selecionado como envio ou retorno", "ALERT", WARNING_MESSAGE);
            }
        }
    }

    private boolean compararNovoDiretorioComDiretoriosAnteriores(String novoDiretorio) {
        return novoDiretorio.equals(getView().getjTextFieldDirEnvio().getText())
                || novoDiretorio.equals(getView().getjTextFieldDirRetorno().getText());

    }

    private void processarAlteracao(JTextField diretorio) {        
        if (("jTextFieldDirEnvio").equals(diretorio.getName())){
            MainServiceFTPCliente.getConfiguracaoGeral().setDiretorioDeEnvio(diretorio.getText());
        } else {
            MainServiceFTPCliente.getConfiguracaoGeral().setDiretorioDeRetorno(diretorio.getText());
        }

        ParseEngineConfig parse = new ParseEngineConfig();
        try {
            parse.toXMLArquivoDeConfiguracaoGeral(MainServiceFTPCliente.getConfiguracaoGeral());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(getView(), "ALERT: Não foi possivel carregar as informações de configuração"
                    + " ocorreu falha na escrita do arquivo config.xml", "ALERT", WARNING_MESSAGE);
            Logger.getLogger(ControllerPanelConfigLocalDiretorios.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ScheduleEngine.prepararEIniciarScheduler();
        }
    }

}
