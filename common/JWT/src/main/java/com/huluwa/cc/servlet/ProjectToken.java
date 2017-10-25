/*
package com.huluwa.cc.servlet;
import com.huluwa.cc.jwt.Jwt;
import com.huluwa.cc.jwt.TokenState;
import com.sun.deploy.net.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class ProjectToken {
    */
/**
     * 生成token
     * @param //userid 用户id
     * @return token
     *//*

    @RequestMapping("/login")
    public JSONObject generatingToken(String userName,String password){
        JSONObject resultJSON=new JSONObject();
        //用户名密码校验成功后，生成token返回客户端
        if("haoxiaoyong".equals(userName)&&"1234".equals(password)){
            //生成token
            Map<String , Object> payload=new HashMap<String, Object>();
            Date date=new Date();
            payload.put("uid", "admin");//用户ID
            payload.put("iat", date.getTime());//生成时间
            payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
            String token= Jwt.createToken(payload);
            System.out.println(token);

            try {
                resultJSON.put("success", true);
                resultJSON.put("msg", "登陆成功");
                resultJSON.put("token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }else{
            try {
                resultJSON.put("success", false);
                resultJSON.put("msg", "用户名密码不对");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        //输出结果

            return resultJSON;
    }

    public static void main(String[] args) {
        ProjectToken p = new ProjectToken();

      p.generatingToken( "haoxiaoyong","1234");


    }

    */
/**
     * 校验token是否合法
     * @param request
     * @param response
     *//*

    @RequestMapping("/")
    public void checkToken(HttpServletRequest request, HttpResponse response) {
        if(request.getRequestURI().endsWith("/servlet/login")){
            //登陆接口不校验token，直接放行
            // chain.doFilter(request, response);
            return;
        }
        //其他API接口一律校验token
        System.out.println("开始校验token");
        //从请求头中获取token
        String token=request.getHeader("token");
        Map<String, Object> resultMap=Jwt.validToken(token);
        TokenState state=TokenState.getTokenState((String)resultMap.get("state"));
        switch (state) {
            case VALID:
                //取出payload中数据,放入到request作用域中
                request.setAttribute("data", resultMap.get("data"));
                //放行
                // chain.doFilter(request, response);
                break;
            case EXPIRED:
            case INVALID:
                System.out.println("无效token");
                //token过期或者无效，则输出错误信息返回给ajax
                JSONObject outputMSg=new JSONObject();
                try {
                    outputMSg.put("success", false);
                    outputMSg.put("msg", "您的token不合法或者过期了，请重新登陆");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 //output(outputMSg.toJSONString(), response);
                break;
        }
    }

}
*/
