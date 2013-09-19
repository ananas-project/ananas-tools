package ananas.tools.apm.core;

import java.io.File;
import java.util.Map;

import ananas.blueprint4.terminal.Terminal;

public interface ApmCore {

	class Agent {

		public static ApmCore getInstance(Terminal terminal) {
			Map<String, Object> global = terminal.getGlobal();
			final String key = ApmCore.class.getName();
			ApmCore inst = (ApmCore) global.get(key);
			if (inst == null) {
				inst = new ApmCoreImpl();
				global.put(key, inst);
			}
			return inst;
		}

	}

	NodeManager getNodeManager();

	NodeManager getNodeManager(boolean update);

	/**
	 * @return a apm node file named 'apm.properties' , it's the root node.
	 * */
	File getRootNode();

	/**
	 * @return a dir or a file, begin of the search
	 * */
	File getBasePath();

	void setBasePath(File file);

	String getDestProjectName();

	void setDestProjectName(String name);

}
