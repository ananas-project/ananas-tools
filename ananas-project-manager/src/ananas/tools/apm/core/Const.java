package ananas.tools.apm.core;

public interface Const {

	String node_file_name = "apm.properties";

	interface Key {
		String node_type = "node.type";
		String project_name = "project.name";

		String cp_dest_dir = "cp.dest.dir";
		String cp_src_project_list = "cp.src.project.list";
		String cp_src_dir_list = "cp.src.dir.list";
	}

	interface NodeType {
		String root = "workspace";
		String project = "project";
	}

}
