/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.pedido.api.ftp.Config;
import br.itecbrazil.pedido.api.ftp.ProcessaFTP;
import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.controller.ControllerDashBoardEnvioRetorno;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

/**
 *
 * @author itec-desenv-willian
 */
public class ThreadRetorno implements Runnable {

    private ProcessaFTP processaRetorno;
    private final Config config;
    public static Logger logger = Logger.getLogger("BuscaFTP");
    public static Logger loggerExceptionRetorno = Logger.getLogger("BuscaFTPException");
    private ControllerDashBoardEnvioRetorno controller;

    public ThreadRetorno(Config config, ControllerDashBoardEnvioRetorno controller) {
        this.config = config;
        this.controller = controller;
    }

    public Config getConfig() {
        return config;
    }

    public ProcessaFTP getProcessaRetorno() {
        return processaRetorno;
    }

    public ControllerDashBoardEnvioRetorno getController() {
        return controller;
    }

    public static Logger getLogger() {
        return logger;
    }

    @Override
    public void run() {
        List<FTPFile> listaDeFTPArquivosCarregados;

        if (getConfig() == null) {
         
            logger.info("Ocorreu um erro ao iniciar o processo de Retorno, configuracao não encontrada - " + Thread.currentThread().getName());
            
        } else {
            if (conectarFTP()) {
                listaDeFTPArquivosCarregados = buscarArquivosNoDiretorioDoFTP(getConfig().getDirFornFtpWriter());
                if (listaDeFTPArquivosCarregados != null && !listaDeFTPArquivosCarregados.isEmpty()) {
                    if (escreverArquivosNoDiretorioPassadoPorParametro(listaDeFTPArquivosCarregados, MainServiceFTPCliente.configuracaoGeral.getDiretorioDeRetorno())) {
                        deletarArquivosJaCarregadosDoDiretorioDoFTP(listaDeFTPArquivosCarregados);
                    }
                }
                try {
                    getProcessaRetorno().logout();
                } catch (Exception ex) {
                    loggerExceptionRetorno.info(Thread.currentThread().getName()+" cliente "+ getConfig().getCnpj()+" "+ex);
                    ex.printStackTrace();
                }
            }
        }

       
        logger.info("Thread " + Thread.currentThread().getName() + " conectada ao Cliente de CNPJ " + getConfig().getCnpj() + " finalizada");
        

    }

    private boolean conectarFTP() {
        try {
            processaRetorno = new ProcessaFTP(getConfig());
            
            logger.info("Thread " + Thread.currentThread().getName() + " conectou no FTP do Cliente " + getConfig().getCnpj());
            
            return true;
        } catch (Exception ex) {
            loggerExceptionRetorno.info(Thread.currentThread().getName()+" cliente "+ getConfig().getCnpj()+" "+ex);           
            ex.printStackTrace();
        }
       
        logger.error("Thread " + Thread.currentThread().getName() + " não se conectou ao FTP do cliente " + getConfig().getCnpj());
        
        return false;
    }

    private List<FTPFile> buscarArquivosNoDiretorioDoFTP(String pathDiretorioFTP) {
        if (pathDiretorioFTP == null || pathDiretorioFTP.trim().isEmpty()) {
           
            logger.info("Não foi possivel encontrar o caminho do diretorio de busca do FTP, o mesmo está nulo ou vazio - " + Thread.currentThread().getName() + " Cliente de CNPJ " + getConfig().getCnpj());
           
            return null;
        }

        if (getProcessaRetorno().checkDirectoryExists(pathDiretorioFTP)) {

            try {
                FTPFile[] listaDeArquivosFTPEncontrados;
                List<FTPFile> listaFTPFileReturn = new ArrayList<FTPFile>();
                listaDeArquivosFTPEncontrados = getProcessaRetorno().getFtpClient().listFiles();
                for (FTPFile listaDeArquivosFTPEncontrado : listaDeArquivosFTPEncontrados) {
                    if (listaDeArquivosFTPEncontrado.getType() == FTPFile.FILE_TYPE) {
                        listaFTPFileReturn.add(listaDeArquivosFTPEncontrado);
                    }
                }
                if (listaFTPFileReturn.isEmpty()) {
                  
                    logger.info("Thread " + Thread.currentThread().getName() + " não encontrou nenhum arquivo no diretorio de busca no FTP do Cliente " + getConfig().getCnpj());
                    
                }

                return listaFTPFileReturn;
            } catch (Exception ex) {
                loggerExceptionRetorno.info(Thread.currentThread().getName()+" cliente "+ getConfig().getCnpj()+" "+ex);
                ex.printStackTrace();
            }
        }
        
        logger.info("A Thread " + Thread.currentThread().getName() + " não encontrou o diretorio de busca do fTP do Cliente de CNPJ " + getConfig().getCnpj());
        
        return null;
    }

