/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import java.io.File;
import java.io.FileNotFoundException;
import tooles.CreateTempFile;

/**
 *
 * @author abaza
 */
public class GIFAnimation_Implimantation {

    private GIFRecord GifVideoThread = new GIFRecord();
    private File GifFile;

    public String Status(boolean SwitchKey) {
        if (SwitchKey) {
            StartGIF();
        } else if (StopGIF()) {
            return GifFile.getAbsolutePath();
        }
        return null;
    }

    private boolean StartGIF() {
        try {
            CreateTempFile CTF = new CreateTempFile();
            GifFile = CTF.createTempDir(2, 2);
            GifVideoThread = new GIFRecord();
            GifVideoThread.init(GifFile);
            GifVideoThread.run();
            return true;
        } catch (FileNotFoundException ex) {
            return false;
        }
    }

    private boolean StopGIF() {
        if (GifVideoThread != null) {
            while (!GifVideoThread.stop()) {
                GifVideoThread = null;
            }
            return true;
        }
        return true;
    }

}
