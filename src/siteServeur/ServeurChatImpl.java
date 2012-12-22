/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siteServeur;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import siteClient.ClientDistant;
import siteClient.ClientDistantImpl;

/**
 *
 * @author user
 */
public class ServeurChatImpl extends UnicastRemoteObject implements ServeurChat{
    ArrayList<ClientDistant> Clients = new ArrayList<ClientDistant>();
    ArrayList<String> listeSubjects = new ArrayList<String>();
    HashMap<String, ArrayList<ClientDistant>> clientBySubject = new HashMap<String, ArrayList<ClientDistant>>();
    
    public ServeurChatImpl()throws RemoteException{
        listeSubjects.add("Sujet1");
        clientBySubject.put("Sujet1", new ArrayList<ClientDistant>());

        listeSubjects.add("Sujet2");
        clientBySubject.put("Sujet2", new ArrayList<ClientDistant>());
        
        listeSubjects.add("Sujet3");
        clientBySubject.put("Sujet3", new ArrayList<ClientDistant>());
    }
    public void connecter(ClientDistant client) throws RemoteException {
        System.out.println("Un client connecte");
        Clients.add(client);
    }

    public void deconnecter() throws RemoteException {
        System.out.println("Un client est deconnecte");
    }

    public void envoyerMessage(String m, String subjectName) throws RemoteException {
        for(ClientDistant client: clientBySubject.get(subjectName))
        {
            try{
                client.renvoyerMessage(subjectName, m);
            } catch (RemoteException e) { e.printStackTrace();}
        } //System.out.println(m);
    }

   public ArrayList<String> getListeSubjects() throws RemoteException {
       return listeSubjects;
   }
   
   public void subscribeTo(String subject, ClientDistant c) throws RemoteException
   {
       clientBySubject.get(subject).add(c);
   }
}
