package ananas.tools.apm.core;

import java.io.File;
import java.util.Properties;

public class NodeWrapper implements Node {

	private final Node _inner;

	public NodeWrapper(Node inner) {
		this._inner = inner;
	}

	@Override
	public File getPath() {
		return this._inner.getPath();
	}

	@Override
	public String getType() {
		return this._inner.getType();
	}

	@Override
	public Properties getProperties() {
		return this._inner.getProperties();
	}

}
