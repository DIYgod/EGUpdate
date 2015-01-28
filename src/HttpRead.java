import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRead {
	private String urlString;
	public HttpRead(String urlString) {
		  this.urlString = urlString;
	}
	public boolean run() {
		HttpURLConnection urlConnection = null;
		BufferedReader reader = null;
		String thisVersion = "V0.1";
		String version = "";
		String ip = "";
		String appid = "";
		try {
			  //打开一个URL对象
			  URL url = new URL(urlString);
			  //打开URL 
			  urlConnection = (HttpURLConnection) url.openConnection();
			  System.out.println("Message>>>正在读取网页内容...");
			  //得到输入流,即获得了网页的内容
			  reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
			  //读取输入流的数据
			  version += reader.readLine().substring(10);
			  appid += reader.readLine().substring(8);
			  ip += reader.readLine().substring(5);
		} catch (Exception e) {
			System.out.println("文件读取异常!");
			e.printStackTrace();
			return false;
		} finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					System.out.println("关闭输入流异常!");
					e.printStackTrace();
				}
			}
		}
		
		if(!version.equals(thisVersion)) {
			System.out.println("新版本发布, 请移步 www.anotherhome.net/1727 更新后使用");
			return false;
		}
		FileReadAndWrite fraw = new FileReadAndWrite(appid, ip);
		fraw.run();
		return true;
	}
}
