/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author itec-desenv-willian
 */
public class ButtonTable extends JButton implements TableCellRenderer {

    private String rotulo;

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public ButtonTable(String rotulo) {
        this.rotulo = rotulo;
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        setForeground(table.getForeground());
        setBackground(new Color(205,205,205));
        setFont(new Font("Verdana", Font.TRUETYPE_FONT, 12));

        setText(getRotulo());

        return this;
    }
}
