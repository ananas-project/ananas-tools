package ananas.tools.apm.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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

	public static String getProperty(File file, Properties prop, String key,
			boolean canBeNull) {

		String value = prop.getProperty(key);
		if (value == null)
			if (!canBeNull) {
				throw new RuntimeException("need a property '" + key
						+ "' in file " + file);
			}
		return value;
	}

	public static String[] parseList(String string, char charSplite) {
		List<String> list = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();
		char[] chs = string.toCharArray();
		for (char ch : chs) {
			switch (ch) {
			case ' ':
			case 0x09:
			case 0x0a:
			case 0x0d:
				break;
			default:
				if (ch == charSplite) {
					if (sb.length() > 0) {
						list.add(sb.toString());
					}
					sb.setLength(0);
				} else {
					sb.append(ch);
				}
				break;
			}
		}
		if (sb.length() > 0) {
			list.add(sb.toString());
		}
		return list.toArray(new String[list.size()]);
	}

}
