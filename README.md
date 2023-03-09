# jiushiSecurity


实现了spring security + oauth2.0框架
1、由gateway鉴权
2、auth负责登录 并且自定义登录方式，生成token令牌
3、gateway验签通过token把 请求转发到其他微服务


参考：https://github.com/mylovezq/spring-cloud-gateway-oauth2

我们理想的微服务权限解决方案应该是这样的，认证服务负责认证，网关负责校验认证和鉴权，其他API服务负责处理自己的业务逻辑。安全相关的逻辑只存在于认证服务和网关服务中，其他服务只是单纯地提供服务而没有任何安全相关逻辑。
