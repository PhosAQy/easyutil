package com.phosa;

import com.google.gson.JsonParser;
import com.yeahmobi.itauto.util.feishu.FeishuUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;

/**
 * @author phosa.gao
 */
@Slf4j
public class MailUtil {
    /**
     * @return
     * 查询邮箱的状态
     * 1：邮件地址格式错误
     * 2：邮件地址域名不存在
     * 3：邮箱地址不存在
     * 4：启用
     * 5：已删除（邮箱回收站中）
     */
    public static String checkMailStatus(String email)  {
        String res = null;
        try {
            res = Request.Post("https://open.feishu.cn/open-apis/mail/v1/users/query")
                    .setHeader("Content-Type", "application/json; charset=utf-8")
                    .setHeader("Authorization", "Bearer " + FeishuUtil.getToken())
                    .bodyString("{\n" +
                            "    \"email_list\": [\n" +
                            "        \"" + email + "\"\n" +
                            "    ]\n" +
                            "}", ContentType.APPLICATION_JSON)
                    .connectTimeout(1000)
                    .socketTimeout(1000)
                    .execute().returnContent().asString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String status = JsonParser.parseString(res).getAsJsonObject().get("data").getAsJsonObject().get("user_list").getAsJsonArray().get(0).getAsJsonObject().get("status").getAsString();
        log.info("checkMailStatus:{}, res:{}", email, res);
        return status;
    }
    public static boolean checkMailValidAndExist(String email) {
        return "4".equals(checkMailStatus(email));
    }
    public static boolean checkMailValidAndNotExist(String email) {
        return "3".equals(checkMailStatus(email));
    }
}
