# apts
快速部署项目-APTS
使用SpringBoot搭建APTS快速部署项目 1.0


	•创建数据库，导入sql脚本
	•在对应的yml文件里面配置数据库的信息，连接地址、用户名、密码
	•配置jar包和日志存放地址，在yml文件下面配置，jarFiles是jar包存放路径，projectFiles：发布的项目运行会输出的日志地址
	•使用maven打包，然后把jar包放在linux上运行（注意要有jdk8以上环境）

启动后访问应用管理，会显示自己部署所有的应用，可以进行添加应用，编辑，启动，停止，查看实时日志

