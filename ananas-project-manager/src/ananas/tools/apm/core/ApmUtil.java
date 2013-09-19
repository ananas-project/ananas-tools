package ananas.tools.apm.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class ApmUtil {

	public static Properties loadProperties(File file) {
		Properties prop = new Properties();
		try {
			InputStream in = new FileInputStream(file);
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
