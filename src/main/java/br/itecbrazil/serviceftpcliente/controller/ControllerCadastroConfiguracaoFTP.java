/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.controller;

import br.itecbrazil.serviceftpcliente.model.Config;
import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.model.ParseEngineConfig;
import br.itecbrazil.serviceftpcliente.util.UtilConfig;
import br.itecbrazil.serviceftpcliente.util.UtilDiretorios;
import br.itecbrazil.serviceftpcliente.view.ViewCadastroDeConfiguracaoFTP;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.JTextField;

/**
 * Classe que representa o controle da ViewCadastroConfiguracaoFTP
 * 
 * @author itec-desenv-willian
 */
public class ControllerCadastroConfiguracaoFTP {

    ViewCadastroDeConfiguracaoFTP view;
    File arquivoXMLDeImportacao;
    ParseEngineConfig parseEngine;

    public ControllerCadastroConfiguracaoFTP(ViewCadastroDeConfiguracaoFTP view) {
        this.view = view;
        parseEngine = new ParseEngineConfig();
        arquivoXMLDeImportacao = new File("configFornTemp.xml");
    }

    public ViewCadastroDeConfiguracaoFTP getView() {
        return view;
    }

    public File getArquivoXMLDeImportacao() {
        return arquivoXMLDeImportacao;
    }
    
    private void gerarArquivoXmlImpotacao(Config config) throws FileNotFoundException, IOException {
         FileOutputStream fops = new FileOutputStream(getArquivoXMLDeImportacao());
         String conteudo = "<listaDeConfiguracoes>\n"
                + "<configuracao>\n"
                + "    <host>"+config.getHost()+"</host>\n"
                + "    <usuario>"+config.getUsuario()+"</usuario>\n"
                + "    <senha>"+config.getSenha()+"</senha>\n"
                + "    <cnpj>"+config.getCnpj()+"</cnpj>\n"
                + "</configuracao>\n"
                + "</listaDeConfiguracoes>";
         fops.write(conteudo.getBytes("UTF-8"));
         fops.flush();
         fops.close();      
    }

    
    private Config getFactoryConfig(){
        return new Config(getView().getjTextFieldHost().getText().trim(), getView().getjTextFieldUsuario().getText().trim(), 
                getView().getjTextFieldSenha().getText().trim(), getView().getjTextFieldCnpj().getText().trim());
    };
    
    
    /**
     * @author willian 
     * 
     * Abre o sistema de diretorio local para o upload do
     * arquivo de configuracao dos servico FTP, importante lembrar que essa configuracao
     * é somente das informações do FTP, informacoes de diretorios de envio e retorno não
     * estão presentes neste arquivo a ser carregado.
     */
    public void carregarXML() {
        int returnVal;
        final JFileChooser fc = new JFileChooser();
        returnVal = fc.showOpenDialog(getView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            arquivoXMLDeImportacao = fc.getSelectedFile();
        } else {
            JOptionPane.showMessageDialog(getView(), "INFO: O arquivo deve ser carregado", "ALERT", WARNING_MESSAGE);
        }
    }

