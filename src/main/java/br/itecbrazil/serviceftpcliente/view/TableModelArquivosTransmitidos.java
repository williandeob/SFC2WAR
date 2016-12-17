/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.view;

import br.itecbrazil.serviceftpcliente.model.Arquivo;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author willian
 */
public class TableModelArquivosTransmitidos extends DefaultTableModel {
    public List<Arquivo> arquivos;
    public String[] colunas;

    public TableModelArquivosTransmitidos(List<Arquivo> arquivos) {
        this.arquivos = arquivos;
        this.colunas = new String[]{"Nome","Data","Status"};
        setColunasNaTabela();
    }
    
    public void setArquivos(List<Arquivo> arquivos){
        this.arquivos = arquivos;
        this.fireTableDataChanged();
    }
    
    @Override
    public int getRowCount() {
        if(this.arquivos == null)
            return 0;
        return this.arquivos.size();
    }

    @Override
    public int getColumnCount() {
       return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Arquivo arquivo = this.arquivos.get(rowIndex);
        switch(columnIndex){
            case 0:
                return arquivo.getNome();
            case 1:
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                return sdf.format(arquivo.getDataDeTransmissao());
            case 2:
                return "Enviado com sucesso";
            default:
                return "";
        }
    }
    
    private void setColunasNaTabela(){
        for(String coluna : this.colunas){
            this.addColumn(coluna);
        }
    }
    
    @Override
    public boolean isCellEditable(int rowIndex,int columnIndex){
       return false;
    }
    
}
