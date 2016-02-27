/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.pedido.api.ftp.Config;
import br.itecbrazil.serviceftpcliente.enums.EnumArquivos;
import br.itecbrazil.serviceftpcliente.enums.EnumDiretorio;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.io.StreamException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author itec-desenv-willian
 */
public class ParseEngineConfig {

    XStream xStream;

    public ParseEngineConfig() {
        this.xStream = new XStream();
    }

    public XStream getxStream() {
        return xStream;
    }

    /**
     * @author willian
     * @param configuracaoGeral deve estar com todos os atributos populados
     * @return true se a partir do objeto configuracaoGeral passado por
     * parametro seja possivel a serializacao do mesmo em XML e persistido no
     * documetento config.xml, que se encontra no diretorio descrito no
     * EnumDiretorio.Configuracao/EnumsAqruivos.Configuracao, caso não seja
     * possivel essa serialização o metodo retorna false.
     */
    public void toXMLArquivoDeConfiguracaoGeral(ConfiguracaoGeral configuracaoGeral) throws FileNotFoundException, IOException {
        FileOutputStream fops = null;

            File config = new File(EnumDiretorio.Configuracao.getDiretorio()
                    .concat(File.separator).concat(EnumArquivos.Configuracao.getNomeDoArquivo()));

            fops = new FileOutputStream(config);
            xStream.alias("configuracaoGeral", ConfiguracaoGeral.class);
            xStream.alias("listaDeConfiguracoes", List.class);
            xStream.alias("configuracao", Config.class);
            xStream.toXML(configuracaoGeral, fops);

            fops.close();
    }

    /**
     * @author willian
     * @param arquivoXML
     * @return listaDeConfiguracoesCarregadas se na deserializacao do arquivo
     * XML importado pelo usuário seja possivel recuperar a lista de Config, ou
     * seja, cada configuracao de FTP e retorna null caso essa lista não for
     * recuperada.
     */
    public List<Config> fromXMLListaDeConfiguracoesFTP(File arquivoXML) {
        BufferedReader input = null;
        List<Config> listaDeConfiguracoesCarregadas;

        try {
            xStream.alias("listaDeConfiguracoes", List.class);
            xStream.alias("configuracao", Config.class);
            input = new BufferedReader(new FileReader(arquivoXML));

            listaDeConfiguracoesCarregadas = (List<Config>) xStream.fromXML(input);

            return listaDeConfiguracoesCarregadas;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (StreamException ex) {
            Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "INFO: Arquivo Inválido, verificar conteúdo do documento importado", "ALERT", WARNING_MESSAGE);
            return null;
        } catch (ConversionException ex) {
            Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "INFO: Arquivo Inválido, verificar conteúdo do documento importado", "ALERT", WARNING_MESSAGE);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "INFO: Arquivo Inválido, verificar conteúdo do documento importado", "ALERT", WARNING_MESSAGE);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    /**
     * Verifica se na lista de Config passada por parametro possuem Config com o
     * mesmo valor de CNPJ.
     *
     * @param listaDeConfiguracoesCarregadas
     * @return true se os valores do CNPJ das Config presente na lista forem
     * distintas, false para outros casos.
     */
    public boolean validarParseFromXML(List<Config> listaDeConfiguracoesCarregadas) {
        int index = 0;
        while (index < listaDeConfiguracoesCarregadas.size()) {
            Config config = listaDeConfiguracoesCarregadas.get(index);
            for (int i = index + 1; i < listaDeConfiguracoesCarregadas.size(); i++) {
                if (config.getCnpj().equals(listaDeConfiguracoesCarregadas.get(i).getCnpj())) {
                    return false;
                }
            }
            index++;
        }
        return true;
    }

    /**
     * @author willian
     * @param config
     * @return objeto de ConfiguracaoGeral conforme o conteudo do arquivo
     * config.xml, caso a serializacao não seja bem sucedida é retornado null.
     */
    public ConfiguracaoGeral fromXMLConfiguracaoGeral(File config) {
        BufferedReader input = null;
        try {

            ConfiguracaoGeral configuracaoGeral;

            xStream.alias("configuracaoGeral", ConfiguracaoGeral.class);
            xStream.alias("listaDeConfiguracoes", List.class);
            xStream.alias("configuracao", Config.class);

            input = new BufferedReader(new FileReader(config));
            configuracaoGeral = (ConfiguracaoGeral) xStream.fromXML(input);

            return configuracaoGeral;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    Logger.getLogger(ParseEngineConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
