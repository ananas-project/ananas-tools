package ananas.tools.apm.cmd;

import java.io.PrintStream;
import java.util.List;

import ananas.blueprint4.terminal.Command;
import ananas.blueprint4.terminal.ExecuteContext;
import ananas.blueprint4.terminal.Terminal;
import ananas.tools.apm.core.ApmCore;
import ananas.tools.apm.core.Node;
import ananas.tools.apm.core.NodeManager;
import ananas.tools.apm.core.ProjectNode;

public class ListSub implements Command {

	@Override
	public void execute(ExecuteContext context) {
		Terminal terminal = context.getTerminal();
		ApmCore core = ApmCore.Agent.getInstance(terminal);

		PrintStream out = terminal.getOutput();
		out.println("List all projects ...");

		NodeManager node_man = core.getNodeManager();

		out.println("  Nodes:");
		List<Node> nodes = node_man.listNodes();
		int index = 0;
		for (Node node : nodes) {
			out.println("    " + (++index) + ". " + node.getType() + " node "
					+ node.getPath());
		}

		out.println("  Projects:");
		index = 0;
		List<ProjectNode> plist = node_man.listProjects();
		for (ProjectNode prj : plist) {
			out.println("    " + (++index) + ". " + prj.getProjectName());
		}

		out.println("done!");

	}

}
