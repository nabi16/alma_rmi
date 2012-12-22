/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siteClient;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author user
 */
public class ClientDistantImpl extends UnicastRemoteObject implements ClientDistant{

    ClientWindow _clientWindow;
    String _userName;
    
    public ClientDistantImpl(ClientWindow client)throws RemoteException{
        _clientWindow = client;
        client.setMessageListener(this);
    }
    public void renvoyerMessage(String subject, String message) throws RemoteException {
        _clientWindow.addMessage(subject, message);
        System.out.println("msg recu: " + message);
    }
    
    public void setUserName(String userName){
        _userName = userName;
    }
    
    public String getUserName(){
        return _userName;
    }

    public void subscribeTo(String text) {
        System.out.println("subscribe to :" + text + " [deprecated]");
        
    }

}
