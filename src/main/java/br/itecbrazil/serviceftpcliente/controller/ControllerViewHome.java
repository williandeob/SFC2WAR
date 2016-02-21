/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.itecbrazil.serviceftpcliente.controller;

import br.itecbrazil.serviceftpcliente.model.ScheduleEngine;
import br.itecbrazil.serviceftpcliente.util.UtilSocket;
import br.itecbrazil.serviceftpcliente.view.ViewHome;
import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author itec-desenv-willian
 */
public class ControllerViewHome {

    private TrayIcon iconItec;
    ViewHome viewHome;

    public ControllerViewHome(ViewHome viewHome) {
        this.viewHome = viewHome;
    }

    public ViewHome getViewHome() {
        return viewHome;
    }

    public void setViewHome(ViewHome viewHome) {
        this.viewHome = viewHome;
    }

    public void rodarServicoEmBackGround() {
        try {

            SystemTray tray = SystemTray.getSystemTray();
            ImageIcon img = new ImageIcon(getClass().getResource("/itecBrazil.png"));
            MouseListener mouseListener;
            mouseListener = new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Tray Icon - Mouse clicked!");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    System.out.println("Evento mouseEntered");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    System.out.println("Evento mouseExited");
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    System.out.println("Evento mousePressed");
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    System.out.println("Evento mouseReleased!");
                }
            };

            ActionListener exitListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    ScheduleEngine.pararScheduler();
                    getViewHome().dispose();
                    try {
                        UtilSocket.liberarPortaParaNovasIntacias();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "ERRO: Ocorreu um erro inesperado ao fechar o programa"
                                + "", "ALERT", ERROR_MESSAGE);
                    }
                    System.exit(0);
                }
            };

            ActionListener displayListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    getViewHome().setVisible(true);
                }
            };

            PopupMenu popup = new PopupMenu();
            MenuItem exitItem = new MenuItem("Sair do SFC");
            exitItem.addActionListener(exitListener);
            MenuItem diplayItem = new MenuItem("Exibir view SFC");
            diplayItem.addActionListener(displayListener);
            popup.add(exitItem);
            popup.add(diplayItem);

            iconItec = new TrayIcon(img.getImage(), "ITECBRAZIL - SFC", popup);

            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    iconItec.displayMessage("Action Event",
                            "An Action Event Has Been Peformed!",
                            TrayIcon.MessageType.INFO);
                }
            };

            iconItec.setImageAutoSize(true);
            iconItec.addActionListener(actionListener);
            iconItec.addMouseListener(mouseListener);

            tray.add(iconItec);

        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(getViewHome(), "INFO: Sistema não suporta executar o serviço em background", "ALERT", WARNING_MESSAGE);
            getViewHome().setDefaultCloseOperation(EXIT_ON_CLOSE);
        } catch (AWTException ex) {
            JOptionPane.showMessageDialog(getViewHome(), "INFO: Sistema não suporta executar o serviço em background", "ALERT", WARNING_MESSAGE);
            getViewHome().setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }

}
