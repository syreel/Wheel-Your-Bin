package com.yrsbradford.binapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtils {

	public static void writeFile(File file, String text) {
		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(text);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFile(File file){
		try {
				String sCurrentLine;
				BufferedReader br = new BufferedReader(new FileReader(file));
				String list = "";
				while ((sCurrentLine = br.readLine()) != null) {
					list = list+"\r\n"+(sCurrentLine);
				}
				
				br.close();
				
				return list;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
}
