# 微服务的通用引用

#微服务里面获取当前请求的用户信息
UserScope scope=App.userScope();

#网关会做拦截，但涉及变更的操作，如删、改、添，要在方法里面做业务逻辑控制
// warm: 重大关键操作需要业务逻辑控制，如只允许admin或system身份才能操作
if (!App.isScopeOf(ScopeType.adminOrsystem)) {
	return App.refused();
}