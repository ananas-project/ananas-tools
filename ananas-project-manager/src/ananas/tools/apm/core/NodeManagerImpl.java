package ananas.tools.apm.core;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NodeManagerImpl implements NodeManager {

	private final List<Node> _nodes = new ArrayList<Node>();
	private final Map<String, ProjectNode> _projects = new HashMap<String, ProjectNode>();
	private final Map<File, ProjectNode> _conflict_projects = new HashMap<File, ProjectNode>();

	public NodeManagerImpl(List<File> nodes) {
		for (File file : nodes) {
			Node node = new NodeImpl(file);
			this._nodes.add(node);
			if (Const.NodeType.project.equals(node.getType())) {
				final ProjectNode prj = new ProjectNodeImpl(node);
				final String prjName = prj.getProjectName().trim();
				final ProjectNode pold = this._projects.get(prjName);
				this._projects.put(prjName, prj);
				if (pold != null) {
					this._conflict_projects.put(pold.getPath(), pold);
					this._conflict_projects.put(prj.getPath(), prj);
					System.err.println("warning: conflict project name '"
							+ prjName + "' of the two projects");
					System.err.println("    project-1 = " + pold.getPath());
					System.err.println("    project-2 = " + prj.getPath());
				} else {
				}
			}
		}
	}

	public static NodeManager newInstance(File root) {
		Builder builder = new Builder();
		return builder.build(root);
	}

	private static class Builder implements FileHandler {

		final List<File> _node_list;

		public Builder() {
			_node_list = new ArrayList<File>();
		}

		public NodeManager build(File root) {

			if (root == null) {
				throw new RuntimeException(
						"cannot find ROOT node (node.type=workspace) file '"
								+ Const.node_file_name + "' !");
			}

			__listAllFilesInDir(root.getParentFile(), this, 64);

			return new NodeManagerImpl(this._node_list);
		}

		@Override
		public void onFile(File path) {
			if (Const.node_file_name.equals(path.getName())) {
				// System.out.println("    node[" + (count++) + "] " + path);
				this._node_list.add(path);
			}
		}

		private void __listAllFilesInDir(File path, FileHandler h,
				int depthLimit) {
			if (path == null)
				return;
			h.onFile(path);
			if (depthLimit < 0)
				throw new RuntimeException("the path is too deep! path=" + path);
			if (path.exists())
				if (path.isDirectory()) {
					File[] list = path.listFiles();
					if (list != null) {
						for (File sub : list) {
							__listAllFilesInDir(sub, h, depthLimit - 1);
						}
					}
				}
		}

	}

	interface FileHandler {
		void onFile(File path);
	}

	@Override
	public List<Node> listNodes() {
		return new ArrayList<Node>(this._nodes);
	}

	@Override
	public ProjectNode getProject(String name) {
		return this._projects.get(name);
	}

	@Override
	public List<ProjectNode> listProjects() {
		return new ArrayList<ProjectNode>(this._projects.values());
	}

	@Override
	public List<ProjectNode> listConflictProjects() {
		return new ArrayList<ProjectNode>(this._conflict_projects.values());
	}

}
