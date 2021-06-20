package com.ian.tools.other;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;

/**
 * FTPUtils
 * 
 * @see http://dophintil.blogspot.com/2013/04/javaftp.html
 * @see https://blog.xuite.net/jane17512001/PenguinDesign/116288107
 */
public class FTPUtils {

    /**
     * 建立FTP連線
     * 
     * @param ip    FTP IP
     * @param port  FTP PORT
     * @param id    FTP 帳號
     * @param pwd   FTP密碼
     * @param isSSL 是否透過SSL
     * @return
     * @throws IOException
     * @throws SocketException
     * @throws NumberFormatException
     * @throws NoSuchAlgorithmException
     */
    public static FTPClient createFtpConnection(String ip, String port, String id, String pwd, boolean isSSL)
            throws NumberFormatException, SocketException, IOException, NoSuchAlgorithmException {
        FTPClient ftpClient;
        if (isSSL) {
            ftpClient = new FTPSClient();
        } else {
            ftpClient = new FTPClient();
        }
        // 登入ftp
        ftpClient.connect(ip, Integer.parseInt(port));
        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            throw new RuntimeException("FTP連線失敗");
        } else {
            boolean ftpConnect = ftpClient.login(id, pwd);
            if (!ftpConnect) {
                throw new RuntimeException("FTP登入失敗[" + id + "," + pwd + "]");
            } else {
                // 設定ftp
                ftpClient.enterLocalPassiveMode();
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                return ftpClient;
            }
        }
    }

    /**
     * 下載FTP檔案
     * 
     * @param ip           FTP IP
     * @param port         FTP PORT
     * @param id           FTP 帳號
     * @param pwd          FTP密碼
     * @param isSSL        是否透過SSL
     * @param dir          FTP路徑
     * @param fileName     檔名
     * @param downloadPath 下載位置
     * @return 是否下載成功
     */
    public static boolean downloadFTPFile(String ip, String port, String id, String pwd, boolean isSSL, String dir,
            String fileName, String downloadPath) {
        boolean downloadFile = false;
        FTPClient ftpClient = null;
        InputStream is = null;
        try {
            ftpClient = createFtpConnection(ip, port, id, pwd, isSSL);
            boolean changeDir;
            if (StringUtils.isBlank(dir)) {// 當不需要切換路徑
                changeDir = true;
            } else {
                changeDir = ftpClient.changeWorkingDirectory(dir);
            }
            if (changeDir) {
                // 下載檔案
                if (ftpClient.listFiles(fileName).length > 0) {// 當檔案存在
                    is = ftpClient.retrieveFileStream(fileName);
                    if (is == null) {// 當檔案不存在
                        log.info("FTP下載檔案[" + fileName + "]不存在");
                    } else {// 當有檔案則轉成Stream
                        FileUtil.filedownLoad(is, downloadPath + fileName);
                        downloadFile = true;
                    }
                }
            } else {
                log.error("FTP目錄[" + dir + "]不存在");
            }
        } catch (ConnectException e) {
            log.error("FTP連線失敗: " + e, e);
        } catch (Exception e) {
            log.error("下載FTP資料失敗:" + e, e);
            log.error(e);
            throw new RuntimeException(e);
        } finally {
            closeFtpConnection(ftpClient);// 中斷FTP連線
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // DO NOTHING
                }
            }
        }
        return downloadFile;
    }

    /**
     * 關閉FTP連線
     * 
     * @param ftpClient
     */
    public static void closeFtpConnection(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                // 可能因為已經關閉連線, do nothing
            }
        }
    }}

    接下來就可以利用apache.common.net.ftp專案來開發FTP相關的功能了，如下：

    String server = "127.0.0.1";
    int port = 21;
FTPClient ftp = new FTPClient();ftp.connect(server,port);
int reply = ftp.getReplyCode();System.out.println(FTPReply.isPositiveCompletion(reply));System.out.println(ftp.login("UserName","Password"));System.out.println(ftp.logout());ftp.disconnect();

第1行及第2行我們定義了FTP Server的IP以及Port。第3行我們宣告了一個FTPClient物件。

第4行我們用connect()方法帶入IP及Port來連結至FTP Server。
第5行用getReplyCode()方法取得FTP Server的回應碼。
第6行用FTPReply.isPositiveCompletion()帶入前一行取得的回應碼，判斷是否成功連結上FTP Server。
第7行用login()方法帶入帳號密碼可以使用特定帳號登入FTP Server。
第8行用logout()方法登出。
第9行用disconnect()來結束FTP連線。 
接續上例，我們在登入FTP Server後也可以設定傳檔的模式，如下： 

ftp.setFileType(FTP.BINARY_FILE_TYPE);
ftp.setFileType(FTP.ASCII_FILE_TYPE);
ftp.setFileType(FTP.EBCDIC_FILE_TYPE);
ftp.setFileType(FTP.LOCAL_FILE_TYPE);
ftp.enterLocalPassiveMode();
ftp.enterLocalActiveMode();

第1-4行設定了檔案的類型，共有四種類型可以設定，在實作時只須擇一設定即可，一般情況下我們會設定FTP.BINARY_FILE_TYPE。
第5-6行設定了傳檔的模式，分為主動及被動兩種，可擇一進行設定，一般情況下我們會設定enterLocalPassiveMode()。
設定好檔案類型及傳輸模式後，就可以切換至我們需要的目錄並取得FTP Server上的檔案，如下： 

ftp.changeWorkingDirectory("/TEST");
ftp.changeToParentDirectory();
FTPFile[] files = ftp.listFiles();
for (int i = 0; i < files.length; i++) {
        System.out.println(files[i].getName());
        System.out.println(files[i].getSize());
        System.out.println(files[i].getTimestamp());
}

第1行用changeWorkingDirectory()轉換至指定的目錄。
第2行changeToParentDirectory()轉換至上一層目錄。
第3行listFiles()可以取得目前這個目錄下的FTP檔案。
第4-8行則用迴圈列出了檔案的名稱、大小、及最後修改時間。
接下來我們切換至指定目錄後，就可以開始進行檔案的上下傳了，如下：

FileOutputStream output = new FileOutputStream("D:\\TEST.TXT");
ftp.retrieveFile("TEST.TXT", output);
ftp.deleteFile("TEST.TXT");
output.close();
FileInputStream input = new FileInputStream("D:\\TEST.TXT");
ftp.storeFile("TEST.TXT", input);
ftp.rename("TEST.TXT", "TEST_FINIAH.TXT");
input.close();

第1-4行為下載的部分。第1行我們先建立一個FileOutputStream來將要下載的檔案輸出。第2行用retrieveFile()來將檔案下載。第3行可視需求用deleteFile()刪除遠端FTP Server的檔案。第4行將FileOutputStream關閉。
第5-8行為上傳的部分。第5行建立一個FileInputStream來將要上傳的檔案輸出。第6行用storeFile()傳送檔案。第7行可視需求用rename()將遠端FTP Server上的檔案更名。最後第8行同樣將FileInputStream關閉。
另外，關於FTPS的作法，和FTP極為類似，只需將FTPClient物件換成FTPSClient物件即可，因此就不再贅述。