package com.tssa.util;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipFile;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import com.tssa.common.mode.ApkInfo;

public class ApkUtil
{
  public static final String NAME_SPACE = "android";
  public static final String VERSION_NAME = "versionName";
  public static final String VERSION_CODE = "versionCode";
  public static final String APK_PACAKGE = "package";
  public static final String MIN_SDK_VERSION = "minSdkVersion";
  public static final String ELEM_USES_PERMISSION = "uses-permission";
  public static final String ELEM_USES_SDK = "uses-sdk";
  public static final Namespace ANDROID_NAMESPACE = Namespace.getNamespace("http://schemas.android.com/apk/res/android");

  @SuppressWarnings("null")
public static ApkInfo getApkInfoFromFile(File file)
  {
    InputStream xmlIs = null;
    ZipFile zipFile = null;
    InputStream stream = null;
    try {
      zipFile = new ZipFile(file);
      stream = FileUtil.getInputStreamFromZip(zipFile, 
        "AndroidManifest.xml");
      xmlIs = FileUtil.xmlPrint(stream);
    } catch (IOException e) {
      e.printStackTrace();
      try
      {
        stream.close();
        zipFile.close();
      } catch (IOException e1) {
        e.printStackTrace();
      }
    }
    finally
    {
      try
      {
        stream.close();
        zipFile.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return manifestSax(xmlIs);
  }
  
  
  
  @SuppressWarnings("null")
public static ApkInfo getApkInfo(String apkPath)
  {
    InputStream xmlIs = null;
    ZipFile zipFile = null;
    InputStream stream = null;
    try {
      zipFile = new ZipFile(apkPath);
      stream = FileUtil.getInputStreamFromZip(zipFile, 
        "AndroidManifest.xml");
      xmlIs = FileUtil.xmlPrint(stream);
    } catch (IOException e) {
      e.printStackTrace();
      try
      {
        stream.close();
        zipFile.close();
      } catch (IOException e1) {
        e.printStackTrace();
      }
    }
    finally
    {
      try
      {
        stream.close();
        zipFile.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return manifestSax(xmlIs);
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
public static ApkInfo manifestSax(InputStream manifestStream)
  {
    ApkInfo apk = new ApkInfo();
    SAXBuilder builder = new SAXBuilder();
    try {
      Document document = builder.build(manifestStream);
      Element root = document.getRootElement();

      apk.setVersionCode(root.getAttributeValue("versionCode", 
        ANDROID_NAMESPACE));
      apk.setVersionName(root.getAttributeValue("versionName", 
        ANDROID_NAMESPACE));

      String pkgName = root.getAttributeValue("package");
      apk.setApkPackage(pkgName);
      apk.setApkName(pkgName.substring(pkgName.lastIndexOf('.') + 1));

      Element elemUseSdk = root.getChild("uses-sdk");
      apk.setMinSdkVersion(elemUseSdk.getAttributeValue("minSdkVersion", 
        ANDROID_NAMESPACE));

      List<Element> listPermission = root.getChildren("uses-permission");
      List permissions = new ArrayList();
      for (Element element : listPermission) {
        permissions.add(element.getAttributeValue("name", 
          ANDROID_NAMESPACE));
      }
      apk.setUses_permission(permissions);
      System.out.println(apk);
    } catch (JDOMException e) {
      e.printStackTrace();
      try
      {
        manifestStream.close();
      } catch (IOException e1) {
        e.printStackTrace();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
      try
      {
        manifestStream.close();
      } catch (IOException e1) {
        e.printStackTrace();
      }
    }
    finally
    {
      try
      {
        manifestStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return apk;
  }
}

