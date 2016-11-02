package org.ninjacave.jarsplice.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class WinExeSplicer extends Splicer
{
  String stubFile = "res/stub.exe";

  @Override
  public void createFatJar(String[] jars, String[] natives, String output, String mainClass, String vmArgs) throws Exception {
    this.dirs.clear();

    FileOutputStream fos = new FileOutputStream(output);

    try
    ( InputStream is = getResourceAsStream(this.stubFile)) {
      int read = 0;
      byte[] bytes = new byte[8024];

      while ((read = is.read(bytes)) != -1)
        fos.write(bytes, 0, read);
    }

    fos.flush();

    Manifest manifest = getManifest(mainClass, vmArgs);
    try
    ( JarOutputStream jos = new JarOutputStream(fos, manifest)) {
      addFilesFromJars(jars, jos);
      addNativesToJar(natives, jos);
      addJarSpliceLauncher(jos);
    } finally {
      fos.close();
    }
  }

  protected InputStream getResourceAsStream(String res)
  {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(res);
  }

  @Override
  protected void addNativesToJar(String[] natives, JarOutputStream out) throws Exception
  {
      for (String native1 : natives) {
          if (native1.endsWith(".dll")) {
              try (InputStream in = new FileInputStream(native1)) {
                  out.putNextEntry(new ZipEntry(getFileName(native1)));
                  while ((this.bufferSize = in.read(this.buffer, 0, this.buffer.length)) != -1) {
                      out.write(this.buffer, 0, this.bufferSize);
                  }
              }
              out.closeEntry();
          }
      }
  }
}
