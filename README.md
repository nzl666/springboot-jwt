# springboot-jwt
前后端分离.API访问安全
## 前端
使用vue开发的,数据都是ajax向后台请求的
## 后台
使用的springboot,jwt使用的是servlet3.0
### 描述
前台ajax访问后台,后台过滤器拦截除登录以外的全部url,用户登录成功,生成token返回到前台,前台访问后台数据必须带token来请求,后台过滤器拦截,通过jwt判断token是否合法,合法统一访问数据,不合法返回相应的信息
