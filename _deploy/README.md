
提醒：sponge 业务技撑中台，基于 water 服务而建。部署时，须提前准备好 water 环境

### 两种部署模式：

* docker-compose：基于 docker-compose 模式部署参考
* k8s ：基于 k8s 模式部署参考


### 镜像列表

| 镜像                         | 镜像端口    | 说明        |
|----------------------------|-------|-----------|
| noearorg/spongeadmin:2.1.0 | 8171  | 管理控制台     |
| noearorg/rockrpc:2.1.0           | 8181  | 应用控制接口服务  |
| noearorg/trackapi:2.1.0          | 8182  | 价值跟踪短地址服务 |


### 部署流程

#### 1) 初始化数据库

* 新建 sponge_rock 库，并执行脚本 sql/sponge_rock.sql
* 新建 sponge_track 库，并执行脚本 sql/sponge_track.sql


建库编码：utf8mb4；排序：utf8mb4_general_ci

#### 2) 进入 Water 配置管理，修改配置

进入 water 控制台，修改 sponge 配置组下的相关配置

| 配置组 | 配置键 | 说明                              |
| -------- | -------- |---------------------------------|
| sponge     | sponge_cache     | 缓存链接配置（可以与water的复用）             |
| sponge     | sponge_rock     | sponge_rock 数据库的链接配置            |
| sponge     | sponge_track     | sponge_track 数据库的链接配置           |
| sponge     | track_uri     | 价值跟踪的服务地址，在trackapi服务域名安排好后再来修改 |


其中，sponge/sponge_redis 配置已弃用。

#### 3) 使用镜像做需要的部署

| 镜像  | 描述          | 备注                                   |
|-----|-------------|--------------------------------------|
| noearorg/spongeadmin:2.1.0 | sponge 管理台后 | 管理后台                                 |
| noearorg/trackapi:2.1.0 | 价值跟踪服务      | 一个短地址统计服务                            |
| noearorg/rockrpc:2.1.0 | 应用控制服务 | 一个rpc服务，提供渠道配置与国际化配置等能力 |

为什么需要提供渠道配置支持？

* 这符合透明原因，在一个中台上能看到
* 夸张一点，如果你有100个分发渠道，每个渠道的标识与密钥如何管理？每个渠道要用户统计
* 每个分发渠道还有自己的个人化配置怎么办？比如流量结算费用不同
* 每个渠道的设置，修改后想要马上生效怎么办？
* 如果密钥被人捕获，让它失效怎么办？
* 国际化配置有类似的道理
* 还有更新管理






