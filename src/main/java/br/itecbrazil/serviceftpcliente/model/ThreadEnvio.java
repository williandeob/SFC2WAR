/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;
import br.itecbrazil.serviceftpcliente.controller.ControllerDashBoardEnvioRetorno;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

/**
 *
 * @author itec-desenv-willian
 */
public class ThreadEnvio implements Runnable {

    private Config config;
    private StringBuilder coteudoFileTransfer;
    private ControllerDashBoardEnvioRetorno controller;
    private static Logger logger = Logger.getLogger("Envio");
    private static Logger loggerExceptionEnvio = Logger.getLogger("EnvioException");
    

    public ThreadEnvio(Config config, ControllerDashBoardEnvioRetorno controller) {
        this.config = config;
        this.controller = controller;
        coteudoFileTransfer = new StringBuilder();
    }

    public Config getConfig() {
        return config;
    }
    
     public StringBuilder getCoteudoFileTransfer() {
        return coteudoFileTransfer;
    }

    public ControllerDashBoardEnvioRetorno getController() {
        return controller;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void run() {
        List<File> listaDeArquivosParaEnvio;
        logger.info("Thread " + Thread.currentThread().getName() + "verificando envio para fornecedor de cnpj " + getConfig().getCnpj()+" iniciado");

        if (getConfig() == null) {
            logger.info("Ocorreu um erro ao iniciar o processo de envio, configuracao não encontrada para a thread " + Thread.currentThread().getName());    
        } else {
            logger.info("Thread " + Thread.currentThread().getName()+ "em execução");
            listaDeArquivosParaEnvio = buscarArquivoDiretorioLocalDeEnvio(MainServiceFTPCliente.getConfiguracaoGeral().getDiretorioDeEnvio());

            if (listaDeArquivosParaEnvio != null && !listaDeArquivosParaEnvio.isEmpty()) {
                logger.info("Arquivo " + listaDeArquivosParaEnvio.get(0).getName() +" carregado para envio. Thread: " + Thread.currentThread().getName());
                enviarArquivo(listaDeArquivosParaEnvio.get(0), getConfig());
            }

            logger.info("Thread " + Thread.currentThread().getName() + " executada para fornecedor de cnpj " + getConfig().getCnpj() + " finalizada");
        }
    }
    
    /**
     * Acessa o diretorio e eretorna os arquivo encontrados no mesmo
     * @param pathDiretorioDeEnvio caminho do diretorio manipulado
     * @return List<File> de arquivos encontados
     */
    private List<File> buscarArquivoDiretorioLocalDeEnvio(String pathDiretorioDeEnvio) {
        if (pathDiretorioDeEnvio == null || pathDiretorioDeEnvio.trim().isEmpty()) {
            logger.info("Não foi possivel encontrar o caminho de envio, diretorio nulo ou vazio. Thread: " + Thread.currentThread().getName());
            return null;
        }

        File diretorioDeEnvio = new File(pathDiretorioDeEnvio);
        List<File>listaDeArquivos = new ArrayList<>();
        for(File file : diretorioDeEnvio.listFiles()){
            if(file.isFile()){
                listaDeArquivos.add(file);
            }
        }

        if (listaDeArquivos.isEmpty()) {
            logger.info("Não foi encontrado nenhum arquivo de envio para a thread - " + Thread.currentThread().getName());
        }
        
        return listaDeArquivos;
    }

    private boolean gravarBackup(File arquivo) {
        logger.info("Gravando backup do arquivo "+arquivo.getName()+". Thread: " + Thread.currentThread().getName());
        
        String pathDoBackUp = arquivo.getParent().concat(File.separator).concat("backup");
        File diretorio = new File(pathDoBackUp);
        FileReader fr = null;
        BufferedReader br = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        
        try{ 
            if(!diretorio.exists()){
                diretorio.mkdir();
            }
            
            File arquivoBackUp = new File(pathDoBackUp.concat(File.separator).concat(arquivo.getName()));
            
            fr = new FileReader(arquivo);
            br = new BufferedReader(fr);
            fw = new FileWriter(arquivoBackUp);
            bw = new BufferedWriter(fw);
            
            String linha = br.readLine();
            while(linha != null){
                bw.write(linha);
                bw.newLine();
                bw.flush();
                coteudoFileTransfer.append(linha);
                linha = br.readLine();
            }
        } catch (FileNotFoundException ex) {
            loggerExceptionEnvio.info(ex);
            return false;
        } catch (IOException ex) {
            loggerExceptionEnvio.info(ex);
            return false;
        }finally{
            try {
                if(fr != null)
                    fr.close();
                if(br != null)
                    br.close();
            } catch (IOException ex) {
                loggerExceptionEnvio.info(ex);
                return false;
            }
        }
 
        return true;
    }

    private void enviarArquivo(File arquivo, Config config){
        if(gravarBackup(arquivo)){
            logger.info("Enviando arquivo "+arquivo.getName()+". Thread: " + Thread.currentThread().getName());
            try {
                HttpClient httpclient = new org.apache.http.impl.client.DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://pedido.2war.com.br/upload");
                httppost.setHeader("X-Requested-With", "XMLHttpRequest");

                MultipartEntity mpEntity = new MultipartEntity();
                mpEntity.addPart("id", new StringBody("3"));
                mpEntity.addPart("arquivo", new FileBody(arquivo));

                httppost.setEntity(mpEntity);
                System.out.println("executing request " + httppost.getRequestLine());
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity resEntity = response.getEntity();
                
                if(response.getStatusLine().getStatusCode() == 200){
                    logger.info("Arquivo arquivo "+arquivo.getName()+" enviado com sucesso. Thread: " + Thread.currentThread().getName());
                    arquivo.delete();
                    logger.info("Arquivo deletado "+arquivo.getName()+" do diretorio de envio. Thread: " + Thread.currentThread().getName());
                }
                
            } catch (MalformedURLException ex) {
                loggerExceptionEnvio.info(ex);
            } catch (IOException ex) {
                loggerExceptionEnvio.info(ex);
            }         
        }
    }
}
