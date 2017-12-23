import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

public class TestFTP {
	@Test
	public void testFTP() throws Exception {
		// 创建FTPClient
		FTPClient ftpClient = new FTPClient();
		ftpClient.connect("172.17.0.231", 21);
		ftpClient.login("ftpuser", "ftpuser");

		FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\t.png"));
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		ftpClient.storeFile("hello.png", fileInputStream);
		ftpClient.logout();
	}

}
