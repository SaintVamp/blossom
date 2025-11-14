package com.blossom.backend.server.utils;

import com.blossom.common.base.util.okhttp.HttpUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class NoticeUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendEmail(String title, String content, String HOST, String USER, String PWD) {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);//设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
        props.put("mail.smtp.auth", "true");  //需要经过授权，也就是有户名和密码的校验，这样才能通过验证（一定要有这一条）
        Session session = Session.getDefaultInstance(props);//用props对象构建一个session
        session.setDebug(false);
        MimeMessage message = new MimeMessage(session);//用session为参数定义消息对象
        try {
            message.setFrom(new InternetAddress(USER));// 加载发件人地址
            InternetAddress[] sendTo = new InternetAddress[1]; // 加载收件人地址
            sendTo[0] = new InternetAddress(USER);
            message.addRecipients(Message.RecipientType.TO, sendTo);
//			message.addRecipients( MimeMessage.RecipientType.CC, InternetAddress.parse( FROM ) );//设置在发送给收信人之前给自己（发送方）抄送一份，不然会被当成垃圾邮件，报554 错
            message.setSubject(title);//加载标题
            Multipart multipart = new MimeMultipart();//向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
            BodyPart contentPart = new MimeBodyPart();//设置邮件的文本内容
            contentPart.setText(content);
            multipart.addBodyPart(contentPart);
            message.setContent(multipart);//将multipart对象放到message中
            message.saveChanges(); //保存邮件
            Transport transport = session.getTransport("smtp");//发送邮件
            transport.connect(HOST, USER, PWD);//连接服务器的邮箱
            transport.sendMessage(message, message.getAllRecipients());//把邮件发送出去
            transport.close();//关闭连接
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendWork(String content, String agentId, String mobile, String userId, String corpId, String corpSecret, String token) {
        String v_token;
        if (Objects.equals(token, ""))
            v_token = createToken(corpSecret, corpId);
        else v_token = token;
        String data = String.format("{\"mobile\": \"%s\"}", mobile);
        if (!searchUser(data, v_token)) v_token = createToken(corpSecret, corpId);
        String workMsg = String.format("{\"touser\": \"%s\", \"msgtype\": \"text\", \"agentid\": \"%s\", \"text\": {\"content\": \"%s\"}}", userId, agentId, content);
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + v_token;
        String response = HttpUtil.post(url, workMsg);
    }

    /**
     * 根据手机号获取企业微信用户ID
     *
     * @param inData    请求数据
     * @param qywxToken 企业微信访问令牌
     * @return 错误码是否为0的结果
     */
    private static boolean searchUser(String inData, String qywxToken) {
        try {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserid?debug=1&access_token=" + qywxToken;
            String response = HttpUtil.post(url, inData);
            JsonNode rsj = objectMapper.readTree(response);
            return rsj.get("errcode").asInt() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String createToken(String corpSecret, String corpId) {
        try {
            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpId + "&corpsecret=" + corpSecret;
            String response = HttpUtil.get(url);
            JsonNode rsj = objectMapper.readTree(response);
            String WorkToken = "";
            if (rsj.get("errcode").asInt() == 0) {
                WorkToken = rsj.get("access_token").asText();
            }
            return WorkToken;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
