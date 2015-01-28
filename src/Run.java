public class Run {
	public static void main(String[] args) {
		ConfigRead cr = new ConfigRead();
		String httpUrl = cr.getConfig();
		System.out.println("配置信息如下:");
		System.out.println("http_url:"+httpUrl);
		HttpRead hr = new HttpRead(httpUrl);
		if(!hr.run()) {
			System.out.println("Message>>>操作失败,请更新版本");
			return;
		}
		System.out.println("Message>>>操作完成");
		
	}
}
