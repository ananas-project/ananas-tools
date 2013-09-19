package ananas.tools.apm.core;

import java.util.List;

public interface NodeManager {

	List<Node> listNodes();

	List<ProjectNode> listProjects();

	List<ProjectNode> listConflictProjects();

	ProjectNode getProject(String name);

}
