package uk.co.balasuriya.serviceont.util;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class APKExpansionFileHandler {

    // The shared path to all app expansion files
    private static Context ctx = ApplicationContext.getAppContext();
    /**
     * This is the android:versionCode defined in the manifest file
     */
   

    /**
     * 
     * @return file path if there is a file exsist.
     */
    public static ZipResourceFile getAPKExpansionFiles() {
	Log.d("APKExpansionFileHandler:getAPKExpansionFiles", "reading apk expansion file");
	String packageName = ctx.getPackageName();
	ZipResourceFile zipfile=null;
	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
	    // Build the full path to the app's expansion files
	    File root = Environment.getExternalStorageDirectory();
	    File expPath = new File(root.toString() + ApplicationCofig.EXPANSION_FILE_HOME_DIR);
	    Log.d("APKExpansionFileHandler:getAPKExpansionFiles", "apk expansion directory path" + expPath);
	    // Check that expansion file path exists
	    if (expPath.exists()) {
		String strMainPath = expPath + File.separator + "main." + ApplicationCofig.APPLICATION_VERSION_NUMBER + "." + packageName + ".obb";
		Log.d("APKExpansionFileHandler:getAPKExpansionFiles", "main file path" + strMainPath);
		File main = new File(strMainPath);
		if (main.isFile()) {
		    Log.d("APKExpansionFileHandler:getAPKExpansionFiles", "apk expansion file available");
		  return  getResourceZipFile(strMainPath);
		}
	    }
	}
	Log.d("APKExpansionFileHandler:getAPKExpansionFiles", "No apk expansion file available");
	return zipfile;
    }

    private static ZipResourceFile getResourceZipFile(String expansionFiles) {
	ZipResourceFile resourcefile =null;
	    if (null != expansionFiles){
		
		try {
		    Log.d("APKExpansionFileHandler:getAPKExpansionFiles", "zip file found");
		    return new ZipResourceFile(expansionFiles);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    Log.d("APKExpansionFileHandler:getAPKExpansionFiles", "Exception occured while getting zip file"+e);
		}
	    }
		
	return resourcefile;
    }

}
