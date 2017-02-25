package kariminf.sentrep.ston;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import kariminf.sentrep.ston.request.ReqCreator;
import kariminf.sentrep.ston.request.ReqParser;


public class StoneParseTest {

	static String testFile = "ressources/exp/that.ston";
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
		
		ReqCreator rq = 
				new ReqCreator(parser.getPlayers(), parser.getActions(), parser.getSentences());
		
		
		String specif2 = rq.getStructuredRequest();
		System.out.println(specif2 + "\n----------\n");
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
