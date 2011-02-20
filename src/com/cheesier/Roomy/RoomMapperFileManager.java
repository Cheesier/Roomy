package com.cheesier.Roomy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.util.Vector;

public class RoomMapperFileManager {
	public static String savefile = "plugins/RoomMapper/rooms.txt";
	private static Logger log = Logger.getLogger("Minecraft");
	public static int desiredPara = 7;



	public static void makeFile() {
		try
		{
			if (new File(savefile).createNewFile()) // make file if not exists
			{
				BufferedWriter gfdw = new BufferedWriter(new FileWriter(savefile));
				gfdw.write("# Roomname:vec1X:vec1Y:vec1Z:vec2X:vec2Y:vec2Z");
				gfdw.newLine(); 
				gfdw.write("# The same roomname can appear on multiple times");
				gfdw.newLine();
				gfdw.close();
			}

		} catch (IOException localIOException) {}
	} // end makeFile()

	

	//Delete a room
	public static int deleteRoom(String name, String player) {
		int del = 0;		
		Iterator<SavedVector> rmit = RoomMapper.savedVectors.iterator();
		
		while(rmit.hasNext()) {
			SavedVector sv = rmit.next();
			if (sv.getName().equalsIgnoreCase(name)) {
				int index = RoomMapper.savedVectors.indexOf(sv);
				if (index >=0) {
					rmit.remove();
					del++;
					System.out.print(player + " Deleted " + sv.getName() + " " + del);
				}
			}
		}
		
		return del;
	} // end deleteRoom()

	

	public static void load() {
		try {

			/*	Sets up a file reader to read the file passed on the command
				line one character at a time */
			FileReader input = new FileReader(savefile);

			/* Filter FileReader through a Buffered read to read a line at a
			   time */
			BufferedReader bufRead = new BufferedReader(input);

			String line; 	// String that holds current file line

			// Read first line
			line = bufRead.readLine();
			
			// Empty the current list to fit the new one
			RoomMapper.savedVectors.clear();
			
			int linenr = 0;
			// Read through file one line at time. Print line data
			while (line != null){
				linenr++;
				if (!line.contains("#")) { // if comment, ignore line
					String[] s = line.split(":");
					if (s.length == desiredPara) {
						Vector v1 = new Vector(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
						Vector v2 = new Vector(Integer.parseInt(s[4]), Integer.parseInt(s[5]), Integer.parseInt(s[6]));
						RoomMapper.savedVectors.add(new SavedVector(s[0], v1, v2));
						//System.out.println(line);
					}
					else {
						if (s[0].length() >= 1) {
							log.warning("[RoomMapper] Saveformat of " + s[0] + " on line " + linenr + " seems to be corrupt.");
						}
						//System.out.println("Saveformat on line " + linenr + " seems to be corrupt.");
					}
				}
				line = bufRead.readLine(); // read next line
			}

			bufRead.close();

		}catch (ArrayIndexOutOfBoundsException e){
			/* If no file was passed on the command line, this expception is
			generated. A message indicating how to the class should be
			called is displayed */
			System.out.println("Usage: java ReadFile filename\n");
			e.printStackTrace();

		}catch (IOException e){
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		}
	} // end load()



	public static int save() {
		List<String> saveLines = new ArrayList<String>();
		
		//read comments from old file and save
		try {

			/*	Sets up a file reader to read the file passed on the command
				line one character at a time */
			FileReader input = new FileReader(savefile);

			/* Filter FileReader through a Buffered read to read a line at a
			   time */
			BufferedReader bufRead = new BufferedReader(input);

			String line; 	// String that holds current file line

			// Read first line
			line = bufRead.readLine();

			// Read through file one line at time. Print line data
			while (line != null){
				if (line.contains("#") || line.split(":").length < desiredPara) { // if comment, or corrupt, save line
					saveLines.add(line);
				}
				line = bufRead.readLine(); // read next line
			}

			bufRead.close();

		}catch (ArrayIndexOutOfBoundsException e){
			/* If no file was passed on the command line, this expception is
			generated. A message indicating how to the class should be
			called is displayed */
			System.out.println("Usage: java ReadFile filename\n");			

		}catch (IOException e){
			// If another exception is generated, print a stack trace
			e.printStackTrace();
		}
		
		
		//get the things to save from the HashMap at RoomMapper.java
		Iterator<SavedVector> rmit = RoomMapper.savedVectors.iterator();
		
		while(rmit.hasNext()) {
			saveLines.add(rmit.next().toString());
		}
		
		Iterator<String> slit = saveLines.iterator();
		
		// Write all we want to the file
		try
		{
			BufferedWriter gfdw = new BufferedWriter(new FileWriter(savefile));
			gfdw.flush();
			while (slit.hasNext()) {
				gfdw.write(slit.next());
				gfdw.newLine();
			}
			gfdw.close();

		} catch (IOException localIOException) {}
		
		return RoomMapper.savedVectors.size();
	} // end save()
}
