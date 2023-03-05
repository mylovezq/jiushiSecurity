WebSecurityConfig的配置
AuthorizationServer的配置
1、客户端详情服务  信息一般就是存在数据库
2、然后配置授权码存储
3、TokenGranterConfig  一般就是重写的
4、然后配置token  配置token由redis存储，过期时间、定义的放入的值参考jwtAccessTokenConverter和JiushiUserAuthenticationConverter
   同时设置keyPair()
5、运行表单 security.allowFormAuthenticationForClients();				//表单认证（申请令牌）




一、授权码模式
1、先通过授权码模式  登录 get
https://2092u8x490.yicp.fun/jiushi/auth/oauth/authorize?client_id=c1&response_type=code&scope=ROLE_ADMIN&redirect_uri=http://www.baidu.com
2、然后获取到token  get
https://2092u8x490.yicp.fun/auth/oauth/token?client_id=c1&client_secret=123&grant_type=authorization_code&code=LyIOGa&username=zhangsan&password=123&redirect_uri=http://www.baidu.com


二、密码模式  post 账户密码
https://2092u8x490.yicp.fun/jiushi/auth/oauth/token?client_id=c1&client_secret=123&grant_type=password&username=zhangsan&password=123&redirect_uri=http://www.baidu.com


三、自定定义  手机号（用户名）密码、短信、三方登录  
   切记在网关放开白名单 /jiushi/auth/login/getSessionId 和  auth白名单。
   WebSecurityConfig和CustomerCorsFilter配置关闭cors。
   数据库要配置登录模式、client_id client_secret password

   
 ①三方登录  由程序提供code
https://2092u8x490.yicp.fun/jiushi/auth/login/getSessionId?code=091RKP000iCFzP157920066mMy4RKP03

②然后请求自定义的登录方式
https://2092u8x490.yicp.fun/jiushi/auth/oauth/token?encryptedData=ObPNjZSH26Nc1WBniYKZ6gJd8a%2F3Ea%2BgDSrTgsY9gFJvPndHQ3Ufj90B7AUTRtxIPRphxge%2BLJBHMFZdxTdTixeH5gKiJhTauwqWHcmyoC9m6br0BH1r8Rz8BWtmV1dLV%2FM4jgVPbpTQBTN%2BE2FV6BX5k%2FVu83Lmjki4ODd6bG%2FoNcZgZ03ceCkesXmJn0d57w7lVYeM19WJ%2BzEiYHdcOe%2F5M3fBZdMTCa3tDX%2Bvdg3%2FVKX8EVDEpxtKNm6Y7YaO%2BKT8DEIMw6H3fqIAF1PWrr4%2BHMmxV4mFJBGkb%2F76drUDw5UXykH%2FwxA6erA28F3cjQxkNtuLsd84ENz2N%2FgHPuvz2Qv7AKYbQdhNtLgpCb58nuaeCYpvXwjIBu205cHe3tkq5TLlSRc1bpMU%2FfwTQyAQKZF%2BQxZhFbGD7Cr0l8P3JUSRvI6f69J53ZjsA9Nnp2XFn2PkS7hXMjQO6aduBg%3D%3D&iv=XODzobw%2BHLG7cAu21ZkPAg%3D%3D
&sessionId=042d0bec-74b7-4330-96ea-732d614251a6
&grant_type=third_party
&thirdPartyType=2
&client_id=c1&client_secret=123
③可以重写/oauth/token,并且自定义jwt
  jwt自定义：1、先生成密钥对jwt.jks  并且提供接口出来  在nacos配置密钥地址 参考KeyPairController
            2、TokenConfig中配置对应的token存储方式、JwtAccessTokenConverter（可自定义jwt中的内容  JiushiUserAuthenticationConverter和enhance均可）




    自定义登录方式
    1、先定义token 参考ThirdPartyAuthenticationToken
    2、再构建 ThirdPartyTokenGranter  个人觉得就是构建一个没有授权的ThirdPartyAuthenticationToken，把前端传入的主要参数做封装 校验，
    3、然后重写掉原先的ThirdPartyTokenGranter，对TokenGranterConfig进行配置，把自定的ThirdPartyTokenGranter加入进去
    4、再定义ThirdPartyAuthenticationProvider 根据参数生产UserDetails，再生产  ThirdPartyAuthenticationToken
    5、再把ThirdPartyAuthenticationProvider放到ThirdPartySecurityConfig中
    6、然后再在WebSecurityConfig的protected void configure(HttpSecurity http) throws Exception 方法中apply()



四、网关会统一拦截所有请求
public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {