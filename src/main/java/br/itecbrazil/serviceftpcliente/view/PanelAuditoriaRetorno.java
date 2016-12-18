/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.view;

import br.itecbrazil.serviceftpcliente.enums.EnumTipoArquivo;
import br.itecbrazil.serviceftpcliente.model.Arquivo;
import br.itecbrazil.serviceftpcliente.model.ArquivoDao;
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author willian
 */
public class PanelAuditoriaRetorno extends javax.swing.JPanel {
private List<Arquivo> listaDeArquivosRetornados;

    /**
     * Creates new form PanelAuditoriaRetorno
     */
    public PanelAuditoriaRetorno() {
        setListaDeArquivosRetornados();
        initComponents();
        buildView();
    }
    
    public List<Arquivo> getListaDeArquivosRetornados() {
        return listaDeArquivosRetornados;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1_pesquisa_nome = new javax.swing.JLabel();
        jTextField1_pesquisa_nome = new javax.swing.JTextField();
        jLabel1_pesquisa_data = new javax.swing.JLabel();
        try{
            MaskFormatter maskDt = new MaskFormatter("##/##/####");
            jTextField_pesquisar_data = new javax.swing.JFormattedTextField(maskDt);
        }catch(ParseException ex){}
        jButton1_pesquisa = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(204, 204, 204));

        jLabel1_pesquisa_nome.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1_pesquisa_nome.setText("Nome do arquivo:");

        jTextField1_pesquisa_nome.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jTextField1_pesquisa_nome.setToolTipText("Pesquisar pelo nome do arquivo");

        jLabel1_pesquisa_data.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel1_pesquisa_data.setText("Data de retorno:");

        jTextField_pesquisar_data.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_pesquisar_data.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jTextField_pesquisar_data.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField_pesquisar_dataFocusLost(evt);
            }
        });

        jButton1_pesquisa.setBackground(new java.awt.Color(255, 255, 255));
        jButton1_pesquisa.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButton1_pesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ic_pesquisar.png"))); // NOI18N
        jButton1_pesquisa.setText("Pesquisar");
        jButton1_pesquisa.setToolTipText("");
        jButton1_pesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1_pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_pesquisaActionPerformed(evt);
            }
        });

        jTable1.setModel(new TableModelArquivosTransmitidos
            (getListaDeArquivosRetornados()));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1_pesquisa_nome)
                            .addComponent(jTextField1_pesquisa_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(jLabel1_pesquisa_data))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jTextField_pesquisar_data, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jButton1_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1_pesquisa_nome))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1_pesquisa_data)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1_pesquisa_nome, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jButton1_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTextField_pesquisar_data))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField_pesquisar_dataFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField_pesquisar_dataFocusLost
        if("  /  /    ".equals(jTextField_pesquisar_data.getText())){
            jTextField_pesquisar_data.setText("");
        }
    }//GEN-LAST:event_jTextField_pesquisar_dataFocusLost

    private void jButton1_pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_pesquisaActionPerformed
        setListaDeArquivosRetornados();
        if(!"".equals(jTextField1_pesquisa_nome.getText().trim()) && "  /  /    ".equals(jTextField_pesquisar_data.getText())){

            for(int i = 0; i < listaDeArquivosRetornados.size(); i++){
                if(!jTextField1_pesquisa_nome.getText().trim().equals(listaDeArquivosRetornados.get(i).getNome())){
                    listaDeArquivosRetornados.remove(i);
                    i--;
                }
            }
        }else if("".equals(jTextField1_pesquisa_nome.getText().trim()) && !"  /  /    ".equals(jTextField_pesquisar_data.getText())){
            for(int i = 0; i < listaDeArquivosRetornados.size(); i++){
                try {
                    SimpleDateFormat parse = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = parse.parse(jTextField_pesquisar_data.getText());
                    Date dataJaRegistrada = (Date) listaDeArquivosRetornados.get(i).getDataDeTransmissao().clone();
                    dataJaRegistrada.setHours(0);dataJaRegistrada.setMinutes(0);dataJaRegistrada.setSeconds(0);
                    if(!dataJaRegistrada.equals(date)){
                        listaDeArquivosRetornados.remove(i);
                        i--;
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PanelAuditoriaEnvio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if (!"".equals(jTextField1_pesquisa_nome.getText().trim()) && !"  /  /    ".equals(jTextField_pesquisar_data.getText())){
            for(int i = 0; i < listaDeArquivosRetornados.size(); i++){
                try {
                    SimpleDateFormat parse = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = parse.parse(jTextField_pesquisar_data.getText());
                    Date dataJaRegistrada = (Date) listaDeArquivosRetornados.get(i).getDataDeTransmissao().clone();
                    dataJaRegistrada.setHours(0);dataJaRegistrada.setMinutes(0);dataJaRegistrada.setSeconds(0);

                    if(!dataJaRegistrada.equals(date) || !jTextField1_pesquisa_nome.getText().trim().equals(listaDeArquivosRetornados.get(i).getNome())){
                        listaDeArquivosRetornados.remove(listaDeArquivosRetornados.get(i));
                        i--;
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PanelAuditoriaEnvio.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        jTable1.setModel(new TableModelArquivosTransmitidos(getListaDeArquivosRetornados()));
    }//GEN-LAST:event_jButton1_pesquisaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_pesquisa;
    private javax.swing.JLabel jLabel1_pesquisa_data;
    private javax.swing.JLabel jLabel1_pesquisa_nome;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1_pesquisa_nome;
    private javax.swing.JFormattedTextField jTextField_pesquisar_data;
    // End of variables declaration//GEN-END:variables

    private void setListaDeArquivosRetornados() {
        ArquivoDao arquivoDao = new ArquivoDao();
        try {
            listaDeArquivosRetornados = arquivoDao.getArquivos(EnumTipoArquivo.Retorno.getTipoDoArquivo());
            if(listaDeArquivosRetornados == null)
                listaDeArquivosRetornados = new ArrayList<Arquivo>();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PanelAuditoriaEnvio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buildView() {
        jTable1.setFont(new Font("Verdana",Font.PLAIN, 12));
        jTable1.setForeground(Color.WHITE);
        jTable1.setBackground(Color.BLUE);
        jTable1.setRowHeight(35);
        
        jTable1.setPreferredScrollableViewportSize(jScrollPane1.getPreferredSize());
        jTable1.setFillsViewportHeight(true);
        
        JTableHeader header = jTable1.getTableHeader();
        header.setFont(new Font("Verdana",Font.BOLD, 12));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLUE);
        
        TableColumn column = null;
        for(int col = 0; col<3; col++){
             column = jTable1.getColumnModel().getColumn(col);
            switch (col) {
                case 0:
                    column.setPreferredWidth((jTable1.getWidth()/5) * 2);
                    break;
                case 2:
                    column.setPreferredWidth((jTable1.getWidth()/5) * 2);
                    break;
                default:
                    column.setPreferredWidth((jTable1.getWidth()/5));
                    break;
            }
        }
    }
}