    /**
     *
     * @param listaDeArquivos
     * @param pathDiretorio
     * @return se todos os aquivos a serem escritos no diretorio passado por
     * parametro forem escritos com sucesso retorna true, caso algum ou nenhuma
     * desses arquivos sejam escritos o metodo retorna false.
     */
    private boolean escreverArquivosNoDiretorioPassadoPorParametro(List<FTPFile> listaDeArquivos, String pathDiretorio) {
        if (pathDiretorio == null || pathDiretorio.trim().isEmpty()) {
            return false;
        }

       
        logger.info("Thread " + Thread.currentThread().getName() + " encontrou no diretorio de busca " + listaDeArquivos.size() + " arquivo(s) para downloading no FTP do Clente " + getConfig().getCnpj());
       

        boolean retorno = true;
        boolean result;

        for (FTPFile ftpFile : listaDeArquivos) {
            if (ftpFile.getType() == FTPFile.FILE_TYPE) {
                OutputStream ops = null;
                try {

                    File file = new File(pathDiretorio.concat(File.separator).concat(ftpFile.getName()));
                    ops = new BufferedOutputStream(new FileOutputStream(file));
                    result = getProcessaRetorno().getFtpClient().retrieveFile(ftpFile.getName(), ops);

                    if (result) {
                        
                        logger.info("Thread " + Thread.currentThread().getName() + " baixou o arquivo " + ftpFile.getName() + " no FTP do Clente" + getConfig().getCnpj());
                        
                        getController().atualizarLogsDeRetorno(getConfig().getHost(), pathDiretorio, ftpFile.getName());

                        if (!getProcessaRetorno().getFtpClient().rename(ftpFile.getName(), getConfig().getDirFornFtpBackUpWriter()
                                .concat(File.separator).concat(ftpFile.getName()))) {
                            getProcessaRetorno().getFtpClient().rename(ftpFile.getName(), getConfig().getDirFornFtpBackUpWriter()
                                    .concat("/").concat(ftpFile.getName()));
                        }

                    } else {
                        
                        logger.info("Thread " + Thread.currentThread().getName() + " falhou ao baixar o arquivo " + ftpFile.getName() + " no FTP do Clente " + getConfig().getCnpj());
                       
                        retorno = false;
                    }

                } catch (Exception ex) {
                   loggerExceptionRetorno.info(Thread.currentThread().getName()+" cliente "+ getConfig().getCnpj()+" "+ex);
                   ex.printStackTrace();
                } finally {
                    if (ops != null) {
                        try {
                            ops.flush();
                            ops.close();

                        } catch (Exception ex) {
                            loggerExceptionRetorno.info(Thread.currentThread().getName()+" cliente "+ getConfig().getCnpj()+" "+ex);
                           // java.util.logging.Logger.getLogger(ThreadRetorno.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

        return retorno;
    }

    private void deletarArquivosJaCarregadosDoDiretorioDoFTP(List<FTPFile> listaDeArquivos) {
        if (listaDeArquivos == null || listaDeArquivos.isEmpty()) {
            return;
        }

        for (FTPFile ftpFile : listaDeArquivos) {
            if (ftpFile.getType() == FTPFile.FILE_TYPE) {
                try {
                    if (getProcessaRetorno().getFtpClient().deleteFile(ftpFile.getName())) {
                       
                        logger.info("Thread " + Thread.currentThread().getName() + " excluiu o arquivo " + ftpFile.getName() + " do FTP do Clente " + getConfig().getCnpj());
                        
                    }
                } catch (Exception ex) {
                   loggerExceptionRetorno.info(Thread.currentThread().getName()+" cliente "+ getConfig().getCnpj()+" "+ex);
                   ex.printStackTrace();
                }
            }

        }
    }

}
