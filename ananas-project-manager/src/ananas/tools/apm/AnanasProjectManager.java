package ananas.tools.apm;

import java.util.Properties;

import ananas.blueprint4.terminal.Terminal;
import ananas.blueprint4.terminal.TerminalFactory;
import ananas.blueprint4.terminal.loader.CommandLoader;
import ananas.lib.util.PropertiesLoader;

public class AnanasProjectManager {

	public static void main(String[] arg) {

		(new AnanasProjectManager()).run();

	}

	private void run() {

		String title = ("Ananas Project Manager - v0.1");
		// System.out.println(title);

		{
			PropertiesLoader.Util.loadPropertiesToSystem(this,
					"system.properties");
		}
		{

			// create terminal
			TerminalFactory tf = TerminalFactory.Agent.newInstance();
			Terminal t = tf.newTerminal(null);

			// load commands
			// t.getCommandRegistrar().register("git.init", new MyCmdGitInit());
			CommandLoader ldr = t.getCommandLoaderFactory().newLoader(t);
			ldr.load("resource:///ananas/tools/apm/terminal-commands.xml");

			// set properties for terminal
			// long now = System.currentTimeMillis();
			String prompt = ">";
			String welcome = title;
			String bye = "";
			Properties prop = t.getProperties();
			prop.setProperty("terminal.welcome", welcome);
			prop.setProperty("terminal.bye", bye);
			prop.setProperty("terminal.prompt", prompt);

			t.getRunnable().run();

		}

		System.out.println("exited!");

	}

}
