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






