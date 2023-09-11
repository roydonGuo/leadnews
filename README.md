# 头条新闻

springcloud分布式微服务项目

# 环境配置
nacos：

1、拉取镜像

docker pull nacos/nacos-server:1.2.0

2、创建容器

docker run --env MODE=standalone --name nacos --restart=always  -d -p 8848:8848 nacos/nacos-server:1.2.0

