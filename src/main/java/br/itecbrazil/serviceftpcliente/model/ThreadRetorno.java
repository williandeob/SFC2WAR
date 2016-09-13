/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.controller.ControllerDashBoardEnvioRetorno;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

/**
 *
 * @author itec-desenv-willian
 */
public class ThreadRetorno implements Runnable {

    private Config config;
    private ControllerDashBoardEnvioRetorno controller;
    private static Logger logger = Logger.getLogger("Busca");
    private static Logger loggerExceptionRetorno = Logger.getLogger("BuscaException");
    

    public ThreadRetorno(Config config, ControllerDashBoardEnvioRetorno controller) {
        this.config = config;
        this.controller = controller;
    }

    public Config getConfig() {
        return config;
    }

    public ControllerDashBoardEnvioRetorno getController() {
        return controller;
    }

    @Override
    public void run() {
        logger.info("Thread " + Thread.currentThread().getName() + "verificando retorno do fornecedor de cnpj " + getConfig().getCnpj()+ " iniciada");
        if (getConfig() == null) {      
            logger.info("Ocorreu um erro ao iniciar o processo de retorno, configuracao não encontrada para a " + Thread.currentThread().getName());  
        } else {            
            donwload();
        }
        logger.info("Thread " + Thread.currentThread().getName() + " conectada ao fornecedor de cnpj " + getConfig().getCnpj() + " finalizada");
    }

    /**
     * Conecta no servidor e verifica se tem arquivos para ser baixados
     * caso tenha arquivo ele baixa e manda grava
     * @see escreverArquivosNoDiretorioPassadoPorParametro
     * @return 
     */
    private void donwload() {
        logger.info("Thread " + Thread.currentThread().getName() + " iniciando conexao com o servidor");
        InputStream is = null;
         try {

                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet("http://pedido.2war.com.br/download/1");
                httpget.setHeader("X-Requested-With", "XMLHttpRequest");

                System.out.println("executing request " + httpget.getRequestLine());
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity resEntity = response.getEntity();

                System.out.println(response.getStatusLine().getStatusCode());
                    
                if(response.getStatusLine().getStatusCode() == 200){
                    String conteudo = inputStreamToString(resEntity.getContent());
                    escreverArquivosNoDiretorioPassadoPorParametro(conteudo, MainServiceFTPCliente.getConfiguracaoGeral().getDiretorioDeRetorno());
                }else{  
                    logger.info("Erro requisicao código: "+response.getStatusLine().getStatusCode());
                }
                    
            } catch (MalformedURLException ex) {
                loggerExceptionRetorno.info(ex);
            } catch (IOException ex) {
                loggerExceptionRetorno.info(ex);
            }    
        
    }
    
    /**
     * Grava arquivos no diretorio desejado
     * @param arquivo Flle a ser gravado
     * @param pathDiretorio diretorio onde o arquivo vai ser gravado
     */
    private void escreverArquivosNoDiretorioPassadoPorParametro(String conteudo, String pathDiretorio) {
        if (pathDiretorio == null || pathDiretorio.trim().isEmpty()) {
            logger.info("Thread " + Thread.currentThread().getName() + " diretório local de escrita não configurado");
        }
        try{
            
            if(!conteudo.isEmpty()){
                File arquivo = new File("C:/Users/willian/towwar/ServiceFTPCliente/Retorno/Retorno.txt");
                logger.info("Thread " + Thread.currentThread().getName() + " gravando arquivo baixado "+arquivo.getName()+" no diretório de retorno");
                FileWriter fw = new FileWriter(arquivo);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(conteudo);
                bw.flush();
                bw.close();
                
            }
            
        }catch(IOException ex){
            loggerExceptionRetorno.info(ex);
        }

    }
    
    /**
     * Parse do conteudo do stream para texto
     * @param is
     * @return 
     */
     private String inputStreamToString(InputStream is) {
        String line;
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        
        try {
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                total.append(line);
                total.append("\r\n");
            }
        } catch (IOException e) {
           
        }
        
        if(total.toString().contains("\"success\":false")){
            return "";
        }
        return total.toString();
    }

}
