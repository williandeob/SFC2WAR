/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import java.util.List;

/**
 *
 * @author itec-desenv-willian
 */
public class ConfiguracaoGeral {

    private String diretorioDeEnvio;
    private String diretorioDeRetorno;
    private List<Config> listaDeConfiguracoes;

    public ConfiguracaoGeral() {
    }

    public ConfiguracaoGeral(String diretorioDeEnvio, String diretorioDeRetorno, List<Config> listaDeConfiguracoes) {
        this.diretorioDeEnvio = diretorioDeEnvio;
        this.diretorioDeRetorno = diretorioDeRetorno;
        this.listaDeConfiguracoes = listaDeConfiguracoes;
    }

    /**
     * Caminho do diretório responsavel por armazenar os arquivos a serem
     * enviados aos FTPs.
     *
     * @author willian
     * @return diretorioDeEnvio
     */
    public String getDiretorioDeEnvio() {
        return diretorioDeEnvio;
    }

    public void setDiretorioDeEnvio(String diretorioDeEnvio) {
        this.diretorioDeEnvio = diretorioDeEnvio;
    }

    public String getDiretorioDeRetorno() {
        return diretorioDeRetorno;
    }

    /**
     * Caminho do diretório responsavel por armazenar os arquivos recebidos dos
     * servidores FTPs.
     *
     * @author willian
     * @param diretorioDeRetorno
     */
    public void setDiretorioDeRetorno(String diretorioDeRetorno) {
        this.diretorioDeRetorno = diretorioDeRetorno;
    }

    public List<Config> getListaDeConfiguracoes() {
        return listaDeConfiguracoes;
    }

    public void setListaDeConfiguracoes(List<Config> listaDeConfiguracoes) {
        this.listaDeConfiguracoes = listaDeConfiguracoes;
    }

    public boolean isPopulado() {
        return !(getDiretorioDeEnvio().isEmpty() || getDiretorioDeRetorno().isEmpty()
                || getListaDeConfiguracoes().isEmpty());
    }

    public Config buscarConfiguracoPorCNPJ(String cnpjDoClienteLigadoAoFTP) {
        for (Config config : getListaDeConfiguracoes()) {
            if (config.getCnpj().equals(cnpjDoClienteLigadoAoFTP)) {
                return config;
            }
        }
        return null;
    }

    /**
     * Metodo verifica se na lista passada por parametro contem Config com o
     * mesmo valor de CNPJ de Config já cadastradas, caso contenha alguma Config
     * com o mesmo CNPJ de outra já cadastrada, as Config presentes na lista
     * passada por parametro não são adicionadas.
     *
     * @param listaComConfiguracoesASeremAdicionadas
     * @return true se todos os Config presentes na lista passada por parametro
     * conter valores de CNPJ distintos das Config já cadastradas na lista de
     * configuracaoGeral
     */
    public boolean adicionarNovasConfiguracoes(List<Config> listaComConfiguracoesASeremAdicionadas) {
        int index = 0;
        boolean naoHouveCNPJIgual = true;

        while (index < listaComConfiguracoesASeremAdicionadas.size()) {
            Config config = listaComConfiguracoesASeremAdicionadas.get(index);
            for (int i = 0; i < getListaDeConfiguracoes().size(); i++) {
                if (config.getCnpj().equals(getListaDeConfiguracoes().get(i).getCnpj())) {
                    naoHouveCNPJIgual = false;
                }
            }
            index++;
        }

        if (naoHouveCNPJIgual) {
            for (Config config : listaComConfiguracoesASeremAdicionadas) {
                getListaDeConfiguracoes().add(config);
            }
        }

        return naoHouveCNPJIgual;

    }
}
