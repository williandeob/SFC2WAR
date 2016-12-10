/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.view;

import br.itecbrazil.serviceftpcliente.controller.ControllerPanelConfigServidoresFTPs;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author itec-desenv-willian
 */
public class PanelConfigServidoresFTPs extends javax.swing.JPanel {

    private DefaultTableModel modeloDaTabelaDeConfigFTP;
    private ControllerPanelConfigServidoresFTPs controller;

    /**
     * Creates new form PanelConfigServidoresFTPs
     */
    public PanelConfigServidoresFTPs() {
        modeloDaTabelaDeConfigFTP = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        initComponents();
        controller = new ControllerPanelConfigServidoresFTPs(this);
        popularTabelaDeConfiguracoesFTPs();
    }

    public JTable getjTableConfigFTP() {
        return jTableConfigFTP;
    }

    public ControllerPanelConfigServidoresFTPs getController() {
        return controller;
    }

    public DefaultTableModel getModeloDaTabelaDeConfigFTP() {
        return modeloDaTabelaDeConfigFTP;
    }

    public void popularTabelaDeConfiguracoesFTPs() {
        getController().popularTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneConfigFTP = new javax.swing.JScrollPane();
        jTableConfigFTP = new javax.swing.JTable();
        jLabelTitulo = new javax.swing.JLabel();
        jButtonAdcionarConfig = new javax.swing.JButton();
        jButtonLimpar = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 126, 244));
        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        setAutoscrolls(true);
        setMaximumSize(new java.awt.Dimension(680, 225));
        setMinimumSize(new java.awt.Dimension(680, 225));
        setName("JPanelConfigFTP"); // NOI18N
        setPreferredSize(new java.awt.Dimension(680, 225));

        jTableConfigFTP.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0)));
        jTableConfigFTP.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jTableConfigFTP.setModel(modeloDaTabelaDeConfigFTP);
        jTableConfigFTP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTableConfigFTP.setMaximumSize(new java.awt.Dimension(598, 100));
        jTableConfigFTP.setMinimumSize(new java.awt.Dimension(598, 100));
        jTableConfigFTP.setName("jTableConfigFTP"); // NOI18N
        jTableConfigFTP.setRowHeight(25);
        jTableConfigFTP.setRowSelectionAllowed(false);
        jTableConfigFTP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableConfigFTPMouseClicked(evt);
            }
        });
        jScrollPaneConfigFTP.setViewportView(jTableConfigFTP);

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jLabelTitulo.setText("Dados servidor ativo");

        jButtonAdcionarConfig.setBackground(new java.awt.Color(255, 255, 255));
        jButtonAdcionarConfig.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButtonAdcionarConfig.setText("Adicionar Novas Configurações");
        jButtonAdcionarConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonAdcionarConfig.setMaximumSize(new java.awt.Dimension(181, 30));
        jButtonAdcionarConfig.setMinimumSize(new java.awt.Dimension(181, 30));
        jButtonAdcionarConfig.setName("jButtonAdcionarConfig"); // NOI18N
        jButtonAdcionarConfig.setPreferredSize(new java.awt.Dimension(181, 30));
        jButtonAdcionarConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAdcionarConfigActionPerformed(evt);
            }
        });

        jButtonLimpar.setBackground(new java.awt.Color(255, 255, 255));
        jButtonLimpar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jButtonLimpar.setText("Limpar Configurações");
        jButtonLimpar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonLimpar.setMaximumSize(new java.awt.Dimension(135, 30));
        jButtonLimpar.setMinimumSize(new java.awt.Dimension(135, 30));
        jButtonLimpar.setName("jButtonLimpar"); // NOI18N
        jButtonLimpar.setPreferredSize(new java.awt.Dimension(135, 30));
        jButtonLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneConfigFTP, javax.swing.GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelTitulo)
                        .addGap(26, 26, 26)
                        .addComponent(jButtonAdcionarConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonAdcionarConfig, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPaneConfigFTP, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTableConfigFTPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableConfigFTPMouseClicked

        Object cnpjDaLinhaSelecionada = getjTableConfigFTP().getValueAt(getjTableConfigFTP().getSelectedRow(), 1);
        int linhaSelecionada = getjTableConfigFTP().getSelectedRow();
        getController().dispararAcaoDoController(getjTableConfigFTP().columnAtPoint(evt.getPoint()), linhaSelecionada, cnpjDaLinhaSelecionada);
    }//GEN-LAST:event_jTableConfigFTPMouseClicked

    private void jButtonAdcionarConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAdcionarConfigActionPerformed
        getController().adicionarConfiguracoesDeFTP();
    }//GEN-LAST:event_jButtonAdcionarConfigActionPerformed

    private void jButtonLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLimparActionPerformed
        getController().limparConfiguracoes();
    }//GEN-LAST:event_jButtonLimparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdcionarConfig;
    private javax.swing.JButton jButtonLimpar;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JScrollPane jScrollPaneConfigFTP;
    private javax.swing.JTable jTableConfigFTP;
    // End of variables declaration//GEN-END:variables
}
