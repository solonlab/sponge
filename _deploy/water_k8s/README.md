# Sponge k8s 模式部署说明 (for water)

## 请在完整的看完文档后，再开始动手!!!


## 一、开始部署服务

* 添加 water/spongeadmin 服务（镜像：noearorg/spongeadmin:latest）
  * 镜像端口：8080
  * 对外端口：8080
  * 建议1个副本即可
  * 要配置外网访问地址，建议加域名

* 添加 water/trackapi 服务（镜像：noearorg/trackapi:latest）
  * 镜像端口：8080
  * 对外端口：8080
  * 建议1个副本起步
  * 要配置外网访问地址，建议加比较短的域名（做短地址服务）；如果不需要这个功能，这个服务可以去掉

* 添加 water/rockrpc 服务（镜像：noearorg/rockrpc:latest）
  * 镜像端口：8080
  * 对外端口：8080
  * 建议2个副本起步
  * 不需要配置外网访问


在 water 域下，不需要设置 solon.cloud.water.server 环境变量。但可以设置时区之间的环境变量

## 二、补充

建议再部署：gritdock，实现跨系统统一管理。大概流程：

* 1，部署 gritdock （假如配置域名为：x.x.x）
* 2，进入 http://x.x.x/grit/ ，登录后修改 spongeadmin 与 wateradmin 资源空间的地址
* 3，进入 water 配置管理/属性配置 grit/gritclient.yml 的 server.session.state.domain 值为 多系统公共的根域名
* 4，打开 http://x.x.x ，进入跨系统统一管理后台


gritdock 项目地址：https://gitee.com/noear/grit


