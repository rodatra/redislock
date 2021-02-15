package rodatra.boot_redis01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodController{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/buy_goods")
    public String buy_Goods(){
        // 1.查看库存数量
        String result = stringRedisTemplate.opsForValue().get("goods:001");
        int goodsNumber = result == null ? 0 : Integer.parseInt(result);

        // 2.卖商品
        if(goodsNumber > 0){
            int realNumber = goodsNumber - 1;
            // 3.成功买入，库存减少一件
            stringRedisTemplate.opsForValue().set("goods:001",String.valueOf(realNumber));
            return "成功买入商品，库存还剩下："+realNumber+"服务端口："+serverPort;
        }else{
            System.out.println("商品卖完"+"服务端口："+serverPort);
        }
        return "商品卖完！"+"服务端口："+serverPort;
    }

}