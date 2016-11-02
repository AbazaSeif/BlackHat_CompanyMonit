/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoimage;

import Comman.Tool;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author abaza
 */
public class GIFAnimation_Implimantation {

    private videoimage.GIFRecord GifVideoThread = new GIFRecord();
    private File GifFile;
    private final Tool cTool = new Tool();

    public String Status(boolean SwitchKey) {
        if (SwitchKey) {
            StartGIF();
        } else {
            if (StopGIF()) {
                return GifFile.getAbsolutePath();
            }
        }
        return null;
    }

    private boolean StartGIF() {
        try {
            GifFile = cTool.createTempDir(2);
            GifVideoThread = new videoimage.GIFRecord();
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
            //FTP
            return true;
        }
        return true;
    }
}
