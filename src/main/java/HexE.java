import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
        System.out.println("Enter key strength (9/16/25)");
        int n = sc.nextInt();
        int k = 0;
        if(n == 9) {
            k = 3;
        }
        else if(n == 16) {
            k = 4;
        }
        else if(n == 25) {
            k = 5;
        }
        System.out.println("Enter option: ");
        System.out.print("1. Encrypt Audio \n2. Decrypt Audio \n3. Encrypt Audio and send to IPFS \n4. Decrypt Audio from IPFS Hash\n5. Enter Test mode\n");
        int op = sc.nextInt();
        String f = null, f1 = null;
        int[] arr = new int[3];
        long unixT = 0, t1=0, t2=0;
        //PuzzleGrid grid;
        int[][] puzzGrid = new int[81][81];
        switch(op) {
            case 1:
                System.out.println("Enter file path of audio:");
                f = sc.next();
                x.audioFrames(f);
                arr = x.audioRead(f);
                //x.audioToByte(f);
                //grid = x.puzzleGenerator();
                puzzGrid = x.gridTaker(n,20);
                x.gridToFile(puzzGrid, n);
                x.encrypt(f, arr[0], arr[2], arr[1], puzzGrid, n, k);
                System.out.println("Audio encrypted successfully");
                break;
            case 2:
                System.out.println("Enter file path of audio:");
                f = sc.next();
                x.audioFrames(f);
                arr = x.audioRead(f);
                //x.audioToByteDec(f);
                System.out.println("Enter file path for puzzle:");
                f1 = sc.next();
                puzzGrid = x.gridReader(f1);
                System.out.println("Enter timestamp in UNIX format:");
                unixT = sc.nextLong();
                x.decrypt(f, arr[0], arr[2], arr[1], puzzGrid, unixT, n, k);
                break;
            case 3:
                System.out.println("Enter file path of audio:");
                f = sc.next();
                x.audioFrames(f);
                arr = x.audioRead(f);
                //x.audioToByte(f);
                //grid = x.puzzleGenerator();
                puzzGrid = x.gridTaker(n,20);
                x.gridToFile(puzzGrid, n);
                x.encrypt(f, arr[0], arr[2], arr[1], puzzGrid, n, k);
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
                //x.audioToByteDec(f);
                System.out.println("Enter file path for puzzle:");
                f1 = sc.next();
                puzzGrid = x.gridReader(f1);
                System.out.println("Enter timestamp in UNIX format:");
                unixT = sc.nextLong();
                x.decrypt(f, arr[0], arr[2], arr[1], puzzGrid, unixT, n, k);
                break;
            case 5:
                System.out.println("Encrypt (1) or Decrypt (2)?");
                int op1 = sc.nextInt();
                switch (op1) {
                    case 1:
                        System.out.println("Enter file path of audio:");
                        f = sc.next();
                        x.audioFrames(f);
                        arr = x.audioRead(f);
                        //x.audioToByte(f);
                        //grid = x.puzzleGenerator();
                        puzzGrid = x.gridTaker(n,20);
                        x.gridToFile(puzzGrid, n);
                        System.out.println("Recording time...");
                        t1 = System.nanoTime();
                        x.encrypt(f, arr[0], arr[2], arr[1], puzzGrid, n, k);
                        t2 = System.nanoTime();
                        System.out.println("Time taken to execute: ");
                        System.out.println(TimeUnit.SECONDS.convert(t2-t1, TimeUnit.NANOSECONDS));
                        break;
                    case 2:
                        System.out.println("Enter file path of audio:");
                        f = sc.next();
                        x.audioFrames(f);
                        arr = x.audioRead(f);
                        //x.audioToByteDec(f);
                        System.out.println("Enter file path for puzzle:");
                        f1 = sc.next();
                        puzzGrid = x.gridReader(f1);
                        System.out.println("Enter timestamp in UNIX format:");
                        unixT = sc.nextLong();
                        t1 = System.nanoTime();
                        x.decrypt(f, arr[0], arr[2], arr[1], puzzGrid, unixT, n, k);
                        t2 = System.nanoTime();
                        System.out.println("Time taken to execute: ");
                        System.out.println(TimeUnit.SECONDS.convert(t2-t1, TimeUnit.NANOSECONDS));
                        break;
                    default:
                        break;
                }
                break;
            default:
                System.err.println("Invalid option");
                break;
        }
        sc.close();
    }
}
