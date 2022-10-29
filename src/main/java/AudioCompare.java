import java.io.File;

import org.tensorflow.Operation;
import org.tensorflow.TensorFlow;
import org.tensorflow.internal.c_api.TF_TString;
import org.tensorflow.op.audio.DecodeWav;
import org.tensorflow.op.audio.Mfcc;
import org.tensorflow.op.io.ReadFile;
import org.tensorflow.types.TString;

/** 
public class AudioCompare{
    public void mfccFn(String f1, String f2) {
        System.out.println("Checking TensorFlow version...");
        System.out.println(TensorFlow.version());
        TString wav1 = ReadFile.create(null, null);
        DecodeWav dWav = DecodeWav.create(null, null, null);
        DecodeWav dWav2 = DecodeWav.create(null, null, null);
        dWav.audio();
    }
}
*/
