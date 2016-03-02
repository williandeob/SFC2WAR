/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.controller;

import br.itecbrazil.pedido.api.ftp.Config;
import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.model.ParseEngineConfig;
import br.itecbrazil.serviceftpcliente.model.ScheduleEngine;
import br.itecbrazil.serviceftpcliente.view.ButtonTable;
import br.itecbrazil.serviceftpcliente.view.CellRenderer;
import br.itecbrazil.serviceftpcliente.view.PanelConfigServidoresFTPs;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author itec-desenv-willian
 */
public class ControllerPanelConfigServidoresFTPs {

    private final PanelConfigServidoresFTPs view;
    private ParseEngineConfig parseEngine;

    public ControllerPanelConfigServidoresFTPs(PanelConfigServidoresFTPs view) {
        this.view = view;
        parseEngine = new ParseEngineConfig();
    }

    public PanelConfigServidoresFTPs getView() {
        return view;
    }

    public void popularTable() {
        getView().getModeloDaTabelaDeConfigFTP().addColumn("HOST");
        getView().getModeloDaTabelaDeConfigFTP().addColumn("CNPJ");
        getView().getModeloDaTabelaDeConfigFTP().addColumn("DIR. FTP ENVIO");
        getView().getModeloDaTabelaDeConfigFTP().addColumn("DIR. FTP BUSCA");
        getView().getModeloDaTabelaDeConfigFTP().addColumn("EXCLUIR");
        getView().getjTableConfigFTP().getTableHeader().setPreferredSize(new Dimension(getView().getjTableConfigFTP().getWidth(), 25));
        getView().getjTableConfigFTP().getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));

        for (Config config : MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes()) {
            getView().getModeloDaTabelaDeConfigFTP().addRow(new Object[]{config.getHost(), config.getCnpj(),
                config.getDirFornFtpReader(), config.getDirFornFtpWriter(), "Excluir"});
        }
        getView().getjTableConfigFTP().setDefaultRenderer(Object.class, new CellRenderer());
        getView().getjTableConfigFTP().getColumn("EXCLUIR").setCellRenderer(new ButtonTable("Excluir"));

    }

    /**
     *
     * @param posicaoDaColuna com valor 4 se refere a coluna de Exclusão.
     * @param posicaoDaLinha
     * @param cnpjDaLinhaSelecionada
     */
    public void dispararAcaoDoController(int posicaoDaColuna, int posicaoDaLinha, Object cnpjDaLinhaSelecionada) {

        switch (posicaoDaColuna) {
            case 4:
                excluirConfiguracaoDeFtp((String) cnpjDaLinhaSelecionada, posicaoDaLinha);
                break;
        }
    }

    private void excluirConfiguracaoDeFtp(String cnpjDoClienteLigadoAoFTP, int posicaoDaLinha) {
        if (cnpjDoClienteLigadoAoFTP == null || cnpjDoClienteLigadoAoFTP.trim().isEmpty()) {
            JOptionPane.showMessageDialog(getView(), "INFO: Não é possivel excluir configuracao sem CNPJ", "ALERT", WARNING_MESSAGE);
            return;
        }

        if (posicaoDaLinha < 0) {
            JOptionPane.showMessageDialog(getView(), "INFO: Linhas selecionada Inválida", "ALERT", WARNING_MESSAGE);
            return;
        }

        Config configASerRemovido = MainServiceFTPCliente.getConfiguracaoGeral().buscarConfiguracoPorCNPJ(cnpjDoClienteLigadoAoFTP);
        if (configASerRemovido != null) {
            ScheduleEngine.pararScheduler();
            MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes().remove(configASerRemovido);
            try {
                parseEngine.toXMLArquivoDeConfiguracaoGeral(MainServiceFTPCliente.getConfiguracaoGeral());
                getView().getModeloDaTabelaDeConfigFTP().removeRow(posicaoDaLinha);
                JOptionPane.showMessageDialog(getView(), "CONFIRM: Exclusão realizada com Sucesso"
                        + "", "ALERT", INFORMATION_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(ControllerPanelConfigServidoresFTPs.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(getView(), "INFO: Ocorreu um erro ao excluir o registro"
                        + " ocorreu falha na escrita do arquivo config.xml", "ALERT", WARNING_MESSAGE);
            }finally{
                ScheduleEngine.prepararEIniciarScheduler();
            }  
        } else {
            JOptionPane.showMessageDialog(getView(), "INFO: Não é possivel excluir configuracao sem CNPJ", "ALERT", WARNING_MESSAGE);
        }

    }

    public void adicionarConfiguracoesDeFTP() {
        int returnVal;
        List<Config> listaDeNovasConfiguracoes;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        returnVal = fc.showOpenDialog(getView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            if (fc.getSelectedFile().length() == 0) {
                JOptionPane.showMessageDialog(getView(), "INFO: Arquivo selecionado está vazio", "ALERT", WARNING_MESSAGE);
                return;
            }

            listaDeNovasConfiguracoes = parseEngine.fromXMLListaDeConfiguracoesFTP(fc.getSelectedFile());
            if (listaDeNovasConfiguracoes == null || listaDeNovasConfiguracoes.isEmpty()) {
                JOptionPane.showMessageDialog(getView(), "INFO: Não foi possivel adicionar novas configurações", "ALERT", WARNING_MESSAGE);
            } else {
                if (!parseEngine.validarParseFromXML(listaDeNovasConfiguracoes)) {
                    JOptionPane.showMessageDialog(getView(), "INFO: O arquivo possui duas configurações com o mesmo CNPJ", "ALERT", WARNING_MESSAGE);
                    return;
                }
                if (!MainServiceFTPCliente.getConfiguracaoGeral().adicionarNovasConfiguracoes(listaDeNovasConfiguracoes)) {
                    JOptionPane.showMessageDialog(getView(), "INFO: Configurações não adicionadas, pois possuem CNPJ já cadastrado", "ALERT", WARNING_MESSAGE);
                } else {
                    processoDeAdicionarNovasConfiguracoes(listaDeNovasConfiguracoes);
                    JOptionPane.showMessageDialog(getView(), "CONFIRM: Inclusão realizada com Sucesso"
                            + "", "ALERT", INFORMATION_MESSAGE);
                }

            }
        }
    }

    private void processoDeAdicionarNovasConfiguracoes(List<Config> listaDeNovasConfiguracoes) {
        ScheduleEngine.pararScheduler();
        try {
            parseEngine.toXMLArquivoDeConfiguracaoGeral(MainServiceFTPCliente.getConfiguracaoGeral());
            for (Config config : listaDeNovasConfiguracoes) {
                getView().getModeloDaTabelaDeConfigFTP().addRow(new Object[]{config.getHost(), config.getCnpj(),
                    config.getDirFornFtpReader(), config.getDirFornFtpWriter()});    
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerPanelConfigServidoresFTPs.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(getView(), "ALERT: Não foi possivel carregar as informações de configuração"
                    + " ocorreu falha na escrita do arquivo config.xml", "ALERT", WARNING_MESSAGE);
        } finally{
            ScheduleEngine.prepararEIniciarScheduler();
        }
        
    }

    public void limparConfiguracoes() {
        ScheduleEngine.pararScheduler();
        MainServiceFTPCliente.getConfiguracaoGeral().getListaDeConfiguracoes().clear();
        try {
            parseEngine.toXMLArquivoDeConfiguracaoGeral(MainServiceFTPCliente.getConfiguracaoGeral());
            while (getView().getModeloDaTabelaDeConfigFTP().getRowCount() > 0) {
                getView().getModeloDaTabelaDeConfigFTP().removeRow(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(ControllerPanelConfigServidoresFTPs.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(getView(), "ALERT: Não foi possivel carregar as informações de configuração"
                    + " ocorreu falha na escrita do arquivo config.xml", "ALERT", WARNING_MESSAGE);
        } finally{
            ScheduleEngine.prepararEIniciarScheduler();
        }
    }
}
