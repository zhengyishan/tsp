# tsp 车联网功能陆续抽空完善
基于GB32960协议数据接入网关

dyy-gateway-server

	功能:车联网数据接入网关

	主要技术:Netty,Kafka,内存+Redis二级缓存/订阅发布

	特点:高性能,高并发,高可用,支持K8S同POD多副本集群部署,横向拓展扩容
	
	测试简介:基于4c8g云虚拟主机,IO密集型机器实测单节点TPS稳定高达13000/s。最长压测时间24小时。


dyy-gateway-tcu

    功能:模拟Tbox终端与TSP联调测试,提供RestFul接口给测试人员使用,降低测试成本。提供实时在线监控车辆功能

	主要技术:Netty,Redis缓存/订阅发布,Websocket

	特点:仿真模拟,降低测试成本,提供实时在线监控车辆功能


dyy-gateway-dispatcher

	功能:将接入数据标准封装成业务对象分发到不同Topic

	主要技术:Kafka,Redis缓存

	特点:纯内存解析,速度快。高性能,高并发,高可用,横向拓展扩容
