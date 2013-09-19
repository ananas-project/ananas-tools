package ananas.tools.apm.core;

public interface Const {

	String node_file_name = "apm.properties";

	interface Key {
		String node_type = "node.type";
		String project_name = "project.name";
	}

	interface NodeType {
		String root = "workspace";
		String project = "project";
	}

}
