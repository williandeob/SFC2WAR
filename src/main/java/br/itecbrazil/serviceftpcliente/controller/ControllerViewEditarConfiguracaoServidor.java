/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.controller;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.model.Config;
import br.itecbrazil.serviceftpcliente.model.ParseEngineConfig;
import br.itecbrazil.serviceftpcliente.model.ScheduleEngine;
import br.itecbrazil.serviceftpcliente.view.PanelConfigServidoresFTPs;
import br.itecbrazil.serviceftpcliente.view.ViewEditarConfiguracaoServidor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author willian
 */
public class ControllerViewEditarConfiguracaoServidor {
    public ViewEditarConfiguracaoServidor view;
    public Config config;
    public int index;
    private ParseEngineConfig parseEngine;
    private PanelConfigServidoresFTPs painel;

    public ControllerViewEditarConfiguracaoServidor(ViewEditarConfiguracaoServidor view, Config config, int index, PanelConfigServidoresFTPs painel) {
        this.view = view;
        this.config = config;
        this.index = index;
        this.painel = painel;
        this.parseEngine = new ParseEngineConfig();
    }

    public ViewEditarConfiguracaoServidor getView() {
        return view;
    }

    public Config getConfig() {
        return config;
    }

    public int getIndex() {
        return index;
    }

    public PanelConfigServidoresFTPs getPainel() {
        return painel;
    }

    public ParseEngineConfig getParseEngine() {
        return parseEngine;
    }

    public void setData(Config config) {
        getView().getjTextFieldHost().setText(config.getHost());
        getView().getjTextFieldCnpj().setText(config.getCnpj());
        getView().getjTextFieldUsuario().setText(config.getUsuario());
        getView().getjTextFieldSenha().setText(config.getSenha());
    }

    public void atualizarConfiguracao() {
        if(validarDados()){
            ScheduleEngine.pararScheduler();
            try{
                
                getConfig().setHost(getView().getjTextFieldHost().getText().trim());
                getConfig().setUsuario(getView().getjTextFieldUsuario().getText().trim());
                getConfig().setSenha(getView().getjTextFieldSenha().getText().trim());
                getConfig().setCnpj(getView().getjTextFieldCnpj().getText().trim());

                MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes().set(getIndex(), getConfig());
                getParseEngine().toXMLArquivoDeConfiguracaoGeral(MainServiceFTPCliente.getConfiguracaoGeral());
              
                getPainel().getModeloDaTabelaDeConfigFTP().setValueAt(getConfig().getHost(), getIndex(), 0);
                getPainel().getModeloDaTabelaDeConfigFTP().setValueAt(getConfig().getCnpj(), getIndex(), 1); 
                getPainel().getModeloDaTabelaDeConfigFTP().setValueAt(getConfig().getUsuario(), getIndex(), 2);
                getPainel().getModeloDaTabelaDeConfigFTP().setValueAt(getConfig().getSenha(), getIndex(), 3);
                
                getView().dispose();
                
            } catch (IOException ex) {
                Logger.getLogger(ControllerViewEditarConfiguracaoServidor.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                 ScheduleEngine.prepararEIniciarScheduler();
            }
        }
    }

    private boolean validarDados() {
         if (getView().getjTextFieldHost().getText().trim().isEmpty()
                || getView().getjTextFieldUsuario().getText().trim().isEmpty()
                || getView().getjTextFieldSenha().getText().trim().isEmpty()
                || getView().getjTextFieldCnpj().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(getView(), "ALERT: Os campos são de preenchimento obrigatório", "ALERT", WARNING_MESSAGE);
            return false;
        }else{
             return true;
         }
    }

    
}