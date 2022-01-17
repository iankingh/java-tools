package com.ian.tools.httpclient;

@Slf4j
public class HttpClientUtils {

     private static final Charset UTF_8 = Charset.forName("UTF-8");


    private String connectTimeout = "155000";
    private String socketTimeout = "156000";
    private String connectionRequestTimeout = "156000";


    private RequestConfig requestConfig;
    private CloseableHttpClient httpClient;

    private ObjectMapper objectMapper;


    public String send(String url, HashMap request) throws IOException {

        String jsonData = this.serialize(request);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(this.requestConfig);
        httpPost.setEntity(new StringEntity(jsonData, UTF_8));
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        CloseableHttpResponse response = null;

        try {
            response = this.httpClient.execute(httpPost);
            return getResponseString(response);
        } finally {
            if (response != null) {
                EntityUtils.consumeQuietly(response.getEntity());
                response.close();
            }

        }


    }

    private String serialize(Object request) throws IllegalArgumentException {
        try {
            return this.objectMapper.writeValueAsString(request);
        } catch (JsonProcessingException var4) {
            StringBuilder sb = new StringBuilder();
            sb.append("Bad input data to JSON serialization");
            if (var4.getLocation() != null) {
                sb.append("at line:").append(var4.getLocation().getLineNr()).append(" column")
                        .append(var4.getLocation().getColumnNr());
            } else {
                sb.append(".").append(var4.getMessage());
            }

            throw new IllegalArgumentException(sb.toString(), var4);
        }
    }

    private <T> T deserialize(String s, Class<T> clazz) throws IOException {
        return this.objectMapper.readValue(s, clazz);
    }

    private String getResponseString(CloseableHttpResponse response) throws IOException {
        String responseString = null;
        if (response.getEntity() != null) {
            responseString = EntityUtils.toString(response.getEntity(), UTF_8);
        }

        return responseString;
    }

    
    
    // 將結果用HTTP打回給URL
    public static void callBack(Map<String, String> decodeCardBillRQMap, String resultCode,
            String resultMessage, String transoutBankID,
            String transeq, String fee) {
        
        log.debug("callBack URL = " + URL);
        try {
            URL url = new URL(URL);
            if (URL.toLowerCase().indexOf("https") == 0) {
                log.debug("sent https url :" + URL);
                // 建立 TLS 陣列
                String[] PROTOCOL_ARRAY = new String[] {"TLSv1.2", "TLSv1.1", "TLS"};
                // 迴圈設定TLS變數,並發送 Https
                for (String string : PROTOCOL_ARRAY) {
                    // 設定TLS
                    trustAllHosts(string);
                    // 發送Https 如果回傳是true 表示設定成功
                    boolean falseflag = tryTLS(url);
                    // 如果 falseflag == true 則跳離迴圈
                    if (falseflag) {
                        log.debug("falseflag true TLS : " + string);
                        break;
                    } else {
                        log.debug("falseflag false TLS : " + string);
                    }
                }
                // 取得urlConnection
                urlConnection = url.openConnection();
                ((HttpsURLConnection) urlConnection).setRequestMethod("POST");
            } else {
                urlConnection = url.openConnection();
                ((HttpURLConnection) urlConnection).setRequestMethod("POST");
            }

            urlConnection.setDoOutput(true);
            dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            String urlParameters = "";
            for (int x = 0; x < keys.length; x++) {
                if (x == 0) {
                    urlParameters = keys[x] + "=" + values[x];
                } else {
                    urlParameters += "&" + keys[x] + "=" + values[x];
                }
            }
            dataOutputStream.writeBytes(urlParameters);
            dataOutputStream.flush();
            if (URL.toLowerCase().indexOf("https") == 0) {
                log.debug(ESAPIUtil.vaildLog("HttpsResponseCode "
                        + ((HttpsURLConnection) urlConnection).getResponseCode()));
            } else {
                log.debug(ESAPIUtil.vaildLog("ResponseCode " + ((HttpURLConnection) urlConnection).getResponseCode()));
            }
        } catch (Exception e) {
            log.error("ExternalPayUtil Exception", e, e);
        } finally {
            try {
                dataOutputStream.close();
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
    }

    /**
     * 發送Https 如果回傳是true 表示TLS設定成功
     * 
     * @param url
     * @return
     */
    public static boolean tryTLS(URL url) {
        log.debug("sentHttps url " + url);
        boolean falseflag = false;
        try {
            URLConnection urlConnection = url.openConnection();
            ((HttpsURLConnection) urlConnection).setHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String host, SSLSession session) {
                    return true;
                }
            });
            ((HttpsURLConnection) urlConnection).setRequestMethod("POST");
            // 需取得回傳判斷回傳是否成功
            ((HttpsURLConnection) urlConnection).getResponseCode();
            falseflag = true;
        } catch (SocketException se) {
            // logger.debug("sentHttps SocketException", se);
        } catch (IOException e) {
            log.debug("sentHttps IOException", e);
        }
        return falseflag;
    }

    /**
     * 忽略HTTPS請求的SSL證書，必須在openConnection之前調用
     */
    private static void trustAllHosts(String strTls) {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain, String authType) {}

            public void checkServerTrusted(X509Certificate[] chain, String authType) {}
        }};
        // Install the all-trusting trust manager
        // 忽略HTTPS请求的SSL證書，必须在openConnection之前调用
        try {
            SSLContext sc = SSLContext.getInstance(strTls);
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            log.error("trustAllHosts is error", e);
        }
    }

    
}
