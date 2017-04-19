package com.tssa.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.dd.plist.NSDictionary;
import com.dd.plist.NSString;
import com.dd.plist.PropertyListParser;
import com.tssa.appVersion.pojo.AppVersion;
import com.tssa.common.mode.ApkInfo;

public class FileParseUtil {

	private final static int ANDROID_FILE = 0x00001;
	private final static int IOS_FILE = 0x00002;
	private final static int ELSE_FILE = 0x00003;

	private static Logger logger = Logger.getLogger(FileParseUtil.class);

	public static int check(File file) throws Exception {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file);
			if (zipFile.getEntry("AndroidManifest.xml") != null) {
				return ANDROID_FILE; // android file
			} else {
				Enumeration<? extends ZipEntry> entries = zipFile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry tmpEntry = entries.nextElement();
					String entryName = tmpEntry.getName();
					if (entryName.endsWith(".app/Info.plist")) {
						return IOS_FILE; // ios 
					}
				}
				return ELSE_FILE;
			}
		} catch (Exception e) {
			throw new Exception("the file is not Android or ios file error!!");
			// return ELSE_FILE;
		}
	}

	/**
	 * @param file
	 * @param app
	 * @param appVersion
	 * @param handleApp
	 *            是否同时对AppBase的相应字段填充值
	 * @throws Exception
	 */
	public static AppVersion parseFile(File file, AppVersion app) throws Exception {
		if ("ios".equalsIgnoreCase(app.getPlatform())
				&& check(file) == IOS_FILE) {
			return FileParseUtil.parseIOSFile(file, app);
		} else if ("android".equalsIgnoreCase(app.getPlatform())
				&& check(file) == ANDROID_FILE) {
			return FileParseUtil.parseAndroidFile(file, app);
		} else {
			throw new Exception(
					"the file not attachment with the AppBase platform ,error!!!!");
		}
	}

	public static AppVersion parseAndroidFile(File apk, AppVersion app)
			throws Exception {
		ApkInfo apkInfo = ApkUtil.getApkInfoFromFile(apk);
		String version = apkInfo.getVersionName();
		String build = apkInfo.getVersionCode();
		String identifier = apkInfo.getApkPackage();

		String buildStr = build.toString();
		if (build != null) {
			app.setBuild((int) Double.parseDouble(buildStr));
		}
		app.setVersion(version);
		app.setIdentifier(identifier);
		logger.info("Android file parse result = { build = " + build
				+ " version= " + version + "identifier =  " + identifier + "}");
		return app;
	}

	public static AppVersion parseIOSFile(File ios, AppVersion app) throws Exception {
		ZipFile zip = new ZipFile(ios);
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			String entryName = entry.getName();
			if (entryName.endsWith(".app/Info.plist")) {
				NSDictionary dict = (NSDictionary) PropertyListParser.parse(zip
						.getInputStream(entry));
				NSString build = (NSString) dict
						.objectForKey("CFBundleVersion");
				NSString version = (NSString) dict
						.objectForKey("CFBundleShortVersionString");
				NSString identifier = (NSString) dict
						.objectForKey("CFBundleIdentifier");

				String buildStr = build.toString();
				if (build != null) {
					app.setBuild((int) Double.parseDouble(buildStr));
				}

				if (version != null) {
					app.setVersion(version.toString());
				} else {
					app.setVersion(buildStr.toString());
				}
				app.setIdentifier(identifier.toString());

				logger.info("IOS file parse result = { build = " + build
						+ " version= " + version + " identifier= " + identifier
						+ "}");
			}
		}
		return app;
	}

	public static String getPlistTemplate() {
		InputStream is = FileParseUtil.class
				.getResourceAsStream("/template.plist");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String tempString = baos.toString();
		return tempString;
	}

//	public static File savePlist2Temp(String filename, String content) {
//		String path = "/data-scm/Cube/files";
//		if (!new File(path).exists()) {
//			new File(path).mkdirs();
//		}
//		File plistFile = new File(path + File.separator + filename);
//		if (plistFile.exists()) {
//			plistFile.delete();
//		}
//		BufferedWriter output = null;
//		try {
//			plistFile.createNewFile();
//			output = new BufferedWriter(new FileWriter(plistFile));
//			output.write(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				output.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		return plistFile;
//	}
}
