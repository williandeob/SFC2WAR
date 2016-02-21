/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.controller;

import br.itecbrazil.serviceftpcliente.view.PanelDashBoardEnvioRetorno;
import java.util.Date;

/**
 *
 * @author itec-desenv-willian
 */
public class ControllerDashBoardEnvioRetorno {

    private static long tamanahoMaximoDoLogEmcaracteres = 20000;

    PanelDashBoardEnvioRetorno view;

    public ControllerDashBoardEnvioRetorno(PanelDashBoardEnvioRetorno view) {
        this.view = view;
    }

    public PanelDashBoardEnvioRetorno getView() {
        return view;
    }

    public synchronized void atualizarLogsDeEnvio(String host, String diretorio, String nomeDoArquivo) {
        escreverEnvioLog(host, diretorio, nomeDoArquivo);
    }

    public synchronized void atualizarLogsDeRetorno(String host, String diretorio, String nomeDoArquivo) {
        escreverRetornoLog(host, diretorio, nomeDoArquivo);
    }

    private void escreverEnvioLog(String host, String diretorio, String nomeDoArquivo) {
        String conteudoASerAdicionado = new Date().toString() + ": Arquivo " + nomeDoArquivo + " enviado para " + host + "" + diretorio + "\n";

        if (getView().getjTextPaneLogsEnvio().getText().length() + conteudoASerAdicionado.length() > tamanahoMaximoDoLogEmcaracteres) {
            String novoConteudoCompleto = conteudoASerAdicionado
                    .concat(getView().getjTextPaneLogsEnvio().getText().substring(0, getView().getjTextPaneLogsEnvio().getText().length() - conteudoASerAdicionado.length()));
            getView().getjTextPaneLogsEnvio().setText(novoConteudoCompleto);
        } else {
            String novoConteudoCompleto = conteudoASerAdicionado.concat(getView().getjTextPaneLogsEnvio().getText());
            getView().getjTextPaneLogsEnvio().setText(novoConteudoCompleto);
        }
    }

    private void escreverRetornoLog(String host, String diretorio, String nomeDoArquivo) {
        String conteudoASerAdicionado = new Date().toString() + ": Arquivo " + nomeDoArquivo + " recebido do " + host + " armazenado em " + diretorio + "\n";

        if (getView().getjTextPaneLogsRetorno().getText().length() + conteudoASerAdicionado.length() > tamanahoMaximoDoLogEmcaracteres) {
            String novoConteudoCompleto = conteudoASerAdicionado
                    .concat(getView().getjTextPaneLogsRetorno().getText().substring(0, getView().getjTextPaneLogsRetorno().getText().length() - conteudoASerAdicionado.length()));
            getView().getjTextPaneLogsRetorno().setText(novoConteudoCompleto);
        } else {
            String novoConteudoCompleto = conteudoASerAdicionado.concat(getView().getjTextPaneLogsRetorno().getText());
            getView().getjTextPaneLogsRetorno().setText(novoConteudoCompleto);
        }
    }

}
