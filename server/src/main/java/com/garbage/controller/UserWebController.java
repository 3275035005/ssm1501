package com.garbage.controller;

import com.alibaba.fastjson.JSONArray;
import com.aliyun.imagerecog20190930.Client;
import com.aliyun.imagerecog20190930.models.ClassifyingRubbishRequest;
import com.aliyun.imagerecog20190930.models.ClassifyingRubbishResponse;
import com.aliyun.imagerecog20190930.models.ClassifyingRubbishResponseBody;
import com.aliyun.tea.TeaException;
import com.aliyun.tea.TeaModel;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.garbage.entity.*;
import com.garbage.service.*;
import com.garbage.utils.OssUtils;
import com.garbage.utils.PageResult;
import com.garbage.utils.R;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对用户平台提供接口
 */
@RequestMapping("userWeb")
@RestController
public class UserWebController {

    private final static String userFilePath = "G:\\成品项目\\2-SSM\\垃圾分类识别系统\\项目源代码\\service\\image";

    private final static String baseUrl = "http://localhost:8080/files";

    @Autowired
    private UserService usersService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private GarbageService garbageService;

    @Autowired
    private KnowledgeService knowledgeService;

    @Autowired
    private FileService fileService;


    @Resource
    private HttpServletRequest request;

    public Long getUserId() {
        return Long.parseLong(request.getHeader("token"));
    }

    /**
     * 用户平台登录接口
     */
    @PostMapping("login")
    public R login(@RequestBody User users){
        User userss = usersService.getUserByUsername(users.getUsername());
        if(userss == null){
            return R.error("请输入正确的账号");
        }
        if(!userss.getPassword().equals(users.getPassword())){
            return R.error("请输入正确的密码");
        }
        String status = userss.getStatus();
        if("0".equals(status)){
            return R.error("账号已被禁用");
        }
        if(!"1".equals(userss.getRole())){
            return R.error("请登录用户账号");
        }
        return R.ok().put("row", userss);
    }


    /**
     * 用户平台注册接口
     */
    @PostMapping("register")
    public R register(@RequestBody User users){
        users.setRole("1");
        users.setStatus("1");
        usersService.save(users);
        return R.ok();
    }
    /**
     * 用户平台找回密码接口
     */
    @PostMapping("reset")
    public R reset(@RequestBody User users){

        User userByUsername = usersService.getUserByUsername(users.getUsername());
        if(userByUsername == null){
            return R.error("资料不正确");
        }
        if(!userByUsername.getName().equals(users.getName())){
            return R.error("资料不正确");
        }

        userByUsername.setPassword(users.getPassword());
        updateUser(users);
        return R.ok();
    }

    /**
     * 个人信息查询
     * @return
     */
    @GetMapping("getUserInfo")
    public R getUserInfo(){
        User user = usersService.getById(getUserId());
        return R.ok().put("row", user);
    }

    /**
     * 个人信息修改
     */
    @PostMapping("/updateUser")
    public R updateUser(@RequestBody User user){
        usersService.update(user);
        return R.ok();
    }

