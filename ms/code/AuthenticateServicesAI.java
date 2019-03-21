
import java.rmi.*;

public interface AuthenticateServicesAI extends java.rmi.Remote
{
  /*******************************************************
   * Authenticates user with username and password
   *******************************************************/

  boolean authenticateUser(String username, String password) throws RemoteException;

}