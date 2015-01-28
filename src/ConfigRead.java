import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigRead {
	public String getConfig(){
		String uri = this.getClass().getResource("/").getPath(); 
		System.out.println("配置文件路径:");
		System.out.println(uri);
		InputStream inputStream =null;
		try {
			inputStream = new FileInputStream(new File("config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties p = new Properties();    
		try {
			p.load(inputStream);    
		} catch (IOException e1) {    
			e1.printStackTrace();    
		}
		String url = p.getProperty("http_url");
		return url;
	}
}
