/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siteServeur;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import siteClient.ClientDistant;

/**
 *
 * @author user
 */
public interface ServeurChat extends Remote {
   public void connecter(ClientDistant client) throws RemoteException;
   public void deconnecter() throws RemoteException;
   public void envoyerMessage(String m, String subjectName) throws RemoteException;
   public ArrayList<String> getListeSubjects() throws RemoteException;
   public void subscribeTo(String subject, ClientDistant c) throws RemoteException;
}
