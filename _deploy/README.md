
> sponge 业务技撑中台，基于 water 服务而建。部署时，必须先初始化 water 环境

### 两种部署模式：

* docker-compose：基于 docker-compose 模式部署参考
* k8s ：基于 k8s 模式部署参考


### 部署流程

#### 1) 初始化数据库

* 新建 sponge_rock 库，并执行脚本 sql/sponge_rock.sql
* 新建 sponge_track 库，并执行脚本 sql/sponge_track.sql

#### 2) 进入 Water 配置管理，修改配置

修改 sponge 下的相关


#### 3) 使用镜像做需要的部署

| 镜像  | 描述          | 备注                      |
|-----|-------------|-------------------------|
| noearorg/spongeadmin | sponge 管理台后 | 管理后台                    |
| noearorg/trackapi | 价值跟踪服务      | 一个短地址统计服务               |
| noearorg/rockrpc | 应用控制服务 | 一个rpc服务，是srww框架的主人体依赖之一 |