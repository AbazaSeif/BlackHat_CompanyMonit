package org.ninjacave.jarsplice.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.ninjacave.jarsplice.JarSplice;

public class Splicer
{
  ArrayList<String> dirs = new ArrayList();
  int bufferSize;
  byte[] buffer = new byte[4096];

  public void createFatJar(String[] jars, String[] natives, String output, String mainClass, String vmArgs) throws Exception {
    this.dirs.clear();

    Manifest manifest = getManifest(mainClass, vmArgs);

    FileOutputStream fos = new FileOutputStream(output);
    try
    ( JarOutputStream jos = new JarOutputStream(fos, manifest)) {
      addFilesFromJars(jars, jos);
      addNativesToJar(natives, jos);
      addJarSpliceLauncher(jos);
    }
    finally {
      fos.close();
    }
  }

  protected Manifest getManifest(String mainClass, String vmArgs) {
    Manifest manifest = new Manifest();
    Attributes attribute = manifest.getMainAttributes();
    attribute.putValue("Manifest-Version", "1.0");
    attribute.putValue("Main-Class", "org.ninjacave.jarsplice.JarSpliceLauncher");
    attribute.putValue("Launcher-Main-Class", mainClass);
    attribute.putValue("Launcher-VM-Args", vmArgs);

    return manifest;
  }

  protected void addFilesFromJars(String[] jars, JarOutputStream out) throws Exception
  {
      for (String jar : jars) {
          try (ZipFile jarFile = new ZipFile(jar)) {
              Enumeration entities = jarFile.entries();
              while (entities.hasMoreElements()) {
                  ZipEntry entry = (ZipEntry)entities.nextElement();
                  
                  if (entry.isDirectory()) {
                      if (!this.dirs.contains(entry.getName()))
                      {
                          this.dirs.add(entry.getName());
                      }
                  }
                  else if (!entry.getName().toLowerCase().startsWith("meta-inf"))
                  {
                      if (!entry.getName().toLowerCase().contains("JarSpliceLauncher"))
                      {
                          try (InputStream in = jarFile.getInputStream(jarFile.getEntry(entry.getName()))) {
                              out.putNextEntry(new ZipEntry(entry.getName()));
                              
                              while ((this.bufferSize = in.read(this.buffer, 0, this.buffer.length)) != -1) {
                                  out.write(this.buffer, 0, this.bufferSize);
                              }
                          }
                          out.closeEntry();
                      }
                  }
              }
          }
      }
  }

  protected void addNativesToJar(String[] natives, JarOutputStream out) throws Exception
  {
      for (String native1 : natives) {
          try (InputStream in = new FileInputStream(native1)) {
              out.putNextEntry(new ZipEntry(getFileName(native1)));
              while ((this.bufferSize = in.read(this.buffer, 0, this.buffer.length)) != -1) {
                  out.write(this.buffer, 0, this.bufferSize);
              }
          }
          out.closeEntry();
      }
  }

  protected void addJarSpliceLauncher(JarOutputStream out) throws Exception
  {
      try (InputStream in = JarSplice.class.getResourceAsStream("JarSpliceLauncher.class")) {
          out.putNextEntry(new ZipEntry("org/ninjacave/jarsplice/JarSpliceLauncher.class"));
          while ((this.bufferSize = in.read(this.buffer, 0, this.buffer.length)) != -1) {
              out.write(this.buffer, 0, this.bufferSize);
          } }
    out.closeEntry();
  }

  protected String getFileName(String ref) {
    ref = ref.replace('\\', '/');
    return ref.substring(ref.lastIndexOf('/') + 1);
  }
}
