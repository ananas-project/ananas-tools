package ananas.tools.apm.core;

public class ProjectNodeImpl extends NodeWrapper implements ProjectNode {

	public ProjectNodeImpl(Node node) {
		super(node);
	}

	@Override
	public String getProjectName() {
		String name = this.getProperties().getProperty(Const.Key.project_name);
		return name;
	}

}
