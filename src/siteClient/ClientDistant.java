/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package siteClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author user
 */
public interface ClientDistant extends Remote{
    void renvoyerMessage(String subject, String message) throws RemoteException;
    String getUserName() throws RemoteException;
    void subscribeTo(String text) throws RemoteException;
}
