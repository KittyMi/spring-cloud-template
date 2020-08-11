下载seata-server
修改conf/registry.conf，将registry.type修改为eureka。即将seata-server注册到eureka
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "eureka"

  eureka {
    serviceUrl = "http://eureka1:9001/eureka"
    application = "seata-server"
    weight = "1"
  }
}
启动eureka-server
启动seata-server