# Feign-Support

[![Build Status](https://travis-ci.com/jingwux/feign-support.svg?branch=master)](https://travis-ci.com/jingwux/feign-support)
[![codecov](https://codecov.io/gh/jingwux/feign-support/branch/master/graph/badge.svg?token=83I3RFWUTU)](https://codecov.io/gh/jingwux/feign-support)

一系列支持 Feign 相关的组件，包括：

- Feign 远程调用结果解码工具（AbstractPayloadDecoder）
- ...

[CHANGELOG](./CHANGELOG.md)

## 如何使用

### AbstractPayloadDecoder

在远程调用完成后，针对多种类型的返回数据结果进行自定义解析

#### 实现 Payload<T> 接口

 OtherPayload 为 Feign 返回结果的 json 数据映射的 Bean
 
 实现 parseData() 方法，可在其中进行自定义的数据解析操作，然后返回解析结果 T (所映射的 Bean)
 
 [详细参考示例](https://github.com/jingwux/feign-support/blob/master/src/test/java/com/jingwu/support/feign/AbstractPayloadDecoderTest.java)

```json
{
  "payload": {
    "foo": "bar"
  },
  "code": "0",
  "msg": "ok"
}
```

```java
@Data
private static class OtherPayload<T> implements Payload<T> {
    private String msg;
    private T payload;
    private String code;

    @Override
    public T parseData() {
        return this.payload;
    }
}

@Data
@ToString
private static class MockData {
    private String foo;
}
```

#### 继承 AbstractPayloadDecoder<T>

```java
private static class TestPayloadDecoder extends AbstractPayloadDecoder<OtherPayload> {
    public TestPayloadDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        super(messageConverters);
    }

    @Override
    protected void check(OtherPayload otherPayload) {
        // do something
    }
}
```

#### In Feign Configuration

```java
class Configuration {
       @Autowired
       private ObjectFactory<HttpMessageConverters> messageConverters;
      
       @Bean
       public Decoder decoder() {
           return new TestPayloadDecoder(messageConverters);
       }
}
```


## TODO
- 处理不同响应状态下不同的数据结构
- 响应异常处理
- ...