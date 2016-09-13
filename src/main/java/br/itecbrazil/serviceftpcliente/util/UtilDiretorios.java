/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.util;

import br.itecbrazil.serviceftpcliente.enums.EnumDiretorio;
import java.io.File;
import java.util.Collection;

/**
 *
 * @author itec-desenv-willian
 *
 * Classe que possui metodos auxiliares disponiveis para todo o servico.
 */
public class UtilDiretorios {

    private UtilDiretorios() {

    }

    /**
     * Metodo que retorna o diretorio que armazena o arquivo de configuracao.
     *
     * @author willian
     * @return File
     */
    public static File getDiretorioDeConfiguracao() {
        return criarDiretorio(EnumDiretorio.Configuracao.getDiretorio());
    }

    public static File criarDiretorio(String diretorio) {
        File directoryFile;
        directoryFile = new File(diretorio);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }

        directoryFile.setWritable(true);
        directoryFile.setReadable(true);

        return directoryFile;
    }

    public static void deletarArquivo(File arquivo) {
        if (arquivo != null) {
            arquivo.delete();
        }
    }

    public static void deletarArquivos(Collection<File> listaDeArquivos) {
        if (listaDeArquivos != null) {
            for (File arquivo : listaDeArquivos) {
                deletarArquivo(arquivo);
            }
        }
    }

}
