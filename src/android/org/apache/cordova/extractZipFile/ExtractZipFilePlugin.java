package org.apache.cordova.extractZipFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.os.Environment;
import android.util.Log;

public class ExtractZipFilePlugin extends CordovaPlugin {

	@Override
	public boolean execute(String arg0, JSONArray args, CallbackContext callbackContext) {
		JSONArray result = new JSONArray();
		PluginResult pr;
		try {
			String filename = args.getString(0);
			File file = new File(filename);
			String[] dirToSplit=filename.split(File.separator);
			String dirToInsert=Environment.getExternalStorageDirectory().getAbsolutePath() + '/' + args.getString(1);
			File dir = new File(dirToInsert);
		    if (!dir.exists()) {
		        dir.mkdirs();
		    }
		    dirToInsert = dir.getAbsolutePath()+'/';
		    boolean t = dir.exists();
			BufferedOutputStream dest = null;
			BufferedInputStream is = null;
			ZipEntry entry;
			ZipFile zipfile;
			try {
				zipfile = new ZipFile(file);
				Enumeration e = zipfile.entries();
				while (e.hasMoreElements()) 
				{
					entry = (ZipEntry) e.nextElement();
					is = new BufferedInputStream(zipfile.getInputStream(entry));
					int count;
					byte data[] = new byte[102222];
					String fileName = dirToInsert + entry.getName();
					File outFile = new File(fileName);
					if (entry.isDirectory()) 
					{
						outFile.mkdirs();
					} 
					else 
					{
						FileOutputStream fos = new FileOutputStream(outFile);
						dest = new BufferedOutputStream(fos, 102222);
						while ((count = is.read(data, 0, 102222)) != -1)
						{
							dest.write(data, 0, count);
						}
						dest.flush();
						dest.close();
						is.close();
					}
				}
			pr = new PluginResult(PluginResult.Status.OK);
			callbackContext.sendPluginResult(pr);
			
			} catch (ZipException e1) {
				// TODO Auto-generated catch block
				pr = new PluginResult(PluginResult.Status.MALFORMED_URL_EXCEPTION);
				callbackContext.sendPluginResult(pr);
				return false;
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				pr = new PluginResult(PluginResult.Status.IO_EXCEPTION);
				return false;
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			pr = new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			callbackContext.sendPluginResult(pr);
			return false;
		}
		return true;
		
	}
}