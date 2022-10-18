import java.io.File;

import org.tensorflow.Operation;
import org.tensorflow.TensorFlow;
import org.tensorflow.op.audio.DecodeWav;
import org.tensorflow.op.audio.Mfcc;

public class AudioCompare implements Operation{
    public void mfccFn(File f1, File f2) {
        System.out.println("Checking TensorFlow version...");
        System.out.println(TensorFlow.version());
        DecodeWav dWav = DecodeWav.create(, f1, null);
    }
}
