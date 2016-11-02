/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videoimage;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author abaza
 */
public class GIFRecord implements Runnable {

    AnimatedGifEncoder e;

    public void init(File Outfile) {
        e = new AnimatedGifEncoder();
        e.start(Outfile.getAbsolutePath());
        e.setDelay(1000); // 1 frame per sec
    }

    @Override
    public void run() {
        System.gc();
        BufferedImage i = e.CreatingCaptcher();
        if (i != null) {
            e.addFrame(i);
        }
        i = null;
        new Thread(this, "Gif Recorder").start();
    }

    public boolean stop() {
        return e.finish();
    }

}
