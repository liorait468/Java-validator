package oop.ex6.main.sjavac_validator;

import oop.ex6.main.LineAnalyze;

import java.io.*;
import java.util.ArrayList;

/**
 * Read lines one by one from sjava file and insert them into an Array list,
 * Ignoring empty and comment lines.
 */
class SjavaFileReader {

	//-------- // Constants //-------- //
	private static final String FILE_TYPE_DIVISOR = "\\.";
	private static final String SJAVC_TYPE = "sjava";

	//-------- // Data-members //-------- //
	private ArrayList<String> fileArray;

	//-------- // Constructor =//-------- //

	/**
	 * Constructor of a SjavaFileReader
	 * @param sourceFilePath the path of the file
	 * @throws IOException
	 */
	SjavaFileReader(String sourceFilePath) throws IOException {

		this.fileArray = new ArrayList<>();
		File file = new File(sourceFilePath);

		if (!getFileExtension(file).equals(SJAVC_TYPE)) { // send warning - not sjava file type
			System.err.println("Warning: Running on a non s-java file"); //TODO use exception?
		}

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		while ((line = reader.readLine()) != null)
			if (!LineAnalyze.isLineEmptyOrComment(line))
				this.fileArray.add(line);

	}

	// ---------// Methods //-------- //
	/*get the extension of a file*/
	private String getFileExtension(File file) {
		String[] listOfTheString = file.getName().split(FILE_TYPE_DIVISOR);
		return (listOfTheString[listOfTheString.length - 1]);
	}

	//-------- // Getters //-------- //

	/**
	 * @return ArrayList containing all the line in the file, not including comments and empty lines.
	 */
	public ArrayList<String> getFileArray() {
		return fileArray;
	}
}
