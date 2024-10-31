/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Server;

/**
 *
 * @author User
 */
import Shared.FOSInterface; 
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.net.MalformedURLException;

public class Server {
    public static void main(String[] args) {
        try {
            
            // Start the RMI registry on port 1098
            LocateRegistry.createRegistry(1098);
            System.out.println("RMI registry started on port 1098.");

            // Create an instance of the server implementation
            FOSInterface si = new ServerImplementation();

            // Bind the server instance to a name in the RMI registry
            Naming.rebind("rmi://localhost/FOSService", si);
            System.out.println("Server is ready and bound to 'FOSService'.");
            
        } catch (RemoteException re) {
            System.out.println("RemoteException: " + re.getMessage());
            re.printStackTrace();
        } catch (MalformedURLException mfe) {
            System.out.println("MalformedURLException: " + mfe.getMessage());
            mfe.printStackTrace();
        }
    }
}

