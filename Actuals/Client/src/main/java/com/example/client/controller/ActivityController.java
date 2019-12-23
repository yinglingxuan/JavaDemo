package com.example.client.controller;


import com.example.client.config.WXConfig;
import com.example.client.utils.ZXingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/twocode/activity")
public class ActivityController {
    @Autowired
    private WXConfig wxConfig;
    @GetMapping("qrcode/{id}")
    @ResponseBody
    public String get(@PathVariable(value = "id", required = true) String id) {
        String url = wxConfig.getOauth2Authorize(wxConfig.getUrl()+"/come/user/login", id);
        System.err.println(url);
        return ZXingUtil.getBase64QRCodeData(url, 300, 300);
    }

    @GetMapping(value = "qrcode/show/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] show(@PathVariable(value = "id", required = true) String id) {
        String url = wxConfig.getOauth2Authorize(wxConfig.getUrl()+"/come/user/login", id);
        System.err.println(url);
        return ZXingUtil.getBytes(url, 300, 300);
    }

    @GetMapping(value = "qrcode/download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public byte[] download(@PathVariable(value = "id", required = true) String id, HttpServletResponse response) {
        response.setHeader("Content-Disposition", "attachment; filename=" + id + "-二维码.jpg");
        String url = wxConfig.getOauth2Authorize(wxConfig.getUrl()+"/come/user/login", id);
        System.err.println(url);
        return ZXingUtil.getBytes(url, 300, 300);
    }

}
