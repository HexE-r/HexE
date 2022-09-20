import javax.sound.midi.*;

public class AudioTest {
    public void play() {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();

            Sequence seq = new Sequence(Sequence.PPQ, 4);

            Track track = seq.createTrack();

            ShortMessage a = new ShortMessage();

            a.setMessage(144,1,44,100);

            MidiEvent noteOn = new MidiEvent(a,1);

            track.add(noteOn);

            ShortMessage b = new ShortMessage();
            b.setMessage(128, 1, 44, 100);
            MidiEvent noteOff = new MidiEvent(b, 16);
            track.add(noteOff);
            player.setSequence(seq);

            // Start the Sequencer using start() method
            player.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        AudioTest music = new AudioTest();
        music.play();
        System.out.println("Complete");
    }
}
