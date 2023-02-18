一、授权码模式
1、先通过授权码模式  登录 post
http://192.168.31.61:52000/jiushi/auth/oauth/authorize?client_id=c1&response_type=code&scope=ROLE_ADMIN&redirect_uri=http://www.baidu.com
2、然后获取到token  post
192.168.31.61:52000/jiushi/auth/oauth/token?client_id=c1&client_secret=123&grant_type=authorization_code&code=9NGO3R&username=zhangsan&password=123&redirect_uri=http://www.baidu.com


二、密码模式  post 账户密码
192.168.31.61:52000/jiushi/auth/oauth/token?client_id=c1&client_secret=123&grant_type=password&username=zhangsan&password=123&redirect_uri=http://www.baidu.com
