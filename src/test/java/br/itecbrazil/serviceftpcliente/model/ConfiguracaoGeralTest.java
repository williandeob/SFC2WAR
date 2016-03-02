/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.pedido.api.ftp.Config;
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
public class ConfiguracaoGeralTest {
   
    public ConfiguracaoGeralTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of isPopulado method, of class ConfiguracaoGeral.
     */
    @Test
    public void testIsPopulado() {
        Config config = new Config("host", "usuario", "senha"); 
        List<Config> listaDeConfiguracoes = new ArrayList<Config>();
        listaDeConfiguracoes.add(config);
        
        ConfiguracaoGeral instance = new ConfiguracaoGeral("diretorioDeEnvio", "diretorioDeRetorno", listaDeConfiguracoes);
        
        assertEquals(true, instance.isPopulado());
    }
    
     /**
     * Test of isPopulado method, of class ConfiguracaoGeral.
     */
    @Test
    public void testIsNotPopulado() {
        List<Config> listaDeConfiguracoes = new ArrayList<Config>();
        ConfiguracaoGeral instance = new ConfiguracaoGeral("diretorioDeEnvio", "diretorioDeRetorno", listaDeConfiguracoes);
        assertEquals(false, instance.isPopulado());
    }

    /**
     * Test of buscarConfiguracoPorCNPJ method, of class ConfiguracaoGeral.
     */
    @Test
    public void testBuscarConfiguracoPorCNPJ() {
        Config config = new Config("host", "usuario", "senha");
        config.setCnpj("cnpj");
        List<Config> listaDeConfiguracoes = new ArrayList<Config>();
        listaDeConfiguracoes.add(config);
        
        ConfiguracaoGeral instance = new ConfiguracaoGeral("diretorioDeEnvio", "diretorioDeRetorno", listaDeConfiguracoes);
        
        assertEquals(config, instance.buscarConfiguracoPorCNPJ("cnpj"));
    }

    /**
     * Test of adicionarNovasConfiguracoes method, of class ConfiguracaoGeral.
     */
    @Test
    public void testAdicionarNovasConfiguracoes() {
        Config config = new Config("host", "usuario", "senha");
        config.setCnpj("cnpj");
        List<Config> listaDeConfiguracoes = new ArrayList<Config>();
        listaDeConfiguracoes.add(config);
        
        ConfiguracaoGeral instance = new ConfiguracaoGeral("diretorioDeEnvio", "diretorioDeRetorno", listaDeConfiguracoes);
         
        Config configASerIncluido = new Config();
        configASerIncluido.setCnpj("novoCnpj");
        List<Config> listaDeNovasConfiguracoes = new ArrayList<Config>();
        listaDeNovasConfiguracoes.add(configASerIncluido);
        
        assertEquals(true, instance.adicionarNovasConfiguracoes(listaDeNovasConfiguracoes));
    }
    
}
