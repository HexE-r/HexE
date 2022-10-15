import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Random;
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
    public void audioRead(String f) {
        try
        {
            // Open the wav file specified as the first argument
            WavFile wavFile = WavFile.openWavFile(new File(f));

            // Display information about the wav file
            wavFile.display();

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

    public PuzzleGrid puzzleGenerator() {
        Solver solv = new Solver();
        PuzzleGen pgen = new PuzzleGen();
        // PuzzleGrid grid = new PuzzleGrid();
        Random r = new Random();
        int n = r.nextInt(60);
        PuzzleGrid grid = pgen.generate(n);
        solv.solve(grid);
        return grid;
    }

    public int[][] gridTaker(PuzzleGrid grid) {
        String GRID = grid.toString();
        int[][] puzzle = new int[81][81];
        int a=0, b=0;
        int n=GRID.length();

        for(int i=0;i<n;i++)
        {
            if(GRID.charAt(i)=='\n')
            {
                a++;
                b=0;
                System.out.print(" ");
                continue;
            }
            int x = (int) GRID.charAt(i) - 48;
            if(x!=-16) {
                puzzle[a][b] = (int) GRID.charAt(i) - 48;
                System.out.print(puzzle[a][b]);
                b++;
            }
        }
        puzzle[a][b] = (int) GRID.charAt(n-2) - 48;
        System.out.println();
        return puzzle;
    }

    public void encrypt(String f) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File(f);
        //AudioInputStream ai = AudioSystem.getAudioInputStream((InputStream) file.toPath());
        //Clip clip = AudioSystem.getClip();
        //clip.open(ai);
        byte[] content = Files.readAllBytes(file.toPath());
        for(int i=0; i<content.length; i++) {
            content[i] += 2;
        }
        ByteArrayInputStream oInstream = new ByteArrayInputStream(content);
        AudioFileFormat.Type afType = AudioFileFormat.Type.WAVE;
        AudioFormat adfmt = new AudioFormat(8000.0f, 16, 2, true , true);
        try {
            File a = new File("./test1.wav");
            AudioInputStream ais = new AudioInputStream(oInstream, adfmt, content.length/adfmt.getFrameSize());
            int W = AudioSystem.write(ais, afType, a);
            System.out.println(W);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decrypt(String f) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File(f);
        //AudioInputStream ai = AudioSystem.getAudioInputStream((InputStream) file.toPath());
        //Clip clip = AudioSystem.getClip();
        //clip.open(ai);
        byte[] content = Files.readAllBytes(file.toPath());
        for(int i=0; i<content.length; i++) {
            content[i] -= 2;
        }
        ByteArrayInputStream oInstream = new ByteArrayInputStream(content);
        AudioFileFormat.Type afType = AudioFileFormat.Type.WAVE;
        AudioFormat adfmt = new AudioFormat(8000.0f, 16, 2, true , true);
        try {
            File a = new File("./test2.wav");
            AudioInputStream ais = new AudioInputStream(oInstream, adfmt, content.length/adfmt.getFrameSize());
            int W = AudioSystem.write(ais, afType, a);
            System.out.println(W);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        Scanner sc = new Scanner(System.in);
        AudioProcess x = new AudioProcess();
        PinataIPFS ipfs = new PinataIPFS();
        System.out.println("Enter file path of audio:");
        String f = sc.next();
        x.audioFrames(f);
        x.audioRead(f);
        x.audioToByte(f);
        PuzzleGrid grid = x.puzzleGenerator();
        x.gridTaker(grid);
        x.encrypt("./binary.crypt");
        x.decrypt("./test1.wav");
        ipfs.TestAPI();
        sc.close();
    }
}
