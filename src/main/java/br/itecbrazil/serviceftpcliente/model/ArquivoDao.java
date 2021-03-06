/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.model;

import br.itecbrazil.serviceftpcliente.enums.EnumArquivos;
import br.itecbrazil.serviceftpcliente.enums.EnumDiretorio;
import br.itecbrazil.serviceftpcliente.enums.EnumTipoArquivo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author willian
 */
public class ArquivoDao {
    public ArquivoDao(){
        super();
    }
    
    public List<Arquivo> getArquivos(int tipo) throws FileNotFoundException{
        List<Arquivo> arquivos;
        if(tipo == EnumTipoArquivo.Envio.getTipoDoArquivo()){
            arquivos = getEnvio();
        }else if (tipo == EnumTipoArquivo.Retorno.getTipoDoArquivo()){
             arquivos = getRetorno();
        }else{
            throw new IllegalArgumentException("Tipo de arquivo "+tipo+" inválido");
        }
        return arquivos;
    }
    
    public void save(int tipo, ArrayList<Arquivo> arquivos) throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File arquivo;
        if(tipo == EnumTipoArquivo.Envio.getTipoDoArquivo()){
            arquivo = new File(EnumDiretorio.Configuracao.getDiretorio().concat(File.separator).concat(EnumArquivos.Envio.getNomeDoArquivo()));
            persistir(gson.toJson(arquivos), arquivo);
        }else if (tipo == EnumTipoArquivo.Retorno.getTipoDoArquivo()){
            arquivo = new File(EnumDiretorio.Configuracao.getDiretorio().concat(File.separator).concat(EnumArquivos.Retorno.getNomeDoArquivo()));
            persistir(gson.toJson(arquivos), arquivo);
        }else{
            throw new IllegalArgumentException("Tipo de arquivo "+tipo+" inválido");
        }
    }

    private ArrayList<Arquivo> getEnvio() throws FileNotFoundException {
        File arquivoDeEnvio = new File(EnumDiretorio.Configuracao.getDiretorio().concat(File.separator).concat(EnumArquivos.Envio.getNomeDoArquivo()));
        FileReader fr = new FileReader(arquivoDeEnvio);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Arquivo>>() {}.getType();
        ArrayList<Arquivo> arquivosEnviados = gson.fromJson(fr, listType);
        return arquivosEnviados;
    }

    private ArrayList<Arquivo> getRetorno() throws FileNotFoundException {
        File arquivoDeRetorno = new File(EnumDiretorio.Configuracao.getDiretorio().concat(File.separator).concat(EnumArquivos.Retorno.getNomeDoArquivo()));
        FileReader fr = new FileReader(arquivoDeRetorno);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Arquivo>>() {}.getType();
        ArrayList<Arquivo> arquivosEnviados = gson.fromJson(fr, listType);
        return arquivosEnviados;
    }

    private void persistir(String conteudo, File arquivo) throws IOException {
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(arquivo));
        buffWrite.write(conteudo);
        buffWrite.flush();
        buffWrite.close();
    }

}
