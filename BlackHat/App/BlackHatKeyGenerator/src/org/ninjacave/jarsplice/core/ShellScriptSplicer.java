package org.ninjacave.jarsplice.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class ShellScriptSplicer extends Splicer
{
  String[] batchFile = { "#!/bin/sh", "FNAME=\"`readlink -f \"$0\"`\"", "java -jar \"$FNAME\"", "exit 0", "" };

  @Override
  public void createFatJar(String[] jars, String[] natives, String output, String mainClass, String vmArgs)
    throws Exception
  {
    this.dirs.clear();

    FileOutputStream fos = new FileOutputStream(output);

    PrintStream pos = new PrintStream(fos);

      for (String batchFile1 : this.batchFile) {
          pos.println(batchFile1);
      }

    pos.flush();
    fos.flush();

    Manifest manifest = getManifest(mainClass, vmArgs);
    try
    ( JarOutputStream jos = new JarOutputStream(fos, manifest)) {
      addFilesFromJars(jars, jos);
      addNativesToJar(natives, jos);
      addJarSpliceLauncher(jos);
    } finally {
      pos.close();
      fos.close();
    }
  }

  @Override
  protected void addNativesToJar(String[] natives, JarOutputStream out)
    throws Exception
  {
      for (String native1 : natives) {
          if (native1.endsWith(".so")) {
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
