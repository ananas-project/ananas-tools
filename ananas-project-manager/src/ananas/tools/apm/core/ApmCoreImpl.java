package ananas.tools.apm.core;

import java.io.File;
import java.net.URL;
import java.util.Properties;

final class ApmCoreImpl implements ApmCore {

	private File _base_path;
	private File _root_node;
	private NodeManager _node_man;

	@Override
	public File getRootNode() {
		File path = this._root_node;
		if (path == null) {
			path = this.getBasePath();
			for (; path != null; path = path.getParentFile()) {
				final File node_file = new File(path, Const.node_file_name);
				if (node_file.exists()) {
					Properties prop = ApmUtil.loadProperties(node_file);
					String type = prop.getProperty(Const.Key.node_type);
					if (Const.NodeType.root.equals(type)) {
						path = node_file;
						break;
					}
				}
			}
			this._root_node = path;
		}
		return path;
	}

	@Override
	public File getBasePath() {
		File ret = this._base_path;
		if (ret == null) {
			try {
				URL url = this.getClass().getProtectionDomain().getCodeSource()
						.getLocation();
				ret = new File(url.toURI());
				this._base_path = ret;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public void setBasePath(File file) {
		this._base_path = file;
		this._root_node = null;
		this._node_man = null;
	}

	@Override
	public NodeManager getNodeManager() {
		NodeManager nm = this.getNodeManager(false);
		if (nm == null) {
			nm = this.getNodeManager(true);
		}
		return nm;
	}

	@Override
	public NodeManager getNodeManager(boolean update) {
		if (update) {
			File root = this.getRootNode();
			NodeManager pnew = NodeManagerImpl.newInstance(root);
			this._node_man = pnew;
			return pnew;
		} else {
			return this._node_man;
		}
	}

}
