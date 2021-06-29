package com.ruoyi.project.zerocarbon.auth;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;

/**
 * @类名: APIValidateUtil
 * @描述:
 * @作者: liudf
 * @日期: 2021/6/29 15:43
 */
@Component
public class APIValidateUtil {
    private static final String ENCODING = "UTF-8";
    private static final String httpMethod = "POST";
    /**
     * 根据传入参数生成签名字符串
     *
     * @param parameters
     * @return
     * @throws Exception
     */
    public String computeSignature(Map<String, String> parameters, String accessKeySecret) throws Exception {
        // 将参数Key按字典顺序排序
        String[] sortedKeys = parameters.keySet().toArray(new String[] {});
        Arrays.sort(sortedKeys);

        final String SEPARATOR = "&";

        // 生成规范化请求字符串
        StringBuilder canonicalizedQueryString = new StringBuilder();
        for (String key : sortedKeys) {
            canonicalizedQueryString.append("&").append(percentEncode(key)).append("=")
                    .append(percentEncode(parameters.get(key)));
        }

        // 生成用于计算签名的字符串 stringToSign
        StringBuilder stringToSign = new StringBuilder();
        stringToSign.append(httpMethod).append(SEPARATOR);
        stringToSign.append(percentEncode("/")).append(SEPARATOR);
        stringToSign.append(percentEncode(canonicalizedQueryString.toString().substring(1)));

        // 注意accessKeySecret后面要加入一个字符"&"
        String signature = calculateSignature(accessKeySecret + "&", stringToSign.toString());
        return signature;
    }

    private static String calculateSignature(String key, String stringToSign) throws Exception {
        // 使用HmacSHA1算法计算HMAC值
        final String ALGORITHM = "HmacSHA1";
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(new SecretKeySpec(key.getBytes(ENCODING), ALGORITHM));
        byte[] signData = mac.doFinal(stringToSign.getBytes(ENCODING));

        return new String(Base64.encodeBase64(signData));
    }

    private static String percentEncode(String value) throws UnsupportedEncodingException {
        // 使用URLEncoder.encode编码后，将"+","*","%7E"做替换
        return value != null ? URLEncoder.encode(value, ENCODING).replace("+", "%20").replace("*", "%2A")
                .replace("%7E", "~") : null;
    }
}

