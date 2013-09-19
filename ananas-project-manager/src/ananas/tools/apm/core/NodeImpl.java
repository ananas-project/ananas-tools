package ananas.tools.apm.core;

import java.io.File;
import java.util.Properties;

public class NodeImpl implements Node {

	private final File _file;
	private Properties _prop;

	public NodeImpl(File file) {
		this._file = file;
	}

	@Override
	public File getPath() {
		return this._file;
	}

	@Override
	public String getType() {
		return this.__getProp().getProperty(Const.Key.node_type) + "";
	}

	@Override
	public Properties getProperties() {
		return this.__getProp();
	}

	private Properties __getProp() {
		Properties prop = this._prop;
		if (prop == null) {
			prop = ApmUtil.loadProperties(this._file);
			this._prop = prop;
		}
		return prop;
	}

}
