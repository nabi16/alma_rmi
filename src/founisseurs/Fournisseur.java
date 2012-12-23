package founisseurs;

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
import java.util.logging.Level;
import java.util.logging.Logger;

import siteClient.ClientDistant;
import siteClient.LanceClient;
import siteServeur.ServeurChat;

public class Fournisseur extends UnicastRemoteObject implements IFournisseur{

	ArrayList<ClientDistant> Clients;
	String sujet;
	String name;
	public Fournisseur() throws RemoteException{

		
	}
	public Fournisseur(String name, String sujet) throws RemoteException{
		this.name=name;
		this.sujet=sujet;
		Clients=new ArrayList<ClientDistant>();
	}
	@Override
	public void connecter(ClientDistant client) throws RemoteException {
		// TODO Auto-generated method stub
		Clients.add(client);
	}

	@Override
	public void deconnecter() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void envoyerMessage(String m, String subjectName)
			throws RemoteException {
		for(ClientDistant client: Clients)
        {
            try{
                client.renvoyerMessage(this.sujet, m);
            } catch (RemoteException e) { e.printStackTrace();}
        }
		
	}

	@Override
	public void subscribeTo(String subject, ClientDistant c)
			throws RemoteException {
		Clients.add(c);
		
	}
	public String getSujet() {
		return sujet;
	}
	public void setSujet(String sujet) {
		this.sujet = sujet;
	}
	
	public ArrayList<ClientDistant> getClients() {
		return Clients;
	}
	public void setClients(ArrayList<ClientDistant> clients) {
		Clients = clients;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
