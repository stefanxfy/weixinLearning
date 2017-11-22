## 接入概述
### 接入微信公众平台开发，开发者需要按照如下步骤完成：

#### 1、填写服务器配置

#### 2、验证服务器地址的有效性

#### 3、依据接口文档实现业务逻辑

## 第一步：填写服务器配置
![image](http://mmbiz.qpic.cn/mmbiz/PiajxSqBRaEIQxibpLbyuSK3AXezF3wer8dofQ1JMtIBXKX9HmjE1qk3nlG0vicvB55FVL5kgsGa5RgGKRc9ug87g/0?wx_fmt=png)
## 第二步：验证消息的确来自微信服务器
#### 开发者提交信息后，微信服务器将发送GET请求到填写的服务器地址URL上，GET请求携带参数如下表所示：

参数 | 描述
------|-------
signature  | 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
timestamp | 	时间戳
nonce | 	随机数
echostr | 	随机字符串

#### 开发者通过检验signature对请求进行校验（下面有校验方式）。若确认此次GET请求来自微信服务器，请原样返回echostr参数内容，则接入生效，成为开发者成功，否则接入失败。加密/校验流程如下：

##### 1）将token、timestamp、nonce三个参数进行字典序排序

2）将三个参数字符串拼接成一个字符串进行sha1加密

3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信

#### 代码示意：

### 1）
```
public static boolean checksignature(String signature,
			String timestamp,String nonce){
        //String token = "stefan";
		String[] arr= new String[]{"stefan",timestamp,nonce};
		Arrays.sort(arr);
		StringBuffer content=new StringBuffer();
		for (int i=0;i<arr.length;i++){
			content.append(arr[i]);
		}
		//sha1
		String temp=getSha1(content.toString());
		return temp.equals(signature);
		
	}
```
### 2）

```
public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
```
### 3）

```
@GetMapping("/wxconn")
    public String wxcheck(String signature, String timestamp, String nonce, String echostr) {
        if (checkUtil.checksignature(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return null;
        }
    }
```
### 第三步：依据接口文档实现业务逻辑
#### 验证URL有效性成功后即接入生效，成为开发者。你可以在公众平台网站中申请微信认证，认证成功后，将获得更多接口权限，满足更多业务需求。

### **==微信开发从这才刚刚开始，你已经打开微信开发的大门，就差走进去了==**

[获取token第一步教程](https://github.com/StefanPython/weixinLearning/blob/master/getToken.md)
[自定义菜单的创建，查询，删除](https://github.com/StefanPython/weixinLearning/blob/master/menuDIY.md)






