package com.cyiplus.scarlett.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import com.qiniu.util.Auth;
import javax.imageio.ImageIO;
import com.alibaba.fastjson.JSONObject;
import com.cyiplus.scarlett.common.lang.Const;
import com.cyiplus.scarlett.common.lang.Result;
import com.cyiplus.scarlett.util.RedisUtil;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;

@RestController
@RequestMapping("/auth")
public class SysAuthController {
    
    @Autowired
    private Producer producer;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RedisUtil redisUtil;

    @GetMapping("/captcha")
    public Result captcha(@RequestParam("uuid") String uuidString) throws IOException {

        if (redisUtil.hHasKey(Const.CAPTCHA_KEY, uuidString)) {
            redisUtil.hdel(Const.CAPTCHA_KEY,uuidString);
        }

        String uuid = UUID.randomUUID().toString();
		String code = producer.createText();

        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", outputStream);
		String str = "data:image/jpeg;base64,";
		String base64Img = str + Base64Encoder.encode(outputStream.toByteArray());
        
        //TODO 修改修改uuid_code有效时间为1分钟 
        redisUtil.hset(Const.CAPTCHA_KEY, uuid, code, 60);

        return Result.succ(
            MapUtil.builder()
                .put("uuid", uuid)
                .put("captchaImg", base64Img)
                .build()
        );        
    }
    
    @GetMapping("/toToken")
    public Result qiniuToken() {

        Auth auth = Auth.create(Const.ACCESSKEY, Const.SECRETKEY);
        String upToken = auth.uploadToken(Const.BUCKET);
        System.out.println(upToken);

        JSONObject token = new JSONObject();
        token.put("uploadToken", upToken);

        String date = DateTimeFormatter.ofPattern("yyyy/MM/dd/").format(LocalDateTime.now());
        String filename = date + "BBS_";

        return Result.succ(
            MapUtil.builder()
                .put("upToken", upToken)
                .put("filename", filename)
                .put("domain", Const.DOMAIN)
                .build()
        );
    }

}