    /**
     * 垃圾分类信息识别
     */
    @PostMapping("/recognition")
    public R recognition(MultipartFile file) throws Exception {
        String uuid= System.currentTimeMillis()+".";
        String originalFilename = file.getOriginalFilename();
        String fileName= uuid + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String image = "";
        try {
            if (fileService.uploadFile(file,fileName)) {
                image =  baseUrl+"/image?imageName="+fileName;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        // 将图片上传到阿里云oss
        String instance = OssUtils.getInstance(userFilePath+"\\"+fileName);
        System.out.println(instance);
        // 调用阿里云垃圾分类图形识别api
        Client client = OssUtils.createClient();
        ClassifyingRubbishRequest classifyingRubbishRequest = new com.aliyun.imagerecog20190930.models.ClassifyingRubbishRequest()
                .setImageURL(instance);
        RuntimeOptions runtime = new com.aliyun.teautil.models.RuntimeOptions();
        try {
            ClassifyingRubbishResponse classifyingRubbishResponse = client.classifyingRubbishWithOptions(classifyingRubbishRequest, runtime);
            ClassifyingRubbishResponseBody.ClassifyingRubbishResponseBodyDataElements classifyingRubbishResponseBodyDataElements = classifyingRubbishResponse.getBody().getData().getElements().get(0);
            String category = classifyingRubbishResponseBodyDataElements.getCategory();
            Float categoryScore = classifyingRubbishResponseBodyDataElements.getCategoryScore();
            String rubbish = classifyingRubbishResponseBodyDataElements.getRubbish();
            Garbage garbage = new Garbage();
            garbage.setImage(image);
            garbage.setType(getType(category));
            garbage.setMatched(categoryScore * 100);
            garbage.setTitle(rubbish);
            return R.ok().put("row", garbage);
        } catch (TeaException teaException) {
            teaException.printStackTrace();
            return R.error("垃圾分类识别失败");
        }

    }

    private String getType(String type){
        if("可回收垃圾".equals(type)){
           return  "0";
        }else if("干垃圾".equals(type)){
            return  "1";
        }else if("湿垃圾".equals(type)){
            return  "2";
        }else if("有害垃圾".equals(type)){
            return  "3";
        }else{
            return  "4";
        }
    }

    /**
     * 垃圾分类信息上传
     */
    @PostMapping("sendGarbage")
    public R sendGarbage(@RequestBody Garbage garbage){
        garbage.setStatus("0");
        garbage.setUserId(getUserId());
        garbageService.save(garbage);
        return R.ok();
    }


    /**
     * 查询首页轮播图信息
     */
    @GetMapping("getBanner")
    public R getBanner(){
        List<Banner> all = bannerService.getAll();
        return R.ok().put("list", all);
    }

    /**
     * 垃圾分类信息分页查询
     */
    @GetMapping("getGarbage")
    public R getGarbage(@RequestParam(required = false, defaultValue = "") String type,
                        @RequestParam(required = false, defaultValue = "") String title,
                        @RequestParam(required = false, defaultValue = "") String status,
                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Garbage garbage = new Garbage();
        garbage.setType(type);
        garbage.setStatus(status);
        garbage.setTitle(title);
        PageResult<Garbage> list = garbageService.pageQuery(garbage, pageNum, pageSize);
        return R.ok().put("list", list);
    }

    /**
     * 垃圾分类详情查询
     */
    @GetMapping("getGarbageById")
    public R getGarbageById(Long id){
        Garbage row = garbageService.getById(id);
        return R.ok().put("row", row);
    }

    /**
     * 首页公告信息查询 查询最新8条通过
     */
    @GetMapping("getNoticeLimit8")
    public R getNoticeLimit8(){
        List<Notice> list = noticeService.getNoticeLimit8();
        return R.ok().put("list", list);
    }

    /**
     * 公告详情查询
     */
    @GetMapping("getNoticeById")
    public R getNoticeById(Long id){
        Notice row = noticeService.getById(id);
        return R.ok().put("row", row);
    }

    /**
     * 首页垃圾分类排行榜查询
     */
    @GetMapping("getRanking")
    private R getRanking(){
        List<User> list = usersService.getRanking();
        return R.ok().put("list", list);
    }

    /**
     * 垃圾分类公告分页查询
     */
    @GetMapping("getNotice")
    public R getNotice(
                        @RequestParam(required = false, defaultValue = "") String title,
                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "10") Integer pageSize){

        Notice notice = new Notice();
        notice.setTitle(title);
        PageResult<Notice> list = noticeService.pageQuery(notice, pageNum, pageSize);
        return R.ok().put("list", list);
    }

    /**
     * 垃圾分类知识分页查询
     */
    @GetMapping("getKnowledge")
    public R getKnowledge(
                       @RequestParam(required = false, defaultValue = "") String title,
                       @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        KnowledgeWithBLOBs knowledgeWithBLOBs = new KnowledgeWithBLOBs();
        knowledgeWithBLOBs.setTitle(title);
        PageResult<KnowledgeWithBLOBs> list = knowledgeService.pageQuery(knowledgeWithBLOBs, pageNum, pageSize);
        return R.ok().put("list", list);
    }

    /**
     * 垃圾分类知识详情查询
     */
    @GetMapping("getKnowledgeById")
    public R getKnowledge(Long id){
        KnowledgeWithBLOBs row = knowledgeService.getById(id);
        return R.ok().put("row", row);
    }

    /**
     * 礼品分页查询
     */
    @GetMapping("getGoods")
    public R getGoods(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Goods goods = new Goods();
        goods.setName(name);
        PageResult<Goods> list = goodsService.pageQuery(goods, pageNum, pageSize);
        return R.ok().put("list", list);
    }

    /**
     * 礼品详情查询
     */
    @GetMapping("getGoodsById")
    public R getGoodsById(Long id){
        Goods goods = goodsService.getById(id);
        return R.ok().put("row", goods);
    }

    /**
     * 兑换礼品立即购买
     */
    @PostMapping("sendGoods")
    public R sendGoods(@RequestBody Order order){
        order.setStatus("0");
        order.setOrderNo(System.currentTimeMillis()+"");
        orderService.save(order);
        return R.ok();
    }


    /**
     * 兑换礼品付款
     */
    @PostMapping("/conversionGoods/{id}")
    public R conversionGoods(@PathVariable Long id){

        Order order = orderService.getById(id);

        // 查询用户信息
        User user = usersService.getById(order.getUserId());
        // 查询礼品信息
        Goods goods = goodsService.getById(order.getGoodsId());
        // 判断用户积分是否充足
        if(goods.getPoint() > user.getPoint()){
            return R.error("您的积分不足，无法兑换");
        }
        if(goods.getStore() == 0 ){
            return R.error("库存不足无法兑换");
        }
        // 扣除积分
        user.setPoint(user.getPoint() - goods.getPoint());
        usersService.update(user);
        // 礼品库存-1，销量+1
        goods.setSales(goods.getSales() + 1);
        goods.setStore(goods.getStore() - 1);
        goodsService.update(goods);
        // 更新订单状态
        Order orderNew = new Order();
        orderNew.setStatus("1");
        orderNew.setId(order.getId());
        orderService.update(orderNew);

        return R.ok();
    }

    /**
     * 我的订单分页查询
     */
    @GetMapping("getOrder")
    public R getOrder(
            @RequestParam(required = false, defaultValue = "") String status,
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        Order order = new Order();
        order.setUserId(getUserId());
        order.setStatus(status);
        PageResult<Order> list = orderService.pageQuery(order, pageNum, pageSize);
        return R.ok().put("list", list);
    }


    /**
     * 商品评价查询
     */
    @GetMapping("getMessage")
    public R getMessageByGoodsId(Long id){
        List<Order> list = orderService.getMessageByGoodsId(id);
        return R.ok().put("list", list);
    }

    /**
     * 订单评价
     */
    @PostMapping("sendOrderContent")
    public R sendOrderContent(@RequestBody Order order){
        order.setStatus("2");
        orderService.update(order);
        return R.ok();
    }

    /**
     * 充值
     */
    @PostMapping("account/{money}")
    public R sendOrderContent(@PathVariable Integer money){
        User user = usersService.getById(getUserId());
        user.setPoint(user.getPoint() + money);
        usersService.update(user);
        return R.ok();
    }

}
