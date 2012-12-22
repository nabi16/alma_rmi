/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siteServeur;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class LanceServeur {

    public static void main(String[] args) {
        try {

            //LocateRegistry.createRegistry(10000); // createRegistry( port)
            Registry registry = LocateRegistry.createRegistry(1099);
            
            // Mise en place d'un security manager si le serveur doit appeler des classes recup sur des machines distantes
            System.out.println("Mise en place du Security Manager ...");
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
            //instanciation d'un obj de la classe distante
            ServeurChatImpl sci = new ServeurChatImpl();
            //enregistrement dans le registre de nom RMI
            String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/TestRMI";
            //System.out.println("Enregistrement de l'objet avec l'url : " + url);
            registry.rebind(url, sci);
           // Naming.rebind(url, informationImpl);
           
            System.out.println("Serveur lancé");
            
        } catch (RemoteException e) {
            e.printStackTrace();
        }  catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
