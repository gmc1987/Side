package com.tssa.common.mode;

import java.util.List;

public class ApkInfo
{
  private String versionCode = null;

  private String versionName = null;

  private String apkPackage = null;

  private String minSdkVersion = null;

  private String apkName = null;

  private List<String> uses_permission = null;

  public String getVersionCode()
  {
    return this.versionCode;
  }

  public void setVersionCode(String versionCode)
  {
    this.versionCode = versionCode;
  }

  public String getVersionName()
  {
    return this.versionName;
  }

  public void setVersionName(String versionName)
  {
    this.versionName = versionName;
  }

  public String getApkPackage()
  {
    return this.apkPackage;
  }

  public void setApkPackage(String apkPackage)
  {
    this.apkPackage = apkPackage;
  }

  public String getMinSdkVersion()
  {
    return this.minSdkVersion;
  }

  public void setMinSdkVersion(String minSdkVersion)
  {
    this.minSdkVersion = minSdkVersion;
  }

  public String getApkName()
  {
    return this.apkName;
  }

  public void setApkName(String apkName)
  {
    this.apkName = apkName;
  }

  public List<String> getUses_permission()
  {
    return this.uses_permission;
  }

  public void setUses_permission(List<String> uses_permission)
  {
    this.uses_permission = uses_permission;
  }

  public String toString()
  {
    return "ApkInfo [versionCode=" + this.versionCode + ", versionName=" + 
      this.versionName + ", apkPackage=" + this.apkPackage + 
      ", minSdkVersion=" + this.minSdkVersion + ", apkName=" + this.apkName + 
      ", uses_permission=" + this.uses_permission + "]";
  }
}
