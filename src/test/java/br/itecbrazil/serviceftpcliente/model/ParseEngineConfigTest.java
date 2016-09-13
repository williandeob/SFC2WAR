/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author willian
 */
public class ParseEngineConfigTest {
public static File testFromXMLListaDeConfiguracoesFTP;
public static File testFromXMLConfiguracaoGeral;

    public ParseEngineConfigTest() {
        
    }

    @BeforeClass
    public static void setUpClass() {
        testFromXMLListaDeConfiguracoesFTP = new File("testFromXMLListaDeConfiguracoesFTP.xml");
        testFromXMLConfiguracaoGeral = new File("testFromXMLConfiguracaoGeral");
    }

    @AfterClass
    public static void tearDownClass() {
        try{
            testFromXMLListaDeConfiguracoesFTP.delete();
            testFromXMLConfiguracaoGeral.delete();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toXMLArquivoDeConfiguracaoGeral method, of class
     * ParseEngineConfig.
     */
    @Test
    public void testToXMLArquivoDeConfiguracaoGeral() throws Exception {
       /* Config config = new Config("host", "usuario", "senha");
        List<Config> listaDeConfiguracoes = new ArrayList<Config>();
        listaDeConfiguracoes.add(config);
        ConfiguracaoGeral configuracaoGeral = new ConfiguracaoGeral("diretorioDeEnvio", "diretorioDeRetorno", listaDeConfiguracoes);
        ParseEngineConfig instance = new ParseEngineConfig();
        instance.toXMLArquivoDeConfiguracaoGeral(configuracaoGeral);
        File configFile = new File(EnumDiretorio.Configuracao.getDiretorio()
                .concat(File.separator).concat(EnumArquivos.Configuracao.getNomeDoArquivo()));

        assertTrue(configFile.length() > 0);*/
    }

    /**
     * Test of fromXMLListaDeConfiguracoesFTP method, of class
     * ParseEngineConfig.
     */
    @Test
    public void testFromXMLListaDeConfiguracoesFTP() {
        ParseEngineConfig instance = new ParseEngineConfig();
        String conteudo = "<listaDeConfiguracoes>\n"
                + "<configuracao>\n"
                + "    <host>teste</host>\n"
                + "    <usuario>teste</usuario>\n"
                + "    <senha>teste</senha>\n"
                + "    <cnpj>teste</cnpj>\n"
                + "</configuracao>\n"
                + "</listaDeConfiguracoes>";

        FileOutputStream fops = null;
    try {
        fops = new FileOutputStream(testFromXMLListaDeConfiguracoesFTP);
        fops.write(conteudo.getBytes());
        fops.flush();
        
        assertTrue("teste".equals(instance.fromXMLListaDeConfiguracoesFTP(testFromXMLListaDeConfiguracoesFTP).get(0).getCnpj()));

    } catch (FileNotFoundException ex) {
        System.out.println(ex.getMessage());
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    } finally{
        if(fops != null){
            try {
                fops.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
       

    }

    /**
     * Test of validarParseFromXML method, of class ParseEngineConfig.
     */
    @Test
    public void testValidarParseFromXML() {
        List<Config> listaDeConfiguracoesCarregadas = new ArrayList<Config>();
        Config config = new Config();
        config.setCnpj("1");
        Config configIgual = new Config();
        configIgual.setCnpj("1");
        listaDeConfiguracoesCarregadas.add(config);
        listaDeConfiguracoesCarregadas.add(configIgual);
        
        ParseEngineConfig instance = new ParseEngineConfig();

        instance.validarParseFromXML(listaDeConfiguracoesCarregadas);
        assertEquals(false, instance.validarParseFromXML(listaDeConfiguracoesCarregadas));
    }
    /**
     * Test of fromXMLConfiguracaoGeral method, of class ParseEngineConfig.
     */
    @Test
    public void testFromXMLConfiguracaoGeral() {
        ParseEngineConfig instance = new ParseEngineConfig();
        String conteudo = "<configuracaoGeral>\n"
                +"<diretorioDeEnvio>envio</diretorioDeEnvio>\n"
                +"<diretorioDeRetorno>retorno</diretorioDeRetorno>\n"
                +"<listaDeConfiguracoes>\n"
                + "<configuracao>\n"
                + "    <host>teste</host>\n"
                + "    <usuario>teste</usuario>\n"
                + "    <senha>teste</senha>\n"
                + "    <cnpj>teste</cnpj>\n"
                + "</configuracao>\n"
                + "</listaDeConfiguracoes>\n"
                + "</configuracaoGeral>";

        FileOutputStream fops = null;
     try {
        fops = new FileOutputStream(testFromXMLListaDeConfiguracoesFTP);
        fops.write(conteudo.getBytes());
        fops.flush();
       
        
         assertTrue("envio".equals(instance.fromXMLConfiguracaoGeral(testFromXMLListaDeConfiguracoesFTP).getDiretorioDeEnvio()));
        

    } catch (FileNotFoundException ex) {
        System.out.println(ex.getMessage());
    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    } finally{
        if(fops != null){
            try {
                fops.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    }
}
