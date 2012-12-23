package founisseurs;

import java.rmi.Remote;
import java.rmi.RemoteException;

import siteClient.ClientDistant;

public interface IFournisseur extends Remote{
	public void connecter(ClientDistant client) throws RemoteException;
	public void deconnecter() throws RemoteException;
	public void envoyerMessage(String m, String subjectName) throws RemoteException;
	public void subscribeTo(String subject, ClientDistant c) throws RemoteException;
}
