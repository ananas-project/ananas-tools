package ananas.tools.apm.cmd;

import java.io.File;
import java.io.PrintStream;

import ananas.blueprint4.terminal.Command;
import ananas.blueprint4.terminal.ExecuteContext;
import ananas.blueprint4.terminal.Terminal;
import ananas.tools.apm.core.ApmCore;

public class Base implements Command {

	@Override
	public void execute(ExecuteContext context) {

		Terminal terminal = context.getTerminal();
		ApmCore core = ApmCore.Agent.getInstance(terminal);

		File base = core.getBasePath();
		File root = core.getRootNode();

		PrintStream out = terminal.getOutput();
		out.println("Current BASE & ROOT path:");
		out.println("    BASE = " + base);
		out.println("    ROOT = " + root);

	}

}
