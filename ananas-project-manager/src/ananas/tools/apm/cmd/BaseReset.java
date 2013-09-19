package ananas.tools.apm.cmd;

import java.io.File;
import java.io.PrintStream;

import ananas.blueprint4.terminal.Command;
import ananas.blueprint4.terminal.ExecuteContext;
import ananas.blueprint4.terminal.Terminal;
import ananas.tools.apm.core.ApmCore;

public class BaseReset implements Command {

	@Override
	public void execute(ExecuteContext context) {
		Terminal terminal = context.getTerminal();
		ApmCore core = ApmCore.Agent.getInstance(terminal);
		core.setBasePath(null);
		File base = core.getBasePath();
		PrintStream out = terminal.getOutput();
		out.println("Reset BASE to " + base);
	}

}
