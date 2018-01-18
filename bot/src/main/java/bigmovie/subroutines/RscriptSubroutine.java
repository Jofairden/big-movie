package bigmovie.subroutines;

import bigmovie.BotUtils;
import com.rivescript.macro.Subroutine;

import java.util.Objects;

/**
 * @author Daniel
 * The following subroutine can execute a given rscript
 * A full path can be given, or a relative path for resources
 */
public class RscriptSubroutine implements Subroutine {
	
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {
		
		String path;
		
		if (args[0].startsWith("resource:")) {
			// assume resource path
			path = Objects.requireNonNull(BotUtils.getResourcePath(
					String.format(
							"/rscript/%s",
							args[0].split(":")[1])
			)).toString();
		} else {
			// assume full path
			path = args[0];
		}
		
		if (path != null && !path.isEmpty()) {
			BotUtils.execRSript(path);
		}
		
		return "";
	}
}
