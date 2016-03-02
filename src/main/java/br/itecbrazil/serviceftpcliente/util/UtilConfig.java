/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.util;

import br.itecbrazil.pedido.api.ftp.Config;
import br.itecbrazil.serviceftpcliente.MainServiceFTPCliente;
import br.itecbrazil.serviceftpcliente.enums.EnumArquivos;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author itec-desenv-willian
 */
public class UtilConfig {

    private UtilConfig() {
    }

    /**
     * Metodo responsável por limpar o objeto de ConfiguracaoGeral.
     */
    public static void limparConfiguracao() {
        List<Config> listaVazia = new ArrayList<Config>();
        MainServiceFTPCliente.getConfiguracaoGeral().setDiretorioDeEnvio("");
        MainServiceFTPCliente.getConfiguracaoGeral().setDiretorioDeRetorno("");
        MainServiceFTPCliente.getConfiguracaoGeral().setListaDeConfiguracoes(listaVazia);

        File diretorioConfiguracao = UtilDiretorios.getDiretorioDeConfiguracao();
        File[] arquivoEncontradosNoDiretorioDeConfiguracao = diretorioConfiguracao.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        });

        for (File arquivosRetornados : arquivoEncontradosNoDiretorioDeConfiguracao) {
            if (arquivosRetornados.getName().equals(EnumArquivos.Configuracao.getNomeDoArquivo()) && arquivosRetornados.isFile()) {
                arquivosRetornados.delete();
            }
        }

    }
}
