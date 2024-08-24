# Sponge k8s 模式部署说明 (for water)

## 请在完整的看完文档后，再开始动手!!!

## 一、开始部署服务

* 添加 water/spongeadmin 服务（镜像：noearorg/spongeadmin:2.10.1）
  * 镜像端口：8171
  * 对外端口：8171
  * 建议1个副本即可
  * 要配置外网访问地址，建议加域名
  
* 添加 water/rockrpc 服务（镜像：noearorg/rockrpc:2.10.1）
  * 镜像端口：8181
  * 对外端口：8181
  * 建议2个副本起步
  * 不需要配置外网访问

* 添加 water/trackapi 服务（镜像：noearorg/trackapi:2.10.1）
  * 镜像端口：8182
  * 对外端口：8182
  * 建议1个副本起步
  * 要配置外网访问地址，建议加比较短的域名（做短地址服务）；如果不需要这个功能，这个服务可以去掉



在 water 域下，不需要设置 solon.cloud.water.server 环境变量。但可以设置时区之间的环境变量

## 二、补充

建议再部署：gritdock，实现跨系统统一管理。大概流程：

* 1，部署 gritdock （假如配置域名为：x.x.x）
* 2，进入 http://x.x.x/grit/ ，登录后修改 spongeadmin 与 wateradmin 资源空间的地址
* 3，进入 water 配置管理/属性配置 grit/gritclient.yml 的 server.session.state.domain 值为 多系统公共的根域名
* 4，打开 http://x.x.x ，进入跨系统统一管理后台


同时要求 gritdock, wateradmin, spongeadmin 在同一个根域或二级域下，例：

* gritdock 服务：http://admin.dev.x.x
* wateradmin 服务：http://admin.water.dev.x.x
* spongeadmin 服务：http://admin.sponge.dev.x.x

那它们的共同二级域为：dev.x.x.x


**附：**

gritdock 项目地址：https://gitee.com/noear/grit


