package com.tssa.util;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.tssa.common.mode.AXMLPrinter;

public class FileUtil
{
  public static InputStream getInputStreamFromZip(ZipFile zipFile, String fileName)
  {
    InputStream is = null;
    try {
      ZipEntry entry = zipFile.getEntry(fileName);
      is = zipFile.getInputStream(entry);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return is;
  }

  public static InputStream xmlPrint(InputStream stream)
  {
    AXMLPrinter xmlPrinter = new AXMLPrinter();
    xmlPrinter.startPrinf(stream);
    try {
      return new ByteArrayInputStream(xmlPrinter.getBuf().toString()
        .getBytes("UTF-8"));
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return null;
  }
}
