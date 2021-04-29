package com.example.stumanage.controller;

import com.example.stumanage.entity.Production;
import com.example.stumanage.entity.ProductionRepository;
import com.example.stumanage.entity.User;
import com.example.stumanage.entity.UserRepository;
import com.example.stumanage.tool.RSA;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Cipher;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;


@Controller
public class RootController {

    @Autowired
    private ProductionRepository productionRepository;

    private final ResourceLoader resourceLoader;

    private final String path = "C:\\Users\\tzf\\IdeaProjects\\stumanage\\src\\main\\resources\\public\\";

    @Autowired
    public RootController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(value = "/root", method = RequestMethod.POST)
    public String addQrCode(Production production, Map<String ,Object> map) throws InterruptedException {
        production.setFlag(0);
        productionRepository.save(production);
        try {
            production.setQrCode(createQrCode(production));
            map.put("qrcode", production.getQrCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        productionRepository.save(production);
        return "qrcode";
    }

//    @RequestMapping(value = "/addProduction", method = RequestMethod.POST)
//    public String addProduction(Production production) {
//
//        return "success";
//    }

//    @RequestMapping("/getAll")
//    public Iterable<Production> getAll(){
//        return productionRepository.findAll();
//    }

    /**
     * 显示单张图片
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ResponseEntity showPhotos(String fileName) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + path + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 获取二维码
     *
     * @return
     */
//    @RequestMapping(value = "/root/getQrCode/addProduction", method = RequestMethod.POST)
    private String getQrCode(String content) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage image = makeQrCode(content);
        ImageIO.write(image, "JPG", out);
        return Base64.encodeBase64String(out.toByteArray());
    }

    private String createQrCode(Production production) throws Exception {
        File temp = new File(path);
        String privateKey = RSA.getPrivateKey();
        String content = production.getSerialNum();
        content = RSA.encrypt(content, privateKey);
        BufferedImage image = makeQrCode(content);
        String fileName = production.getId() + ".jpg";//随机生成二维码的名称
        ImageIO.write(image, "jpg", new File(path + File.separator + fileName));//将图片写到磁盘

        return fileName;

//        HashMap hints = new HashMap();
//        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//设置字符的编码
//        hints.put(EncodeHintType.MARGIN, 1);//设置外边距的距离
//        BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//
//        MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(Color.BLACK.getRGB(), Color.WHITE.getRGB());
//
//        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix,matrixToImageConfig);

    }

    private BufferedImage makeQrCode(String content) throws Exception{
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.MARGIN, 1);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 40, 40, hints);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return image;
        }
    }

}
