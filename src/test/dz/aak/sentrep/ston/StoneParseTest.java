package dz.aak.sentrep.ston;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import dz.aak.sentrep.ston.ReqCreator;

public class StoneParseTest {

	static String testFile = "ston/that.ston";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String specif = readFile(testFile);
		
		//specif = "{ jjjjj }";
		//System.out.println(specif + "\n----------\n");
		ReqParser parser = new ReqParser(specif);
		System.out.println(parser.parsed());
		System.out.println(parser.getPlayers().size());
		
		ReqCreator rq = new ReqCreator(parser.getPlayers(), parser.getActions());
		
		
		String specif2 = rq.getStructuredRequest();
		System.out.println(specif2 + "\n----------\n");
		//System.out.println(specif2.equals(specif));
	}


	public static String readFile (String f) {
		try {
			String contents = "";

			BufferedReader input = new BufferedReader(new FileReader(f));

			
			for(String line = input.readLine(); line != null; line = input.readLine()) {
				contents += line + "\n";
			}
			input.close();

			return contents;

		} catch(IOException e) {
			e.printStackTrace();
			System.exit(1);
			return null;
		} 
	}

}
