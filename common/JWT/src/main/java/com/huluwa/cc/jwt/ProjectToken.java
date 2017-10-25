package com.huluwa.cc.jwt;

import com.huluwa.cc.config.TokenStateCode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ProjectToken {
    /**
     * 生成token
     * @param userid 用户id
     * @return token
     */
    public String generatingToken(String userid){
        //生成token
        Map<String , Object> payload=new HashMap<String, Object>();
        Date date=new Date();
        payload.put("uid", userid);//用户ID
        payload.put("iat", date.getTime());//生成时间
        payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
        String token= Jwt.createToken(payload);
        System.out.println("------------------"+token);
        return token;

    }

    public static void main(String[] args) {
        ProjectToken p = new ProjectToken();
        System.out.println(p.generatingToken("1111"));
    }

    /**
     * 校验token是否合法
     * @param request
     * @param response
     */
    public int checkToken(HttpServletRequest request, HttpServletResponse response) {
        //其他API接口一律校验token
        System.out.println("开始校验token");
        TokenStateCode tokenStateCode = new TokenStateCode();
        //从请求头中获取token
        String token=request.getHeader("token");
        Map<String, Object> resultMap=Jwt.validToken(token);
        TokenState state=TokenState.getTokenState((String)resultMap.get("state"));
        int code = 102;
        switch (state) {
            case VALID:
                code = tokenStateCode.SUCCESS;
                break;
            case EXPIRED:
                code = tokenStateCode.TIMEOUT;
            break;
            case INVALID:
                code = tokenStateCode.NOPERMISSION;
                break;
        }
        return code;
    }
}
