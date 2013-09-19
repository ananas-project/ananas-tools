package ananas.tools.apm.cmd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import ananas.blueprint4.terminal.Command;
import ananas.blueprint4.terminal.ExecuteContext;
import ananas.blueprint4.terminal.Terminal;
import ananas.tools.apm.core.ApmCore;
import ananas.tools.apm.core.ApmUtil;
import ananas.tools.apm.core.Const;
import ananas.tools.apm.core.NodeManager;
import ananas.tools.apm.core.ProjectNode;

public class SrcCopy implements Command {

	@Override
	public void execute(ExecuteContext context) {

		final Terminal terminal = context.getTerminal();
		final ApmCore core = ApmCore.Agent.getInstance(terminal);
		final PrintStream out = terminal.getOutput();

		final String[] param = context.getParameters();
		String dpn = null;
		if (param.length >= 2) {
			dpn = param[1];
			core.setDestProjectName(dpn);
		} else {
			dpn = core.getDestProjectName();
		}
		ProjectNode dest_prj = core.getNodeManager().getProject(dpn);
		if (dest_prj == null) {
			out.println("cannot find project named '" + dpn + "'");
			return;
		}

		out.println("Copy all needed src files to DEST project '" + dpn
				+ "' ...");

		Dest dest = new Dest(dest_prj);
		dest.doInit(out);
		dest.doClear(out);
		dest.doInit(out);
		dest.doCopy(out, core);

		out.println("done!");

	}

	private static class Util {

		public static void copyDir(File src, File dest, int depthLimit) {

			if (src == null)
				return;
			if (dest == null)
				return;

			if (!dest.exists())
				dest.mkdirs();

			File[] list = src.listFiles();
			if (list == null)
				return;

			for (File ch_src : list) {
				String name = ch_src.getName();
				final File ch_dest = new File(dest, name);
				if (ch_src.isDirectory()) {
					Util.copyDir(ch_src, ch_dest, depthLimit - 1);
				} else {
					Util.copyFile(ch_src, ch_dest);
				}
			}

		}

		private static void copyFile(File src, File dest) {
			try {
				// System.err.println("        write file " + dest);
				InputStream in = new FileInputStream(src);
				OutputStream out = new FileOutputStream(dest);
				byte[] buf = new byte[1024];
				for (int cb = in.read(buf); cb > 0; cb = in.read(buf)) {
					out.write(buf, 0, cb);
				}
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class Src {

		private final ProjectNode _project;

		public Src(ProjectNode prj) {
			this._project = prj;
		}

		public void doCopy(PrintStream out, File dest) {
			List<File> src_list = this.__listAllSrcDir();
			for (File src : src_list) {
				out.println("    cp: " + dest + " << " + src);
				Util.copyDir(src, dest, 64);
			}
		}

		private List<File> __listAllSrcDir() {
			List<File> list = new ArrayList<File>();
			final File base = this._project.getPath().getParentFile();
			String dirs_string = ApmUtil.getProperty(this._project.getPath(),
					this._project.getProperties(), Const.Key.cp_src_dir_list,
					false);
			String[] dirs_array = ApmUtil.parseList(dirs_string, ';');
			for (String offset : dirs_array) {
				list.add(new File(base, offset));
			}
			return list;
		}

	}

	class Dest {

		private final ProjectNode _project;
		private File _dest_dir;

		public Dest(ProjectNode project) {
			this._project = project;
		}

		public void doCopy(PrintStream out, ApmCore core) {

			File dest = this.__getDestDir();
			NodeManager node_man = core.getNodeManager();

			String[] src_list = this.__listSrcProjects();
			for (String name : src_list) {
				ProjectNode prj = node_man.getProject(name);
				if (prj == null) {
					throw new RuntimeException("cannot find SRC project : "
							+ name);
				}
				Src src = new Src(prj);
				src.doCopy(out, dest);
			}
		}

		private String[] __listSrcProjects() {
			File file = this._project.getPath();
			Properties prop = this._project.getProperties();
			String key = Const.Key.cp_src_project_list;
			String value = ApmUtil.getProperty(file, prop, key, false);
			String[] array = ApmUtil.parseList(value, ';');
			return array;
		}

		public void doInit(PrintStream out) {
			File dir = this.__getDestDir();
			if (!dir.exists())
				dir.mkdirs();
		}

		public void doClear(PrintStream out) {
			File dir = this.__getDestDir();
			out.println("    clear: " + dir);
			this.__deleteAllFilesInDir(dir, 64);
		}

		private void __deleteAllFilesInDir(File dir, int depthLimit) {

			if (depthLimit < 0) {
				throw new RuntimeException("the path is too deep : " + dir);
			}
			if (dir == null)
				return;
			File[] list = dir.listFiles();
			if (list == null)
				return;

			for (File ch : list) {
				if (ch.isDirectory()) {
					this.__deleteAllFilesInDir(ch, depthLimit - 1);
				}
				ch.delete();
			}
		}

		private File __getDestDir() {
			File dest_dir = this._dest_dir;
			if (dest_dir == null) {
				File file = this._project.getPath();
				Properties prop = this._project.getProperties();
				String key = Const.Key.cp_dest_dir;
				String dir_name = ApmUtil.getProperty(file, prop, key, false)
						.trim();
				if (dir_name.length() < 1) {
					throw new RuntimeException(
							"the dest dir name cannot be empty!");
				}
				dest_dir = new File(file.getParentFile(), dir_name);
				this._dest_dir = dest_dir;
			}
			return dest_dir;
		}
	}

}
