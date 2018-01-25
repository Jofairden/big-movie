package bigmovie.subroutines;

import bigmovie.Bot;
import bigmovie.BotUtils;
import bigmovie.PrepArg;
import com.rivescript.macro.Subroutine;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * @author Jildert
 * Writes a file and calls other subroutines depening on given context
 */
public class WriteExecuteSendR implements Subroutine {
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {
		StringBuilder sb = new StringBuilder();
		String path;
		if (args.length > 1) {
			
			if (args[0].equals("context:genres") && args.length > 2 && args.length < 14) {
				path = args[1];
				
				for (int i = 2; i < args.length; i++) {
					
					args[i] = args[i].replace(",", "");
					if (args[i].equals("and")) continue;
					
					PrepArg[] prepargsC = new PrepArg[] {new PrepArg<String>("%" + args[i].trim() + "%")};
					if (BotUtils.execSqlQuery("getGenre.sql", prepargsC).equals("")) {
						Bot.messageSubroutine.call(rs, new String[] {"Genre: " + args[i].trim() + " not found"});
						return "";
					}
					
					sb.append(BotUtils.firstToUpper(args[i])).append("\n");
				}
				WriteFile(sb, path);
				Bot.rscriptSubroutine.call(rs, new String[] {"resource:Vraag9.R"});
				Bot.messageSubroutine.call(rs, new String[] {"context:file", "resource:vraag9.png"});
				Bot.messageSubroutine.call(rs, new String[] {"Produced by R."});
			} else if (args[0].equals("context:genres")) {
				Bot.messageSubroutine.call(rs, new String[] {"Please add between 1 and 11 genres, splitted by space"});
			} else {
				
				//write temp country for R to read
				path = args[0];
				
				for (int i = 1; i < args.length; i++) {
					sb.append(args[i]).append(" ");
				}
				
				//check if country exists in database
				PrepArg[] prepargsC = new PrepArg[] {new PrepArg<String>("%" + sb.toString().trim() + "%")};
				if (BotUtils.execSqlQuery("getCountry.sql", prepargsC).equals("")) {
					Bot.messageSubroutine.call(rs, new String[] {"Country: " + sb.toString().trim() + " not found"});
				} else {
					WriteFile(sb, path);
					Bot.rscriptSubroutine.call(rs, new String[] {"resource:Vraag8.R"});
					Bot.messageSubroutine.call(rs, new String[] {"context:file", "resource:vraag8.png"});
					Bot.messageSubroutine.call(rs, new String[] {"Produced by R."});
				}
			}
		}
		return "";
	}

	//Writing the string in stringbuilder to the given path, for rscript to read and process.
	private void WriteFile(StringBuilder sb, String path) {
		try {
			String data = sb.toString();
			data = data.trim();
			BotUtils.writeFile(path, data);
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			Bot.logger.error(e.toString());
		}
	}
}
