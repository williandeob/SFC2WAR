/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
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
    private static Logger logger = Logger.getLogger("Busca");
    private static Logger loggerExceptionRetorno = Logger.getLogger("BuscaException");
    

    public ThreadRetorno(Config config) {
        this.config = config;
    }

    public Config getConfig() {
        return config;
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
                HttpGet httpget = new HttpGet("http://"+config.getHost()+"/download/"+config.getUsuario());
                httpget.setHeader("X-Requested-With", "XMLHttpRequest");

                System.out.println("executing request " + httpget.getRequestLine());
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity resEntity = response.getEntity();

                System.out.println(response.getStatusLine().getStatusCode());
                    
                if(response.getStatusLine().getStatusCode() == 200 && response.getFirstHeader("Content-Disposition")!= null){
                    String conteudo = inputStreamToString(resEntity.getContent());
                    String nomeArquivo = response.getFirstHeader("Content-Disposition").getValue().split("=")[1];
                    escreverArquivosNoDiretorioPassadoPorParametro(conteudo, MainServiceFTPCliente.getConfiguracaoGeral().getDiretorioDeRetorno(), nomeArquivo);
                }else{  
                    logger.info("Falha na busca do arquivo: Código: "+response.getStatusLine().getStatusCode()+" "
                            + "Content-Disposition "+response.getFirstHeader("Content-Disposition"));
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
    private void escreverArquivosNoDiretorioPassadoPorParametro(String conteudo, String pathDiretorio, String nomeArquivo) {
        if (pathDiretorio == null || pathDiretorio.trim().isEmpty()) {
            logger.info("Thread " + Thread.currentThread().getName() + " diretório local de escrita não configurado");
        }
        try{
            
            if(!conteudo.isEmpty()){
                File arquivo = new File(pathDiretorio, nomeArquivo);
                logger.info("Thread " + Thread.currentThread().getName() + " gravando arquivo baixado "+arquivo.getName()+" no diretório de retorno");
                FileWriter fw = new FileWriter(arquivo);
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(conteudo);
                    bw.flush();
                }
                
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
        StringBuilder total = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            String line = rd.readLine();
            if(line.contains("\"success\":false")){
                return "";
            }else{
                total.append(line);
                total.append("\r\n");
            }
                
            while((line = rd.readLine()) != null){
                total.append(line);
                total.append("\r\n");
            }
        } catch (IOException ex) {
            loggerExceptionRetorno.info(ex);
        }

        return total.toString();
    }

}
