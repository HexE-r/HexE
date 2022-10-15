import pinata.Pinata;
import java.io.*;
import pinata.PinataException;
import pinata.PinataResponse;

public class PinataIPFS {
    public void TestAPI() {
        Pinata pinata = new Pinata("3913db0a3955d0cd6449", "465097e85c4f810204f7149ab3fe3b58843cbe2472d7b3a41a7d8389e123663d");
        try {
            PinataResponse authResponse = pinata.testAuthentication();
            // If a PinataException hasn't been been thrown, it means that the status is 200  
            System.out.println(authResponse.getStatus()); // 200
            System.out.println("Test OK");
          } catch (PinataException e) {
            // The status returned is not 200
            System.out.print("Status code not 200");
            e.printStackTrace();
          } catch (IOException e) {
            // Unable to send request
            System.err.println("Error encountered");
            e.printStackTrace();
          }
          
          /* 
          // If you created a Pinata instance without keys
          try {
            PinataResponse authResponse = pinata.testAuthentication("yourPinataApiKey", "yourPinataSecretApiKey");
            // If a PinataException hasn't been been thrown, it means that the status is 200  
            System.out.println(authResponse.getStatus()); // 200
          } catch (PinataException e) {
            // The status returned is not 200
            e.printStackTrace();
          } catch (IOException e) {
            // Unable to send request
            e.printStackTrace();
          }
          */
    }
}
