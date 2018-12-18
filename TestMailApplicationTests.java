package com.liu.testmail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMailApplicationTests {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        // 邮件设置
        message.setSubject("自己发送的邮件");
        message.setText("测试邮件");
        message.setTo("as920579353@163.com");
        message.setFrom("920579353@qq.com");

        mailSender.send(message);
    }

    @Test
    public void testMail() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                true, "UTF-8");

        // 邮件设置
        helper.setSubject("通知-今晚开会");
        helper.setText("<b style='color:red'>今天 7:30 开会</b>",true);

        helper.setTo("as920579353@163.com");
        helper.setFrom("920579353@qq.com");

        // 上传文件
        helper.addAttachment("3.jpg", new File("D:/[3]happy sky/图片/壁纸/3.jpg"));

        mailSender.send(mimeMessage);
    }

}

