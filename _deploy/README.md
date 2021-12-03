
提醒：sponge 业务技撑中台，基于 water 服务而建。部署时，必须准备好 water 环境

### 两种部署模式：

* docker-compose：基于 docker-compose 模式部署参考
* k8s ：基于 k8s 模式部署参考


### 部署流程

#### 1) 初始化数据库

* 新建 sponge_rock 库，并执行脚本 sql/sponge_rock.sql
* 新建 sponge_track 库，并执行脚本 sql/sponge_track.sql


编码：utf8mb4；排序：utf8mb4_general_ci

#### 2) 进入 Water 配置管理，修改配置

进入 water 控制台，修改 sponge 配置组下的相关配置

| 配置组 | 配置键 | 说明 |
| -------- | -------- | -------- |
| sponge     | sponge_cache     | memcached 链接配置（可以与water的复用）     |
| sponge     | sponge_redis     | redis 链接配置（可以与water的复用）     |
| sponge     | sponge_rock     | sponge_rock 数据库的链接配置     |
| sponge     | sponge_track     | sponge_track 数据库的链接配置     |
| sponge     | track_uri     | 价值跟踪的服务地址     |


#### 3) 使用镜像做需要的部署

| 镜像  | 描述          | 备注                                    |
|-----|-------------|---------------------------------------|
| noearorg/spongeadmin | sponge 管理台后 | 管理后台                                  |
| noearorg/trackapi | 价值跟踪服务      | 一个短地址统计服务                             |
| noearorg/rockrpc | 应用控制服务 | 一个rpc服务，是srww框架的主要依赖（提供渠道配置与国际化配置等能力） |








