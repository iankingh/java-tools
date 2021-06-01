package com.ian.tools.other;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * SecurityUtils
 */

// 參考 adanac-framework-common/XSSCheck.java at master ·
// adanac/adanac-framework-common

// Top 20 OWASP Vulnerabilities And How To Fix Them Infographic
// https://www.upguard.com/articles/top-20-owasp-vulnerabilities-and-how-to-fix-them

public class SecurityUtils {

    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定義script的正規表示式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定義style的正規表示式
    private static final String regEx_html = "</?[^>]+>"; // 定義HTML標籤的正規表示式
    private static final String regEx_space = "\\s*|\t|\r|\n";// 定義空格ENTER鍵換行符
    private static final String regEx_w = "<w[^>]*?>[\\s\\S]*?<\\/w[^>]*?>";// 定義所有w標籤

    /**
     * 
     * 1. SQL Injection Description: SQL injection vulnerabilities occur when data
     * enters an application from an untrusted source and is used to dynamically
     * construct a SQL query. SQL Injection may result in data loss or corruption,
     * lack of accountability, or denial of access. Injection can sometimes lead to
     * complete host takeover.
     * 
     * Fix / Recommendation: Proper server-side input validation must be used for
     * filtering out hazardous characters from user input. Additionally, making use
     * of prepared statements / parameterized stored procedures can ensure that
     * input is processed as text.
     * 
     * Sample Code Snippet (Input Validation):
     * 
     * @param oldValue
     * @return
     */
    public String sqlInjectCheck(String input) {
        String output = "";
        String characterPattern = "[0-9a-zA-Z]";
        if (!input.matches(characterPattern)) {
            System.out.println("Invalid Input");
        } else {

            output = input;

        }
        return output;
    }

    /**
     * 
     * Fortify漏洞之 Log Forging（日誌偽造） - IT閱讀
     * 
     * @param log
     * @return
     * @see https://www.itread01.com/content/1526318438.html
     */
    public static String vaildLog(String log) {
        List<String> list = new ArrayList<String>();
        list.add("%0d");
        list.add("\r");
        list.add("%0a");
        list.add("\n");
        String encode = Normalizer.normalize(log, Form.NFKC);
        for (int i = 0; i < list.size(); i++) {
            encode = encode.replace(list.get(i), "");
        }
        return encode;
    }

    /**
     * 将请求中的参数进行如下处理 1.通过参数名称，从请求中取得参数值。 2、将&,<,>,’,”转义: & -> &amp;< -> &it; > ->
     * &gt; “ -> &quot; ‘ -> &acute; 3、返回安全的字符串。
     * 
     * @author adanac
     * @version 1.0
     */
    public String xSSCheckgetParameter(HttpServletRequest request, String paramName) {
        if (request == null || paramName == null) {
            return null;
        }
        String ret = request.getParameter(paramName);
        if (ret == null) {
            return null;
        }
        ret = ret.replaceAll("'", "&acute");
        ret = ret.replaceAll("&", "&amp");
        ret = ret.replaceAll("<", "&it");
        ret = ret.replaceAll(">", "&gt");
        ret = ret.replaceAll("\"", "&quot");
        return ret;
    }

    /**
     * @param htmlStr
     * @return 過濾Html標籤
     * @see https://codertw.com/%E7%A8%8B%E5%BC%8F%E8%AA%9E%E8%A8%80/308349/
     */
    public static String delHTMLTag(String htmlStr) {
        if (htmlStr == null) {
            return "";
        } else {
            Pattern p_w = Pattern.compile(regEx_w, Pattern.CASE_INSENSITIVE);
            Matcher m_w = p_w.matcher(htmlStr);
            htmlStr = m_w.replaceAll(""); // 過濾script標籤
            Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            Matcher m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); // 過濾script標籤
            Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            Matcher m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); // 過濾style標籤
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); // 過濾html標籤
            Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
            Matcher m_space = p_space.matcher(htmlStr);
            htmlStr = m_space.replaceAll(""); // 過濾空格ENTER鍵標籤
            htmlStr = htmlStr.replaceAll(" ", ""); // 過濾
            return htmlStr.trim(); // 返回文字字串
        }
    }

}