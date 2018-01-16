package bigmovie;

import com.rivescript.macro.Subroutine;
import org.math.R.RenjinSession;
import org.math.R.RserveSession;
import org.math.R.RserverConf;
import org.math.R.Rsession;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Properties;

public class RSubroutine implements Subroutine {
	@Override
	public String call(com.rivescript.RiveScript rs, String[] args) {

		//@todo: https://github.com/bedatadriven/renjin-gradle-example/blob/master/src/test/java/org/renjin/gradle/RenjinGradleTest.java
		RserverConf conf = new RserverConf(" 127.0.0.1", 6311 ,"", "", new Properties());
		Rsession s = RserveSession.newInstanceTry(System.out,conf);
		s.installPackage("RMariaDB", true);

//		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
//		ScriptEngine renjin = scriptEngineManager.getEngineByName("Renjin");
//		try {
//			renjin.eval("library(RMariaDB)");
//
//		} catch (ScriptException e) {
//			e.printStackTrace();
//		}
		//Object o = s.eval("library(RMariaDB)");




		return "";
	}
}
