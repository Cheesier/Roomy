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


/*TODO Fix the save and load
 * 
 * Alot here need to be rewritter from scratch, if not all
 * 
 * Loading and saving to the file is a disaster, it works, but it aint pretty
 * 
*/

public class RoomyFileManager {
	public static String savefile = "plugins/Roomy/rooms.txt";
	private static Logger log = Logger.getLogger("Minecraft");
	public static int desiredPara = 7;


	// Does not make a folder if needed
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

	

	// Delete a room
	// Works, happy with it
	public static int deleteRoom(String name, String player) {
		int del = 0;		
		Iterator<SavedRoom> rmit = Roomy.savedRooms.iterator();
		
		while(rmit.hasNext()) {
			SavedRoom sv = rmit.next();
			if (sv.getName().equalsIgnoreCase(name)) {
				int index = Roomy.savedRooms.indexOf(sv);
				if (index >=0) {
					rmit.remove();
					del++;
					System.out.print(player + " Deleted " + sv.getName() + " " + del);
				}
			}
		}
		
		return del;
	} // end deleteRoom()

	
	// As said before, works but sucks
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
			Roomy.savedRooms.clear();
			
			int linenr = 0;
			// Read through file one line at time. Print line data
			while (line != null){
				linenr++;
				if (!line.contains("#")) { // if comment, ignore line
					String[] s = line.split(":");
					if (s.length == desiredPara) {
						// get the line into 2 vectors
						Vector v1 = new Vector(Integer.parseInt(s[1]), Integer.parseInt(s[2]), Integer.parseInt(s[3]));
						Vector v2 = new Vector(Integer.parseInt(s[4]), Integer.parseInt(s[5]), Integer.parseInt(s[6]));
						
						// Make the actual saved room
						Roomy.savedRooms.add(new SavedRoom(s[0], v1, v2));
					}
					else {
						if (s[0].length() >= 1) {
							log.warning("[Roomy] Saveformat of " + s[0] + " on line " + linenr + " seems to be corrupt.");
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


	// Puts all the lines not containing a valid room at the top, the filling it with rooms
	public static int save() {
		// temporary storage for the rooms
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
		
		
		//get the things to save from the HashMap at Roomy.java
		Iterator<SavedRoom> rmit = Roomy.savedRooms.iterator();
		
		while(rmit.hasNext()) {
			saveLines.add(rmit.next().toSaveFormat());
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
		
		return Roomy.savedRooms.size();
	} // end save()
}
