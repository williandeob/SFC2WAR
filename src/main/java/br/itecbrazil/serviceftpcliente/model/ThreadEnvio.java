/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.pedido.api.ftp.Config;
import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;
import java.util.Arrays;
import org.apache.log4j.Logger;
import br.itecbrazil.pedido.api.ftp.ProcessaFTP;
import br.itecbrazil.serviceftpcliente.controller.ControllerDashBoardEnvioRetorno;
import br.itecbrazil.serviceftpcliente.util.UtilDiretorios;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

/**
 *
 * @author itec-desenv-willian
 */
public class ThreadEnvio implements Runnable {

    private ProcessaFTP processaEnvio;
    private final Config config;
    public static Logger logger = Logger.getLogger("EnvioFTP");
    public static Logger loggerExceptionEnvio = Logger.getLogger("EnvioFTPException");
    private ControllerDashBoardEnvioRetorno controller;

    public ThreadEnvio(Config config, ControllerDashBoardEnvioRetorno controller) {
        this.config = config;
        this.controller = controller;
    }

    public Config getConfig() {
        return config;
    }

    public ProcessaFTP getProcessaEnvio() {
        return processaEnvio;
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

        if (getConfig() == null) {

            logger.info("Ocorreu um erro ao iniciar o processo de Envio, configuracao não encontrada - " + Thread.currentThread().getName());

        } else {

            logger.info("Thread em Execucao - " + Thread.currentThread().getName() + " Cliente de CNPJ " + getConfig().getCnpj());

            listaDeArquivosParaEnvio = buscarArquivoDiretorioLocalDeEnvio(MainServiceFTPCliente.configuracaoGeral.getDiretorioDeEnvio());

            if (listaDeArquivosParaEnvio != null && !listaDeArquivosParaEnvio.isEmpty()) {

                logger.info("Processando " + listaDeArquivosParaEnvio.size() + " Arquivo(s) na Thread " + Thread.currentThread().getName() + " Cliente de CNPJ " + getConfig().getCnpj());

                if (connectarFTP()) {
                    if (commitDiretorioFTP(listaDeArquivosParaEnvio, getConfig().getDirFornFtpBackUp())) {
                        if (commitDiretorioFTP(listaDeArquivosParaEnvio, getConfig().getDirFornFtpReader())) {
                            UtilDiretorios.deletarArquivos(listaDeArquivosParaEnvio);
                        }
                    }

                    try {
                        getProcessaEnvio().logout();
                    } catch (Exception ex) {
                        loggerExceptionEnvio.info(Thread.currentThread().getName() + " cliente " + getConfig().getCnpj() + " erro logout - " + ex);
                    }
                }

            }

            logger.info("Thread " + Thread.currentThread().getName() + " Cliente de CNPJ " + getConfig().getCnpj() + " finalizada");

        }
    }

    private List<File> buscarArquivoDiretorioLocalDeEnvio(String pathDiretorioDeEnvio) {
        if (pathDiretorioDeEnvio == null || pathDiretorioDeEnvio.trim().isEmpty()) {

            logger.info("Não foi possivel encontrar o caminho de envio, pathDiretorioDeEnvio nulo ou vazio - " + Thread.currentThread().getName() + " Cliente de CNPJ " + getConfig().getCnpj());

            return null;
        }

        File diretorioDeEnvio = new File(pathDiretorioDeEnvio);
        File[] arquivoEncontradosNoDiretorioDeEnvio = diretorioDeEnvio.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.contains(getConfig().getCnpj());
            }
        });

        if (arquivoEncontradosNoDiretorioDeEnvio.length == 0) {

            logger.info("Não foi encontrado nenhum arquivo de envio para a Thread - " + Thread.currentThread().getName() + " Cliente de CNPJ " + getConfig().getCnpj());

        }

        return Arrays.asList(arquivoEncontradosNoDiretorioDeEnvio);

    }

    private boolean connectarFTP() {
        try {
            processaEnvio = new ProcessaFTP(getConfig());

            logger.info("Thread " + Thread.currentThread().getName() + " conectou no FTP do Cliente " + getConfig().getCnpj());

            return true;
        } catch (Exception ex) {
            logger.info("Thread " + Thread.currentThread().getName() + " não se conectou ao FTP do cliente " + getConfig().getCnpj());
            loggerExceptionEnvio.info(Thread.currentThread().getName() + " cliente " + getConfig().getCnpj() + " " + ex);
        }

        return false;
    }

    private boolean commitDiretorioFTP(List<File> listaDeArquivos, String pathDiretorio) {

        if (getProcessaEnvio().checkDirectoryExists(pathDiretorio)) {
            for (File arquivo : listaDeArquivos) {
                BufferedReader br = null;
                OutputStream os = null;
                try {
                    br = new BufferedReader(new FileReader(arquivo));
                    String linha = br.readLine();
                    String conteudo = "";
                    while (linha != null) {
                        conteudo += linha;
                        linha = br.readLine();
                    }
                    os = getProcessaEnvio().getFtpClient().storeFileStream(arquivo.getName());
                    os.write(conteudo.getBytes());

                } catch (FileNotFoundException ex) {
                    loggerExceptionEnvio.info(Thread.currentThread().getName() + " cliente " + getConfig().getCnpj() + " " + ex);
                    return false;
                } catch (IOException ex) {
                    loggerExceptionEnvio.info(Thread.currentThread().getName() + " cliente " + getConfig().getCnpj() + " " + ex);
                    return false;
                } finally {
                    try {
                        if (br != null) {
                            br.close();
                        }
                        if (os != null) {
                            os.flush();
                            os.close();
                            if (getProcessaEnvio().getFtpClient().completePendingCommand()) {

                                logger.info("Thread " + Thread.currentThread().getName() + " gravou o arquivo " + arquivo.getName() + " no diretório " + pathDiretorio + " FTP do Cliente " + getConfig().getCnpj());

                                if (pathDiretorio.equals(getConfig().getDirFornFtpReader())) {
                                    getController().atualizarLogsDeEnvio(getConfig().getHost(), pathDiretorio, arquivo.getName());
                                }
                            }

                        }
                    } catch (Exception ex) {
                        loggerExceptionEnvio.info(Thread.currentThread().getName() + " cliente " + getConfig().getCnpj() + " " + ex);
                    }
                }
            }

            return true;
        } else {

            logger.info("Thread " + Thread.currentThread().getName() + " não encontrou o diretorio " + pathDiretorio + " do FTP do cliente " + getConfig().getCnpj());

            return false;
        }

    }

}
