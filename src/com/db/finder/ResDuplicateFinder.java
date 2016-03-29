package com.db.finder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.db.finder.FileUtil.Log;

public class ResDuplicateFinder {

	private String mProjectDir;

	private HashMap<String,File> mResList = new HashMap<String,File>();

	private List<String> mEmptyDir = new ArrayList<String>();

	private List<String> mValueDir = new ArrayList<String>();
	
	private ArrayList<File> filesList = new ArrayList<File>();

	private ArrayList<File> mSrcDir = new ArrayList<File>();

	private File mAndroidManifest = null;

	public ResDuplicateFinder(String dir) {
		mProjectDir = dir;
	}

	public HashMap<String,File> checkDir() {
		if (!checkAndroidProjectDir(mProjectDir)) {
			return null;
		}		
		File root = new File(mProjectDir);
		if (root != null) {
			File[] files = root.listFiles();
			if (files != null && files.length > 0) {
				for (File file : files) {
					if (file.isDirectory()) {
						if (file.getName().contains("res")) {
							FileUtil.getFiles(file.getAbsolutePath(), filesList);
							for (int i = 0; i < filesList.size(); i++) {							
								String fileName = find_AndroidProjctFile(filesList.get(i));		
								if (fileName.length() > 0) {
									mResList.put(fileName, filesList.get(i));
									//Log.i(filesList.get(i).getName());
								}else{
									mValueDir.add(filesList.get(i).getAbsolutePath());
								}
							}
						} else if (file.getName().contains("src")) {
							FileUtil.getFiles(file.getAbsolutePath(), mSrcDir);
						}
					}
				}
			}
		}	
		findKeyWordOfFile(mSrcDir,mResList,"",false);
		if(mAndroidManifest != null)filesList.add(mAndroidManifest);
		findKeyWordOfFile(filesList,mResList,"xml",true);
		
		ValuesCompare.Check(mValueDir);
		
		return mResList;
	}

	public boolean checkAndroidProjectDir(String mProjectDir){
		File root = new File(mProjectDir);
		if (root != null) {
			File[] files = root.listFiles();
			if (files != null && files.length > 0) {
				boolean isAndroidProject = false;
				for (File file : files) {
					if (file.isFile()&& file.getAbsolutePath().contains("AndroidManifest")) {
						isAndroidProject = true;
						mAndroidManifest = file;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public String find_AndroidProjctFile(File commonFile) {	
		String sufix="R.";
		String absolutePath = commonFile.getAbsolutePath();
		absolutePath =absolutePath.replace(commonFile.getName(), "");
		if (absolutePath.contains("drawable")) {
			sufix += "drawable.";
		}else if (absolutePath.contains("anim")) {
			sufix += "anim.";
		}else if (absolutePath.contains("layout")){
			sufix += "layout.";
	    }else if (absolutePath.contains("menu")){
			sufix += "menu.";
	    }else if (absolutePath.contains("values")){
	    	return "";
	    }else if (absolutePath.contains("color")){
			sufix += "color.";
	    }	
		String fileNameString = commonFile.getName();
		if (fileNameString.contains("xml")) {
			fileNameString = fileNameString.replace(".xml", "");
		}else if (fileNameString.contains("jpg")) {
			fileNameString = fileNameString.replace(".jpg", "");
		}else if (fileNameString.contains("9.png")) {
			fileNameString = fileNameString.replace(".9.png", "");
		}else if (fileNameString.contains("png")) {
			fileNameString = fileNameString.replace(".png", "");
		}
		return sufix+fileNameString;
	}
	
	public void findKeyWordOfFile(List<File> files , HashMap<String,File> filesCollection, String fileEnd, boolean isRepale){
		for (File resFile : files) {			
			if (resFile.getAbsolutePath().contains(fileEnd)) {
				String tempString = FileUtil.read(resFile.getAbsolutePath());			
				Set<String> resKey = mResList.keySet();
				List<String> needRemoveKeyList = new ArrayList<String>();
				for (String string : resKey) {	
					String resString = string;
					if (isRepale) {
						resString = string.replace(".", "/");
						resString = resString.replace("R/", "@");
					}
					if (tempString.contains(resString)) {
						//Log.i("this res is use:"+resString);
						needRemoveKeyList.add(string);
					}
				}
				for (int j = 0; j < needRemoveKeyList.size(); j++) {
					mResList.remove(needRemoveKeyList.get(j));
				}
			}	
		}
	}
}
