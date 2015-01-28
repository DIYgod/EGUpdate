import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;

public class FileReadAndWrite {
	private String ip;
	private String appid;
	public FileReadAndWrite(String appid, String ip){
		this.appid = appid;
		this.ip = ip;
	}
	public void run(){
		File bfile = new File(".." + File.separator + "local" + File.separator +"proxy.ini.bak");
		if(!bfile.isFile()){
			try {
				bfile.createNewFile();
			} catch (IOException e) {
				System.out.println("创建备份文件失败!");
				return;
			}
		}
		File file = new File(".." + File.separator + "local" + File.separator +"proxy.ini");
		if(!file.isFile()){
			System.out.println("proxy.ini文件不存在!");
			return;
		}
		FileWriter cb;
		try {
			cb = new FileWriter(bfile);
			cb.write("");
		} catch (IOException e1) {
			System.out.println("初始化失败!");
		}
		fileCopy(file, bfile);
		try {
			cb = new FileWriter(file);
			cb.write("");
		} catch (IOException e1) {
			System.out.println("初始化失败!");
		}
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		StringBuffer buf = new StringBuffer();
		try {
			System.out.println("Message>>>正在读取文件...");
			fis = new FileInputStream(bfile);		//从proxy.ini.bak读取
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			pw = new PrintWriter(file);				//写入proxy.ini
			str = null;
			System.out.println("Message>>>正在更新配置...");
            while ((str = br.readLine()) != null) {
            	buf.append(str);
            	if(str.indexOf("appid") != -1) {
            		int i = str.indexOf(" = ") + 3;
                	buf.replace(i, str.length(), appid);
            	}
            	else if(str.indexOf("google_cn") == 0 || str.indexOf("google_hk") == 0) {
                	int i = str.indexOf(" = ") + 3;
                	if(str.indexOf("www.google.") != -1) {		//兼容原生proxy.ini文件
                		buf.insert(i, ip + System.getProperty("line.separator") + ";");
                	}
                	else {										//EasyGoAgent的proxy.ini文件
                		buf.replace(i, str.length(), ip);
                	}
                }
                str = buf.toString();						//写入配置文件
                pw.println(str);
                buf.delete(0, buf.length());
            }
        } catch (IOException e) {  
            System.out.println("更新配置文件出错");
        } finally {
            try {
                br.close();
                isr.close();
                fis.close();
                pw.close();
            } catch (IOException e) {  
                System.out.println("关闭流出错!");
            }
        }
	}
	
	private static void fileCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0, in.size(), out);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
				inStream.close();
				in.close();
				outStream.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}
