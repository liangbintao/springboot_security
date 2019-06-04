package com.mochu.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class SignUtil {

    public static void main(String[] args) {

        try {

            Map<String, String> params = new HashMap<>();
            params.put("mobile", "13328776957");
            params.put("t", "1558405868");

            String signText = sign(params);
            log.info(signText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static private String salt = "ed732d7bf0e0470fb729dcf199d6b919";

    public static boolean checkSign(Map<String, String> params, String sign) {

        String signText = sign(params);

        return signText.equals(sign);
    }

    public static String sign(Map<String, String> params) {

        params.put("salt", salt);

        String orderText = getOrderText(params);

        String signText = Md5Util.getMD5Str(orderText);

        return signText;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    private static String getOrderText(Map<String, String> params) {

        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
                params.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        StringBuilder sb = new StringBuilder();
        int a = 0;
        for (Map.Entry<String, String> item : infoIds) {
            if (item.getKey() != null || item.getKey() != "") {
                String key = item.getKey();
                String val = item.getValue();
                if (!(val == "" || val == null)) {
                    if (a == 0) {
                        sb.append(key + "=" + val);
                    } else {
                        sb.append("&" + key + "=" + val);
                    }
                }
                a++;
            }
        }
        return sb.toString();
    }
}