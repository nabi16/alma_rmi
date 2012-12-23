/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siteServeur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import founisseurs.Fournisseur;
import siteClient.ClientDistant;
import siteClient.ClientDistantImpl;

/**
 *
 * @author user
 */
public class ServeurChatImpl extends UnicastRemoteObject implements ServeurChat{
    
	ArrayList<ClientDistant> Clients = new ArrayList<ClientDistant>();
    ArrayList<String> listeSubjects = new ArrayList<String>();
    //HashMap<String, ArrayList<ClientDistant>> clientBySubject = new HashMap<String, ArrayList<ClientDistant>>();
    HashMap<String, Fournisseur> fournisseursDeSujet = new HashMap<String, Fournisseur>();
     public ServeurChatImpl()throws RemoteException{
  
        listeSubjects.add("Sujet1");
        listeSubjects.add("Sujet2");       
        listeSubjects.add("Sujet3");
        createFournisseur();
    }
     
    public void createFournisseur() throws RemoteException{
    	for(int i=0; i<listeSubjects.size(); i++){
    		Fournisseur f= new Fournisseur(listeSubjects.get(i),listeSubjects.get(i));
    		fournisseursDeSujet.put(listeSubjects.get(i), f);
    	}
    }
    
    public void lookForFournisseur(String sujet, ClientDistant cl){
    		if(fournisseursDeSujet.containsKey(sujet)){
    			try {
					fournisseursDeSujet.get(sujet).connecter(cl);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    		
    			}else{
    				Fournisseur f= null;
    				try {
						f= new Fournisseur(sujet,sujet);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				f.setSujet(sujet);
    				listeSubjects.add(sujet);
    				try {
						f.connecter(cl);
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}    			}    	
    }
    
    public void connecter(ClientDistant client) throws RemoteException {
        System.out.println("Un client connecte");
        Clients.add(client);
    }

    public void deconnecter() throws RemoteException {
        System.out.println("Un client est deconnecte");
    }

    public void envoyerMessage(String m, String subjectName) throws RemoteException {
    	if(fournisseursDeSujet.containsKey(subjectName)){
    		fournisseursDeSujet.get(subjectName).envoyerMessage(m, subjectName);
    	}
    }

   
   public void subscribeTo(String subject, ClientDistant c) throws RemoteException
   {
	   
	   if(fournisseursDeSujet.containsKey(subject)){
		   fournisseursDeSujet.get(subject).getClients().add(c);
	   }
   }

	@Override
	public ArrayList<String> getListeSubjects() throws RemoteException {
		return listeSubjects;
	}
}
