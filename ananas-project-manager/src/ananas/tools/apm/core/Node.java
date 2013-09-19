package ananas.tools.apm.core;

import java.io.File;
import java.util.Properties;

public interface Node {

	File getPath();

	String getType();

	Properties getProperties();

}
