/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siteClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import siteServeur.ServeurChat;


/**
 *
 * @author user
 */
public class LanceClient{

    
    public static void main(String[] args) {
        //ClientDistantImpl cdi;
        System.out.println("Lancement du client");
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            ServeurChat r = (ServeurChat) registry.lookup("rmi://" + InetAddress.getLocalHost().getHostAddress() + "/TestRMI");
            //System.out.println(r);
            //cdi= new ClientDistantImpl();
            //r.connecter();
            Scanner sc= new Scanner(System.in);
            r.envoyerMessage(sc.nextLine(), "");
                //System.out.println("chaine renvoyee = " + s);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(LanceClient.class.getName()).log(Level.SEVERE, null, ex);
        }catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        //System.out.println("Fin du client");
    }
}
