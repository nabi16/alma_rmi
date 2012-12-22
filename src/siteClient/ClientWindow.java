/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siteClient;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import siteServeur.ServeurChat;

/**
 *
 * @author gwen
 */
public class ClientWindow extends javax.swing.JFrame {
    FlowLayout subjectbuttons = new FlowLayout();
    JPanel panWest = new JPanel();
    JPanel panSouth = new JPanel();
    JTextArea textMessages = new JTextArea();
    JButton sendMessageButton = new JButton("Envoyer");
    JTextField currentMessage = new JTextField();
    
    String selectedSubject;
    
    JList listSubjects;
    DefaultListModel listSubjectsModel;
    JPopupMenu menuSubscribeSubject;
    JButton btnAddSubject;
            
    ServeurChat server;
    
    ClientDistantImpl messageListener;
    HashMap<String,String> buffersSubjects;
    
    void setServer(ServeurChat _server) {
        try {
            this.server = _server;
            
            listSubjectsModel = new DefaultListModel();  
            listSubjects = new JList(listSubjectsModel);
            
            menuSubscribeSubject = new JPopupMenu();
            
            for(String subject : server.getListeSubjects())
            {
                JMenuItem menuItem = new JMenuItem(subject);
                menuItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        String subjectLabel = ((JMenuItem)ae.getSource()).getText();
                        
                        if(listSubjectsModel.indexOf(subjectLabel) == -1)
                        {
                            try {
                                server.subscribeTo(subjectLabel, messageListener);
                                listSubjectsModel.addElement(subjectLabel);
                                listSubjects.setSelectedValue(subjectLabel, true);
                            } catch (RemoteException ex) {
                                Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                });
                menuSubscribeSubject.add(menuItem);  
            }
            
            listSubjects.addListSelectionListener(new ListSelectionListener(){
                public void valueChanged(ListSelectionEvent lse) {
                    on_subject_selected();
                }
            });
                    
            panWest.add(listSubjects);
            
        } catch (RemoteException ex) {
            Logger.getLogger(Client_old.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    void setMessageListener(ClientDistantImpl messageListener) {
        this.messageListener = messageListener;
    }
 
    /** Creates new form Client1 */
    public ClientWindow() throws RemoteException {
        this.setLayout(new BorderLayout());
        add(panWest, BorderLayout.WEST);
        panWest.setLayout(subjectbuttons);
        panWest.add(new JLabel("Subjects"));
        
        add(textMessages, BorderLayout.CENTER);
        add(currentMessage, BorderLayout.SOUTH);
        add(panSouth, BorderLayout.SOUTH);
        panSouth.setLayout(new FlowLayout());
        panSouth.add(currentMessage);
        currentMessage.setPreferredSize(new Dimension(200,30));
        panSouth.add(sendMessageButton);
        this.setSize(800, 600);
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                on_sendMessage(currentMessage.getText());
            }
        });
        
        btnAddSubject = new JButton("Subscribe to an other subject");
        btnAddSubject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                menuSubscribeSubject.show(btnAddSubject, btnAddSubject.getX(), btnAddSubject.getY() + btnAddSubject.getHeight());
            }
        });
        panWest.add(btnAddSubject);

        buffersSubjects = new HashMap<String, String>();
    }
    
    public void addMessage(String subject, String message)
    {
        buffersSubjects.put(subject, buffersSubjects.get(subject) + "\n" + message);
        
        textMessages.setText(buffersSubjects.get(selectedSubject));
    }
    
    public void on_sendMessage(String message)
    {
        try {
            server.envoyerMessage("<" + messageListener.getUserName() +"> "+ message, selectedSubject);
        } catch (RemoteException ex) {
            Logger.getLogger(ClientWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        currentMessage.setText("");
    }
    
    public void on_subject_selected()
    {
        selectedSubject = (String) listSubjects.getSelectedValue();
        System.out.println("selectedSubject : " + selectedSubject);
        textMessages.setText(buffersSubjects.get(selectedSubject));
        
        
    }
}
