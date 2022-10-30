import pinata.Pinata;
import java.io.*;
import java.net.URL;

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

    public void sendFile(String f) {
      File f1 = new File(f);
      JSONParse x = new JSONParse();
      Pinata pinata = new Pinata("3913db0a3955d0cd6449", "465097e85c4f810204f7149ab3fe3b58843cbe2472d7b3a41a7d8389e123663d");
      try {
        pinata.testAuthentication();
      } catch (Exception e) {
        System.err.println("Auth not successful");
        e.printStackTrace();
      }
      System.out.print("Commencing file upload via Pinata API...\n");
      try {
        PinataResponse r = pinata.pinFileToIpfs(f1);
        System.out.println("File uploaded to Pinata IPFS");
        String res = r.getBody();
        String res1 = x.parseData(res);
        System.out.println("Pinned IPFS Hash: " + res1);
      } catch (Exception e) {
        System.out.print("Could not upload file to Pinata IPFS");
        e.printStackTrace();
      }
    }

    public void downloadUsingStream(String hash) throws IOException{
      String urlStr = "https://cloudflare-ipfs.com/ipfs/" + hash;
      URL url = new URL(urlStr);
      BufferedInputStream bis = new BufferedInputStream(url.openStream());
      FileOutputStream fis = new FileOutputStream("output.wav");
      byte[] buffer = new byte[1024];
      int count=0;
      while((count = bis.read(buffer,0,1024)) != -1)
      {
          fis.write(buffer, 0, count);
      }
      fis.close();
      bis.close();
      System.out.println("File Downloaded from IPFS");
  }
}
