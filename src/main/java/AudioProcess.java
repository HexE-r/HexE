import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class AudioProcess {
    // Long[] binStore = new Long[999999];
    public void audioFrames(String f) {
        int totalFramesRead = 0;
        File fileIn = new File(f);
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(fileIn);
            int bytesPerFrame =
                    audioInputStream.getFormat().getFrameSize();
            // Set an arbitrary buffer size of 1024 frames.
            int numBytes = 1024 * bytesPerFrame;
            byte[] audioBytes = new byte[numBytes];
            try {
                int numBytesRead = 0;
                int numFramesRead = 0;
                // Try to read numBytes bytes from the file.
                while ((numBytesRead =
                        audioInputStream.read(audioBytes)) != -1) {
                    // Calculate the number of frames actually read.
                    numFramesRead = numBytesRead / bytesPerFrame;
                    totalFramesRead += numFramesRead;
                }
                System.out.println("Audio frames present: " + totalFramesRead);
            } catch (Exception ex) {
                // Handle the error...
                ex.printStackTrace();
            }
        } catch (Exception e) {
            // Handle the error...
            e.printStackTrace();
        }
    }
    public int[] audioRead(String f) {
        int[] metadata = new int[3];
        try
        {
            // Open the wav file specified as the first argument
            WavFile wavFile = WavFile.openWavFile(new File(f));

            // Display information about the wav file
            wavFile.display();

            metadata[0] = wavFile.getNumChannels();
            metadata[1] = wavFile.getValidBits();
            metadata[2] = (int)wavFile.getSampleRate();

            // Get the number of audio channels in the wav file
            int numChannels = wavFile.getNumChannels();

            // Create a buffer of 100 frames
            double[] buffer = new double[100 * numChannels];

            int framesRead;
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            do
            {
                // Read frames into buffer
                framesRead = wavFile.readFrames(buffer, 100);

                // Loop through frames and look for minimum and maximum value
                for (int s=0 ; s<framesRead * numChannels ; s++)
                {
                    if (buffer[s] > max) max = buffer[s];
                    if (buffer[s] < min) min = buffer[s];
                }
            }
            while (framesRead != 0);

            wavFile.close();

            System.out.printf("Min: %f, Max: %f\n", min, max);
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
        return metadata;
    }

    public void audioToByte(String f) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));

            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0)
            {
                out.write(buff, 0, read);
            }
            out.flush();
            byte[] audioBytes = out.toByteArray();
            File file = new File("./binary.crypt");
            OutputStream os = new FileOutputStream(file);
            os.write(audioBytes);
            os.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void audioToByteDec(String f) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));

            int read;
            byte[] buff = new byte[1024];
            while ((read = in.read(buff)) > 0)
            {
                out.write(buff, 0, read);
            }
            out.flush();
            byte[] audioBytes = out.toByteArray();
            File file = new File("./binary1.crypt");
            OutputStream os = new FileOutputStream(file);
            os.write(audioBytes);
            os.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] gridTaker(int n, int k) {
        int[][] puzzle = new int[81][81];
        SuDoKu y = new SuDoKu(n, k);
        puzzle = y.gridGen(n, k);
        return puzzle;
    }

    public void gridToFile(int[][] grid, int n) {
        try {
            PrintWriter writer = new PrintWriter(new File("puzzGrid.txt"));
            for(int i=0; i<n; i++) {
                for(int j=0; j<n; j++) {
                    writer.print(grid[i][j]);
                    writer.print(" ");
                }
                writer.println();
            }
            writer.close();
            System.out.println("\nPuzzle Grid saved under puzzGrid.txt");
        }
        catch (IOException e) {
            System.err.println("\nError occured");
            e.printStackTrace();
        }
    }

    public int[][] gridReader(String f) throws IOException {
        int[][] GRID = new int[81][81];
        Path file = Paths.get(f);
        Scanner scan = new Scanner(file);
        ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
        while(scan.hasNextLine())
        {
            Scanner colReader = new Scanner(scan.nextLine());
            ArrayList<Integer> col = new ArrayList<Integer>();
            while(colReader.hasNextInt())
            {
                col.add(colReader.nextInt());
            }
            a.add(col);
        }

        int x=0,y=0;
        for(int i=0;i<a.size();i++) {
            for(int j=0;j<a.get(i).size();j++)
            {
                GRID[x][y] = a.get(i).get(j);
                y++;
            }
            x++;
            y=0;
        }
        
        scan.close();
        System.out.println("\nFile import successful");
        return GRID;
    }

    public int[][] keyGen(int[][] grid, int n, int k) {
        int[][] miniGrid = new int[n][n];
        long unixTime = System.currentTimeMillis()/1000L;
        System.out.println("\nUNIX Timestamp: " + unixTime + "\n");
        int p = 0;
        long digit = 0;
        long d1 = unixTime % 10;
        while(unixTime > 0) {
            digit = unixTime % 10;
            p += digit;
            unixTime /= 10;
        }
        //Random rand = new Random();
        int t = p % n;
        if(t==0) {
            t++;
        }
        int u = p % k;
        int u1 = (int) d1 % k;
        int k_pos = 0;
        if(k == 3) {
            k_pos = 0;
        }
        else if(k == 4) {
            k_pos = 1;
        }
        else if(k == 5) {
            k_pos = 4;
        }
        int grd = 0;
        //System.out.print("t: "+ t +" u: "+u+" u1: "+u1+"\n");
        if(u == 0 && u1 == 0)
        {
            grd = 1;
        }
        else if(u == 0 && u1 == 1)
        {
            grd = 2;
        }
        else if(u == 0 && u1 == 2)
        {
            grd = 3;
        }
        else if(u == 1 && u1 == 0)
        {
            grd = 4;
        }
        else if(u == 1 && u1 == 1)
        {
            grd = 5;
        }
        else if(u == 1 && u1 == 2)
        {
            grd = 6;
        }
        else if(u == 2 && u1 == 0)
        {
            grd = 7;
        }
        else if(u == 2 && u1 == 1)
        {
            grd = 8;
        }
        else {
            grd = 9;
        }
        int k1=0, k2=0;
        switch (grd) {
            case 1:
                for(int i=(0+k_pos);i<(3+k_pos);i++)
                {
                    for(int j=(0+k_pos);j<(3+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 2:
                for(int i=(0+k_pos);i<(3+k_pos);i++)
                {
                    for(int j=(3+k_pos);j<(6+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 3:
                for(int i=(0+k_pos);i<(3+k_pos);i++)
                {
                    for(int j=(6+k_pos);j<(9+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 4:
                for(int i=(3+k_pos);i<(6+k_pos);i++)
                {
                    for(int j=(0+k_pos);j<(3+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 5:
                for(int i=(3+k_pos);i<(6+k_pos);i++)
                {
                    for(int j=(3+k_pos);j<(6+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 6:
                for(int i=(3+k_pos);i<(6+k_pos);i++)
                {
                    for(int j=6+(k_pos);j<(9+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 7:
                for(int i=(6+k_pos);i<(9+k_pos);i++)
                {
                    for(int j=(0+k_pos);j<(3+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 8:
                for(int i=(6+k_pos);i<(9+k_pos);i++)
                {
                    for(int j=(3+k_pos);j<(6+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 9:
                for(int i=(6+k_pos);i<(9+k_pos);i++)
                {
                    for(int j=(6+k_pos);j<(9+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            default:
                System.err.println("Invalid GRID. Please try again");
                break;
        }
        return miniGrid;
    }

    public int[][] keyGenDec(int[][] grid, int n, int k, long unixT) {
        int[][] miniGrid = new int[n][n];
        long unixTime = unixT;
        System.out.println("\nUNIX Timestamp: " + unixTime + "\n");
        int p = 0;
        long digit = 0;
        long d1 = unixTime % 10;
        while(unixTime > 0) {
            digit = unixTime % 10;
            p += digit;
            unixTime /= 10;
        }
        //Random rand = new Random();
        int t = p % n;
        if(t==0) {
            t++;
        }
        int u = p % k;
        int u1 = (int) d1 % k;
        int grd = 0;
        int k_pos = 0;
        if(k == 3) {
            k_pos = 0;
        }
        else if(k == 4) {
            k_pos = 1;
        }
        else if(k == 5) {
            k_pos = 4;
        }
        //System.out.print("t: "+ t +" u: "+u+" u1: "+u1+"\n");
        if(u == 0 && u1 == 0)
        {
            grd = 1;
        }
        else if(u == 0 && u1 == 1)
        {
            grd = 2;
        }
        else if(u == 0 && u1 == 2)
        {
            grd = 3;
        }
        else if(u == 1 && u1 == 0)
        {
            grd = 4;
        }
        else if(u == 1 && u1 == 1)
        {
            grd = 5;
        }
        else if(u == 1 && u1 == 2)
        {
            grd = 6;
        }
        else if(u == 2 && u1 == 0)
        {
            grd = 7;
        }
        else if(u == 2 && u1 == 1)
        {
            grd = 8;
        }
        else {
            grd = 9;
        }
        int k1=0, k2=0;
        switch (grd) {
            case 1:
                for(int i=(0+k_pos);i<(3+k_pos);i++)
                {
                    for(int j=(0+k_pos);j<(3+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 2:
                for(int i=(0+k_pos);i<(3+k_pos);i++)
                {
                    for(int j=(3+k_pos);j<(6+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 3:
                for(int i=(0+k_pos);i<(3+k_pos);i++)
                {
                    for(int j=(6+k_pos);j<(9+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 4:
                for(int i=(3+k_pos);i<(6+k_pos);i++)
                {
                    for(int j=(0+k_pos);j<(3+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 5:
                for(int i=(3+k_pos);i<(6+k_pos);i++)
                {
                    for(int j=(3+k_pos);j<(6+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 6:
                for(int i=(3+k_pos);i<(6+k_pos);i++)
                {
                    for(int j=6+(k_pos);j<(9+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 7:
                for(int i=(6+k_pos);i<(9+k_pos);i++)
                {
                    for(int j=(0+k_pos);j<(3+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 8:
                for(int i=(6+k_pos);i<(9+k_pos);i++)
                {
                    for(int j=(3+k_pos);j<(6+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            case 9:
                for(int i=(6+k_pos);i<(9+k_pos);i++)
                {
                    for(int j=(6+k_pos);j<(9+k_pos);j++)
                    {
                        miniGrid[k1][k2] = grid[i][j] * t;
                        k2++;
                    }
                    k1++;
                    k2=0;
                }
                break;
            default:
                System.err.println("Invalid GRID. Please try again");
                break;
        }
        return miniGrid;
    }

    public String keyGenBinary(int[][] grid, int n, int k) {
        int[][] miniGrid = keyGen(grid, n, k);
        String binKey = null;
        StringBuilder binMod = new StringBuilder();

        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                System.out.print(miniGrid[i][j] + "\t");
                binMod.append(Integer.toBinaryString(miniGrid[i][j]));
            }
            System.out.println();
        }
        System.out.println(binMod);
        binKey = binMod.toString();
        return binKey;
    }
    public String keyGenBinaryDec(int[][] grid, int n, int k, long unixT) {
        int[][] miniGrid = keyGenDec(grid, n, k, unixT);
        String binKey = null;
        StringBuilder binMod = new StringBuilder();

        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                System.out.print(miniGrid[i][j] + "\t");
                binMod.append(Integer.toBinaryString(miniGrid[i][j]));
            }
            System.out.println();
        }
        System.out.println(binMod);
        binKey = binMod.toString();
        return binKey;
    }

    public void encrypt(String f, int Channel, int SampleRate, int Val, int[][] grid, int n, int k) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //File file = new File(f);
        //AudioInputStream ai = AudioSystem.getAudioInputStream((InputStream) file.toPath());
        //Clip clip = AudioSystem.getClip();
        //clip.open(ai);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] content = out.toByteArray();
        String key = keyGenBinary(grid, n, k);
        //System.out.println(key);
        int len = key.length();
        int j = 0;
        for(int i=40; i<content.length; i++) {
            if(j == len-1)
            {
                j = 0;
            }
            char c = key.charAt(j);
            int keyVal = c - '0';
            //System.out.print(keyVal);
            content[i] = (byte) (content[i] ^ (keyVal*5000));
            if(i%10 == 0)
            {
                j++;
            }
            //System.out.print(content[i]);
        }
        try {
            Path a = Paths.get("./test1.wav");
            Files.write(a, content);
            //System.out.println(W);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        in.close();
    }

    public void decrypt(String f, int Channel, int SampleRate, int Val, int[][] grid, long unixT, int n, int k) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        //File file = new File(f);
        //AudioInputStream ai = AudioSystem.getAudioInputStream((InputStream) file.toPath());
        //Clip clip = AudioSystem.getClip();
        //clip.open(ai);
         
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0)
        {
            out.write(buff, 0, read);
        }
        out.flush();
        byte[] content = out.toByteArray();
        String key = keyGenBinaryDec(grid, n, k, unixT);
        //System.out.println(key);
        int len = key.length();
        int j = 0;
        for(int i=40; i<content.length; i++) {
            if(j == len-1)
            {
                j = 0;
            }
            char c = key.charAt(j);
            int keyVal = c - '0';
            content[i] = (byte) (content[i] ^ (keyVal*5000));
            if(i%10 == 0)
            {
                j++;
            }
            //System.out.print(content[i]);
        }
        try {
            Path a = Paths.get("./test2.wav");
            Files.write(a, content);
            //System.out.println(W);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        in.close();
    }

    public void audioWrite(String f) {
        try
        {
            int sampleRate = 44100;    // Samples per second
            double duration = 5.0;     // Seconds

            // Calculate the number of frames required for specified duration
            long numFrames = (long)(duration * sampleRate);

            // Create a wav file with the name specified as the first argument
            WavFile wavFile = WavFile.newWavFile(new File(f), 2, numFrames, 16, sampleRate);

            // Create a buffer of 100 frames
            double[][] buffer = new double[2][100];

            // Initialise a local frame counter
            long frameCounter = 0;

            // Loop until all frames written
            while (frameCounter < numFrames)
            {
                // Determine how many frames to write, up to a maximum of the buffer size
                long remaining = wavFile.getFramesRemaining();
                int toWrite = (remaining > 100) ? 100 : (int) remaining;

                // Fill the buffer, one tone per channel
                for (int s=0 ; s<toWrite ; s++, frameCounter++)
                {
                    buffer[0][s] = Math.sin(2.0 * Math.PI * 400 * frameCounter / sampleRate);
                    buffer[1][s] = Math.sin(2.0 * Math.PI * 500 * frameCounter / sampleRate);
                }

                // Write the buffer
                wavFile.writeFrames(buffer, toWrite);
            }

            // Close the wavFile
            wavFile.close();
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}
