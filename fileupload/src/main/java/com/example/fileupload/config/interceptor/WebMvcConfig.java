package com.example.fileupload.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 添加自定义的拦截器（注册拦截器）
 *   方法一：extends       WebMvcConfigurationSupport（拦截器的配置类，主要配置拦截器的相关参数，
 *       并继承以下方法：
 *         addInterceptors：添加拦截器实例
 *         addResourceHandlers：静态文件访问配置
 *         configureViewResolvers:视图配置
 *   ）
 *   方法二：implements    WebMvcConfigurer
 *   方法三：extends       WebMvcConfigurerAdapter (WebMvcConfigurer 的实现类)
 * 
 * 在该类标注@Configuration，表明它是一个配置类
 * 
 * 一个项目中只能有一个继承WebMvcConfigurationSupport的@Configuration类（使用@EnableMvc效果相同），
 * 如果存在多个这样的类，只有一个配置可以生效。推荐使用implements WebMvcConfigurer 的方法自定义mvc配置。
 * 
 * WebMvcConfigurationSupport的作用：
 *   1.可以配置多个路由，放到一个配置类中
 *   2.添加自定义拦截器
 * @author Administrator
 *
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport{

	/**
	 * 添加静态资源映射路径，css、js等都放在classpath下的static中
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/**
		 * addResourceHandler 指的是对外暴露的访问路径
		 * addResourceLocations 指的是文件配置的目录
		 */
		
		//文件上传路径映射
		registry.addResourceHandler("/mimi/upload/**")
		.addResourceLocations("file:D:/mimi/upload/");
		
		super.addResourceHandlers(registry);
	}
}
