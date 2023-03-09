# jiushiSecurity

实现了spring security + oauth2.0框架
1、由gateway鉴权
2、auth负责登录 并且自定义登录方式，生成token令牌
3、gateway验签通过token把 请求转发到其他微服务
