/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.util;

import java.io.IOException;
import java.net.ServerSocket;


/**
 *
 * @author itec-desenv-willian
 */
public class UtilSocket {

    private static ServerSocket socket;

    public static void verificarInstaciaEmExecucao() throws IOException {
            socket = new ServerSocket(9581);
      
    }
    
    public static void liberarPortaParaNovasIntacias() throws IOException{       
            socket.close();      
    }

}
