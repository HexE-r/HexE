import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;


public class HexETest {

    @DisplayName("Test 1")
    @Test
    public void testMain() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"9", "1", "src/test/files/raw/file1.wav"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Test 2")
    @Test
    public void testMain2() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"16", "1", "src/test/files/raw/file1.wav"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Test 3")
    @Test
    public void testMain3() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"16", "1", "src/test/files/raw/file2.wav"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Test 4")
    @Test
    public void testMain4() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"9", "1", "src/test/files/raw/file2.wav"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Repeated Test")
    @RepeatedTest(10)
    public void testMainRepeat() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"9", "1", "test.wav"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Repeated Test var 16")
    @RepeatedTest(10)
    public void testMainRepeat16() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"16", "1", "test.wav"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Test 7")
    @Test
    public void testMainDec() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"9", "2", "src/test/files/raw/file1.wav", "puzzGrid.txt", "1010101010"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Test 2")
    @Test
    public void testMainDec2() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"16", "2", "src/test/files/raw/file1.wav", "puzzGrid.txt", "1010101010"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Test 3")
    @Test
    public void testMainDec3() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"16", "2", "src/test/files/raw/file2.wav", "puzzGrid.txt", "1010101010"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Test 4")
    @Test
    public void testMainDec4() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"9", "1", "src/test/files/raw/file2.wav", "puzzGrid.txt", "1010101010"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Repeated Test")
    @RepeatedTest(10)
    public void testMainDecRepeat() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"9", "2", "test.wav", "puzzGrid.txt", "1010101010"};
        //HexE x = new HexE();
        HexE.main(args);
    }

    @DisplayName("Repeated Test var 16")
    @RepeatedTest(10)
    public void testMainDecRepeat16() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        System.out.println("main");
        String[] args = {"16", "2", "test.wav", "puzzGrid.txt", "1010101010"};
        //HexE x = new HexE();
        HexE.main(args);
    }
}
