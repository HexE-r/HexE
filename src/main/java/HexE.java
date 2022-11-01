import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class HexE {
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner sc = new Scanner(System.in);
        AudioProcess x = new AudioProcess();
        PinataIPFS ipfs = new PinataIPFS();
        System.out.println("-----------------------------------");
        System.out.println(" _  _         ___ ");
        System.out.println("| || |_____ _| __|");
        System.out.println("| __ / -_) \\ / _|"); 
        System.out.println("|_||_\\___/_\\_\\___|");
        System.out.println("-----------------------------------");
        System.out.println("Enter option: ");
        System.out.print("1. Encrypt Audio \n2. Decrypt Audio \n3. Encrypt Audio and send to IPFS \n4. Decrypt Audio from IPFS Hash\n");
        int op = sc.nextInt();
        String f = null, f1 = null;
        int[] arr = new int[3];
        long unixT = 0;
        PuzzleGrid grid;
        int[][] puzzGrid = new int[3][3];
        switch(op) {
            case 1:
                System.out.println("Enter file path of audio:");
                f = sc.next();
                x.audioFrames(f);
                arr = x.audioRead(f);
                x.audioToByte(f);
                grid = x.puzzleGenerator();
                puzzGrid = x.gridTaker(grid);
                x.gridToFile(puzzGrid);
                x.encrypt(f, arr[0], arr[2], arr[1], puzzGrid);
                System.out.println("Audio encrypted successfully");
                break;
            case 2:
                System.out.println("Enter file path of audio:");
                f = sc.next();
                x.audioFrames(f);
                arr = x.audioRead(f);
                x.audioToByteDec(f);
                System.out.println("Enter file path for puzzle:");
                f1 = sc.next();
                puzzGrid = x.gridReader(f1);
                System.out.println("Enter timestamp in UNIX format:");
                unixT = sc.nextLong();
                x.decrypt(f, arr[0], arr[2], arr[1], puzzGrid, unixT);
                break;
            case 3:
                System.out.println("Enter file path of audio:");
                f = sc.next();
                x.audioFrames(f);
                arr = x.audioRead(f);
                x.audioToByte(f);
                grid = x.puzzleGenerator();
                puzzGrid = x.gridTaker(grid);
                x.gridToFile(puzzGrid);
                x.encrypt("./binary.crypt", arr[0], arr[2], arr[1], puzzGrid);
                System.out.println("Audio encrypted successfully");
                ipfs.sendFile(f);
                break;
            case 4:
                System.out.println("Enter IPFS Hash:");
                String hash = sc.next();
                ipfs.downloadUsingStream(hash);
                f = "./output.wav";
                x.audioFrames(f);
                arr = x.audioRead(f);
                x.audioToByteDec(f);
                System.out.println("Enter file path for puzzle:");
                f1 = sc.next();
                puzzGrid = x.gridReader(f1);
                System.out.println("Enter timestamp in UNIX format:");
                unixT = sc.nextLong();
                x.decrypt("./binary1.crypt", arr[0], arr[2], arr[1], puzzGrid, unixT);
                break;
            //case 5:
                
                //break;
            default:
                System.err.println("Invalid option");
                break;
        }
        sc.close();
    }
}