    /**
     * @author willian
     * @return true se o diretorios locais para envio e retorno dos arquivos
     * estiverem sido criados com sucesso e todos os demais campos tenham sido preenchido
     * ou false, caso por algum motivo não seja possivel criar os diretorios. 
     * Caso seja passado um diretorio fora dos mapemamentos das unidades locais, 
     * as pastas serão criadas na raiz do projeto.
     */
    public boolean validarFormulario() {
        if (getView().getjTextFieldDiretorioEnvio().getText().trim().isEmpty() 
                || getView().getjTextFieldDiretorioRetorno().getText().trim().isEmpty()
                || getView().getjTextFieldHost().getText().trim().isEmpty()
                || getView().getjTextFieldPorta().getText().trim().isEmpty()
                || getView().getjTextFieldUsuario().getText().trim().isEmpty()
                || getView().getjTextFieldSenha().getText().trim().isEmpty()
                || getView().getjTextFieldCnpj().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(getView(), "ALERT: Os campos são de preenchimento obrigatório", "ALERT", WARNING_MESSAGE);
            return false;
        }
        
        if(getView().getjTextFieldDiretorioEnvio().getText().trim().equals(getView().getjTextFieldDiretorioRetorno().getText().trim())){
             JOptionPane.showMessageDialog(getView(), "ALERT: Os diretórios de envio e retorno devem ser diferentes", "ALERT", WARNING_MESSAGE);
            return false;
        }

        File diretorioLeituraFTP = UtilDiretorios.criarDiretorio(getView().getjTextFieldDiretorioEnvio().getText());
        File diretorioEscritaFTP = UtilDiretorios.criarDiretorio(getView().getjTextFieldDiretorioRetorno().getText());

        if (!diretorioLeituraFTP.isDirectory() || !diretorioEscritaFTP.isDirectory()) {
            JOptionPane.showMessageDialog(getView(), "ALERT: Diretórios inválidos", "ALERT", WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    /**
     * @author willian
     * Metodo realiza o parse dos dados informados pelo usuario
     * com as configuracoes dos FTPs, caso esse parse seja realizado com sucesso
     * o metodo defini valores para o objeto statico  MainServiceFTPCliente.configuracaoGeral
     * logo após essa definição há um outro parse so que dessa vez para XML,
     * em que o arquivo de config.xml é escrito, caso ocorra algum erro nesse parse
     * o objeto MainServiceFTPCliente.configuracaoGeral é limpado e o config.xml deletado.
     */
    public void criarConfiguracaoFTP() {

        try {
            gerarArquivoXmlImpotacao(getFactoryConfig());
            List<Config> listaDeConfiguracaoAux = parseEngine.fromXMLListaDeConfiguracoesFTP(getArquivoXMLDeImportacao());
            getArquivoXMLDeImportacao().delete();
            
            if(listaDeConfiguracaoAux == null || listaDeConfiguracaoAux.isEmpty()) {
                JOptionPane.showMessageDialog(getView(), "ALERT: Não foi possivel carregar as informações de configuração", "ALERT", WARNING_MESSAGE);
            }else{
                if (!parseEngine.validarParseFromXML(listaDeConfiguracaoAux)) {
                    JOptionPane.showMessageDialog(getView(), "INFO: Arquivo Inválido, possui duas configurações com o mesmo CNPJ", "ALERT", WARNING_MESSAGE);
                    return;
                }
            
                MainServiceFTPCliente.getConfiguracaoGeral().setDiretorioDeEnvio(getView().getjTextFieldDiretorioEnvio().getText().trim());
                MainServiceFTPCliente.getConfiguracaoGeral().setDiretorioDeRetorno(getView().getjTextFieldDiretorioRetorno().getText().trim());
                MainServiceFTPCliente.getConfiguracaoGeral().setListaDeConfiguracoes(listaDeConfiguracaoAux);

                parseEngine.toXMLArquivoDeConfiguracaoGeral(MainServiceFTPCliente.getConfiguracaoGeral());
                JOptionPane.showMessageDialog(getView(), "CONFIRM: Configuração realizada com Sucesso", "ALERT", INFORMATION_MESSAGE); 
                getView().dispose();
            }
                 
        }catch(IOException ex){
                Logger.getLogger(ControllerCadastroConfiguracaoFTP.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(getView(), "ALERT: Não foi possivel carregar as informações de configuração arquivo não encontrado", "ALERT", WARNING_MESSAGE);
                UtilConfig.limparConfiguracao();
        }catch (HeadlessException ex) {
                Logger.getLogger(ControllerCadastroConfiguracaoFTP.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(getView(), "ALERT: Não foi possivel carregar as informações de configuração ocorreu falha na escrita do arquivo config.xml", "ALERT", WARNING_MESSAGE);
                UtilConfig.limparConfiguracao();
        }
    }
    

    public void carregarDiretorio(JTextField diretorio) {
      int returnVal;
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        returnVal = fc.showOpenDialog(getView());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            diretorio.setText(fc.getSelectedFile().getPath());
        }
    }
}