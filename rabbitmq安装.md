### 安装过程

1. 安装依赖环境
```
yum install build-essential openssl openssl-devel unixODBC unixODBC-devel make gcc gcc-c++ kernel-devel m4 ncurses-devel
```

2. 下载软件包及其依赖包：

```
wget www.rabbitmq.com/releases/erlang/erlang-18.3-1.el7.centos.x86_64.rpm
wget http://repo.iotti.biz/CentOS/7/x86_64/socat-1.7.3.2-5.el7.lux.x86_64.rpm
wget www.rabbitmq.com/releases/rabbitmq-server/v3.6.5/rabbitmq-server-3.6.5-1.noarch.rpm
```

3. 安装

```
rpm -ivh erlang-18.3-1.el7.centos.x86_64.rpm
rpm -ivh socat-1.7.3.2-5.el7.lux.x86_64.rpm
rpm -ivh rabbitmq-server-3.6.5-1.noarch.rpm

```

4. 配置

```
vim /usr/lib/rabbitmq/lib/rabbitmq_server-3.6.5/ebin/rabbit.app

loopback_users 中的 <<"guest">>,只保留"guest"

```

5. 启动和停止

```
启动 rabbitmq-server start &
停止 rabbitmqctl app_stop

```

6. 查看是否成功：

```
lsof -i:5672

```

### 插件管理

1. 查看插件列表

```
rabbitmq-plugins list

```

2. 启动management插件

```
rabbitmq-plugins enable rabbitmq_management
```

3. 至此已经安装配置完成，访问管控台查看信息

```
http://YourIp:15672/
```

4. 如果无法访问，可能是防火墙的问题，可以关闭防火墙试试

```
service firewalld stop  
```
