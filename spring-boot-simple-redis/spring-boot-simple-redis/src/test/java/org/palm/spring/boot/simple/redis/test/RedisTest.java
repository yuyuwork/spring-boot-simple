package org.palm.spring.boot.simple.redis.test;

import com.sun.jmx.snmp.Timestamp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.palm.spring.boot.simple.redis.RedisApplication;
import org.palm.spring.boot.simple.redis.enums.ExpireEnum;
import org.palm.spring.boot.simple.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApplication.class)
public class RedisTest {

    private static final String REDIS_WEBSOCKET_USER_SET="mq-websocket:websocket_user_set";
    private static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private RedisService redisService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void redisSetTest(){
        SetOperations<String, Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(REDIS_WEBSOCKET_USER_SET,"hahaha","admin");

        //1. 遍历
        Set<Object> set = opsForSet.members(REDIS_WEBSOCKET_USER_SET);
        System.out.println("缓存集合数据："+set);

        //2. 判断是否是set成员
        System.out.println(opsForSet.isMember(REDIS_WEBSOCKET_USER_SET,"admin"));
        System.out.println(opsForSet.isMember(REDIS_WEBSOCKET_USER_SET,"zifangsky"));

        //3. 移除某个值
        opsForSet.remove(REDIS_WEBSOCKET_USER_SET,"admin");

        //4. 再次判断
        System.out.println(opsForSet.isMember(REDIS_WEBSOCKET_USER_SET,"admin"));
        System.out.println("当前时间是："+new Timestamp(System.currentTimeMillis()));

        //11、设置值
        opsForSet.add(REDIS_WEBSOCKET_USER_SET, "22");
        opsForSet.add(REDIS_WEBSOCKET_USER_SET,"33");
        Set<Object> newSetResult = opsForSet.members(REDIS_WEBSOCKET_USER_SET);
        System.out.println("缓存集合数据："+newSetResult);
    }

    @Test
    public void redisHashTest(){
        //Hash结构，保存和读取map
        Map<String,Object> mapParam=new HashMap<>();
        mapParam.put("key1","value1");
        mapParam.put("key2","value2");
        mapParam.put("key3","value3");
        mapParam.put("key4","value4");
        mapParam.put("key5","value5");

        HashOperations<String, String, Object> hashOperations= redisTemplate.opsForHash();
        hashOperations.putAll("map1",mapParam);

        Map<String,Object> resultMap= hashOperations.entries("map1");
        System.out.println("resultMap结果:"+resultMap);

        List<Object> reslutMapList=hashOperations.values("map1");
        System.out.println("resulreslutMapListtMap结果:"+reslutMapList);
        Set<String> resultMapSet=hashOperations.keys("map1");
        System.out.println("resultMapSet结果:"+resultMapSet);

        String value=(String)hashOperations.get("map1","key1");
        System.out.println("value结果:"+value);
    }

    @Test
    public void redisListTest(){
        List<String> list1=new ArrayList<>();
        list1.add("a1");
        list1.add("a2");

        List<String> list2=new ArrayList<>();
        list2.add("b1");
        list2.add("b2");

        ListOperations<String, Object> listOperations= redisTemplate.opsForList();
        listOperations.leftPush("listkey1",list1);
        listOperations.rightPush("listkey2",list2);

        List<String> resultList1=(List<String>)listOperations.leftPop("listkey1");
        List<String> resultList2=(List<String>)listOperations.rightPop("listkey2");
        System.out.println("resultList1:"+resultList1);
        System.out.println("resultList2:"+resultList2);
        /*
        不管是leftPush还是rightPush都可以用leftPop或者rightPoP任意一种获取到其中的值，不过就是获取的遍历方向不一样
        有学过数据结构的人都知道里面循环链表是可以前后遍历的，就和这里的场景是一样的。如果还有不懂的话可以去看看这部分的源代码，
        其实就是遍历方向不同，所以效率也不同。所以最好leftPush用leftPoP遍历，rightPush用rightPoP遍历
         */
    }

    @Test
    public void redisValueTest(){
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForValue().set("key2","value2");

        String result1=redisTemplate.opsForValue().get("key1").toString();
        String result2=redisTemplate.opsForValue().get("key2").toString();
        System.out.println("缓存结果为：result："+result1+"  "+result2);

        redisTemplate.opsForValue().set("key3",10);
        System.out.println("缓存结果为：key3："+redisTemplate.opsForValue().get("key3"));
    }

    @Test
    public void redisIncrTest(){
        Long value=redisService.incr("auto_increment",ExpireEnum.AUTO_INCREMENT);
        System.out.println("返回结果："+value);
        value=redisService.incr("auto_increment",ExpireEnum.AUTO_INCREMENT);
        System.out.println("返回结果："+value);

        //这种无法设置有效期,后面Long类型的为增长值
        value=redisTemplate.opsForValue().increment("auto_increment1",10);
        System.out.println("返回结果："+value);
        Integer increment= (Integer) redisTemplate.opsForValue().get("auto_increment1");
        System.out.println("返回结果："+increment);
    }

    @Test
    public void redisSetValueTest() throws ParseException {
        //redisService.set("keyValue",11,DATE_FORMAT.parse("2018-12-18 13:45:00"));
        redisService.set("keyValue",11,ExpireEnum.AUTO_INCREMENT.getTime(),ExpireEnum.AUTO_INCREMENT.getTimeUnit());
        System.out.println(redisTemplate.opsForValue().get("keyValue"));

        System.out.println(redisService.generate("keyValue"));
        System.out.println(redisService.generate("keyValue",5));
    }

}
