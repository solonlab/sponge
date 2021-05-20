
[![Maven Central](https://img.shields.io/maven-central/v/org.noear/rock.client.svg)](https://mvnrepository.com/search?q=g:org.noear%20AND%20rock.client)

` QQ交流群：22200020 `

# Sponge 是什么？

一个业务中台...

# Sponge 部署说明

## 请在完整的看完文档后，再开始动手!!!

## 环境要求说明

* mysql8：做为主库（字符集：utf8mb4，排序集：utf8mb4_general_ci）
* memcached：做为缓存使用
* redis：做为id生成与监控数据记录
* jdk11：做为运行时用（或者JDK8）
* water：做为技术支撑平台（参考 Water 部署说明）

> 建议使用 centos7+ 部署

## 开始部署

### 一、初始化数据库（参考db目录下的sql文件）

| 数据库 | 说明 |
| -------- | -------- |
| sponge_rock | 应用控制库 |
| sponge_track | 价值跟踪库 |

### 二、初始化配置

进入 water 控制台，修改 sponge 配置组下的相关配置

| 配置组 | 配置键 | 说明 |
| -------- | -------- | -------- |
| sponge     | sponge_cache     | memcached 链接配置（可以与water的复用）     |
| sponge     | sponge_redis     | redis 链接配置（可以与water的复用）     |
| sponge     | sponge_rock     | sponge_rock 数据库的链接配置     |
| sponge     | sponge_track     | sponge_track 数据库的链接配置     |
| sponge     | track_uri     | 价值跟踪的服务地址     |

### 三、部署流程说明

1. 启动 trackapi.jar （加域名和nginx配置）
2. 启动 spongeadmin.jar （加域名和nginx配置）
3. 启动 rockrpc.jar （不需要 nginx 配置；生产环境多实例部署）

> 服务运行，建议配置成System Service进行控制



### 附：补充说明

* sponge 管理抬台初始账号与密码

> 账号：admin 密码：bcf1234

* 在linux下建议用配置成service，由 systemctl 命令管理（以waterapi例）

```ini
#
#add file: /etc/systemd/system/waterapi.service
[Unit]
Description=waterapi
After=syslog.target

[Service]
ExecStart=/usr/bin/java -jar /data/sss/water/waterapi.jar
SuccessExitStatus=143
Restart=on-failure

[Install]
WantedBy=multi-user.target

# 操控命令部分参考：
# systemctl restart waterapi  #重启服务
# systemctl enable waterapi   #启用服务（系统重启时，自动启动该服务）
# systemctl start waterapi    #启动服务
# systemctl stop waterapi     #停止服务
```

* water域的nginx配置示例（注意真实的ip转发）

```ini
upstream waterapi{
    server 127.0.0.1:9370 weight=10;
    server 127.0.0.1:9371 weight=10;
}
server{
    listen 80;
    server_name water;
    
    location / {
        proxy_pass http://waterapi;
        proxy_set_header  X-Real-IP  $remote_addr;
        proxy_set_header  X-Forwarded-For  $proxy_add_x_forwarded_for;
        proxy_set_header  Host $http_host;
    }
}
```



