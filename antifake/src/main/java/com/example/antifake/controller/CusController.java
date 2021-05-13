package com.example.antifake.controller;

import com.example.antifake.tool.RSA;
import com.example.antifake.entity.UserRepository;
import com.example.antifake.entity.Production;
import com.example.antifake.entity.ProductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RestController
public class CusController {
    @Autowired
    private ProductionRepository productionRepository;
    private UserRepository userRepository;

    @RequestMapping(value = "/miwen", method = RequestMethod.POST)
    @ResponseBody
    public String getInfo(String secode) throws Exception {
        String miwen = secode;
        String serialNum =  RSA.decrypt(miwen, RSA.getPublicKey());
//        List<Production> productions = productionRepository.findByproductionserialnum(serialNum);
//        Production productiona = productions.get(0);

//        List<User> userList = userRepository.findByusername(miwen);
//        User user1 = userList.get(0);

//        String info = productiona.getInfo();
//        return "serialNum: " + serialNum + "\n" + "proctionInfo: " + info;

        if(serialNum.equals("0")) return "you are fake!!";
        else {
            List<Production> emptyOpt = productionRepository.findBySerialNum(serialNum);
            if (emptyOpt.size() != 0) {
                Production productiona = emptyOpt.get(0);
                if (productiona.getFlag() == 0) {
                    String info = productiona.getInfo();
                    productiona.updateFlag();
                    productionRepository.save(productiona);
                    return "serialNum: " + serialNum + "\n" + "proctionInfo: " + info;
                }
                else return "你已经验过了!!";
            } else return "you are fake!!";
        }
    }
}
