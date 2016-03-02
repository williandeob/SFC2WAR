/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente;

import br.itecbrazil.serviceftpcliente.enums.EnumArquivos;
import br.itecbrazil.serviceftpcliente.model.ConfiguracaoGeral;
import br.itecbrazil.serviceftpcliente.model.ParseEngineConfig;
import br.itecbrazil.serviceftpcliente.util.UtilDiretorios;
import br.itecbrazil.serviceftpcliente.util.UtilSocket;
import br.itecbrazil.serviceftpcliente.view.ViewCadastroDeConfiguracaoFTP;
import br.itecbrazil.serviceftpcliente.view.ViewHome;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;

/**
 * Classe principal do sistema responsavel por chamar metodos de configuracao e
 * tela principal.
 *
 * @author itec-desenv-willian
 */
public class MainServiceFTPCliente {

    private static ConfiguracaoGeral configuracaoGeral;
    private static File diretoriodeConfiguracao;
    private static File arquivoDeConfiguracao;
    
    
    private MainServiceFTPCliente(){
        
    }
            

    /**
     * Metodo responsavel por criar ou carregar o arquivo de configuracao dos
     * servidores FTPs.
     *
     * @author willian
     */
    private static void configurarDiretorioeArquivoDeConfiguracao() {

        diretoriodeConfiguracao = UtilDiretorios.getDiretorioDeConfiguracao();
        File[] arquivoEncontradosNoDiretorioDeConfiguracao = diretoriodeConfiguracao.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.equals(EnumArquivos.Configuracao.getNomeDoArquivo());
            }
        });

        for (File arquivosRetornados : arquivoEncontradosNoDiretorioDeConfiguracao) {
            if (arquivosRetornados.getName().equals(EnumArquivos.Configuracao.getNomeDoArquivo()) && arquivosRetornados.isFile()) {
                arquivoDeConfiguracao = arquivosRetornados;
                break;
            }
        }

    }

    public static ConfiguracaoGeral getConfiguracaoGeral() {
        return configuracaoGeral;
    }

    /**
     * A partir do arquivo de configuracao passado por parametro, caso o mesmo
     * seja válido, o arquivo é processado, sendo as configuracoes atribuidas e
     * a tela principal exibida ao usuario, caso o arquivo não exista ou esteja
     * vazio é exibido o fluxo de cadastro de configuracao.
     *
     * @author willian
     * @param config
     */
    private static void processarArquivoDeConfiguracao(File config){
        if (config == null || !config.canRead() || config.length() == 0) {
            chamarCadastroDeConfiguracaoFTP();
        } else {
            carregarConfiguracao(config);
        }
    }

    private static void chamarCadastroDeConfiguracaoFTP() {
        ViewCadastroDeConfiguracaoFTP viewCadastroDeConfiguracaoFTP = new ViewCadastroDeConfiguracaoFTP(null, true);
        viewCadastroDeConfiguracaoFTP.setVisible(true);
    }

    /**
     * De acordo com o arquivo de configuracao passado por parametro é realizado
     * o parse do conteudo do documento para o objeto ConfigurcaoGeral
     *
     * @author willian
     * @param config
     */
    private static void carregarConfiguracao(File config){
        ParseEngineConfig parseEngine = new ParseEngineConfig();
        configuracaoGeral = parseEngine.fromXMLConfiguracaoGeral(config);            
    }

    /**
     * Se a configuracao estiver correta o metodo iniciar a tela principal do
     * sistema
     *
     * @author willian
     * @param configuracaoGeral
     */
    private static void carregarTelaPrincipal(ConfiguracaoGeral configuracaoGeral) {
        if (configuracaoGeral.getDiretorioDeEnvio() == null || configuracaoGeral.getDiretorioDeRetorno() == null
                || configuracaoGeral.getListaDeConfiguracoes() == null) {
            JOptionPane.showMessageDialog(null, "ERRO: Ao carregar o arquivo de configuracao"
                    + "", "ALERT", ERROR_MESSAGE);
        } else {
            ViewHome viewHome = new ViewHome();
            viewHome.setVisible(true);
        }
    }

    public static void main(String[] args) {
        try {
            UtilSocket.verificarInstaciaEmExecucao();

            configuracaoGeral = new ConfiguracaoGeral();
            configurarDiretorioeArquivoDeConfiguracao();
            processarArquivoDeConfiguracao(arquivoDeConfiguracao);
            carregarTelaPrincipal(configuracaoGeral);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "ERRO: O Programa já está em excecução ou a porta 9581 está ocupada"
                    + "", "ALERT", ERROR_MESSAGE);
        } catch(ClassCastException ex){
             JOptionPane.showMessageDialog(null, "ERRO: Erro de inicialização, verifique o XML de configuração!"
                    + "", "ALERT", ERROR_MESSAGE); 
        }

    }

}
