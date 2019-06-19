package com.atguigu;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.atguigu.config.AppConfig;
import com.atguigu.config.RootConfig;
import com.atguigu.controller.MyFirstFilter;
/*1、Servlet容器在启动的时候，会扫描每个jar包下的META-INF/services/javax.servlet.ServletContainerInitializer
spring-web-4.3.11.RELEASE.jar包的META-INF/services/javax.servlet.ServletContainerInitializer,该文件中包含org.springframework.web.SpringServletContainerInitializer
          该类上的注解为@HandlesTypes(WebApplicationInitializer.class)*/
//MyWebAppInitializer(自己创建的类继承AbstractAnnotationConfigDispatcherServletInitializer)
// AbstractAnnotationConfigDispatcherServletInitializer实现了抽象类 AbstractDispatcherServletInitializer
	// AbstractDispatcherServletInitializer 类的onStartup(ServletContext servletContext)调用了registerDispatcherServlet(servletContext);
			/*该方法实现：
			 *  WebApplicationContext servletAppContext = createServletApplicationContext(); //创建web容器
			 *  FrameworkServlet dispatcherServlet = createDispatcherServlet(servletAppContext);
				dispatcherServlet.setContextInitializers(getServletApplicationContextInitializers());	
				ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
				registration.setLoadOnStartup(1);
				registration.addMapping(getServletMappings());//获取映射   该方法子类中实现
				registration.setAsyncSupported(isAsyncSupported());
				Filter[] filters = getServletFilters();
					if (!ObjectUtils.isEmpty(filters)) {
						for (Filter filter : filters) {
							registerServletFilter(servletContext, filter);//该方法子类中实现
						}
					}
			
			*/
/*AbstractDispatcherServletInitializer实现了抽象类AbstractContextLoaderInitializer
 *   AbstractContextLoaderInitializer类的onStartup(ServletContext servletContext)方法调用了registerContextLoaderListener(servletContext);
 *      该想法实现：
 *      	WebApplicationContext rootAppContext = createRootApplicationContext();//创建根容器
			if (rootAppContext != null) {
				ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
				listener.setContextInitializers(getRootApplicationContextInitializers());
				servletContext.addListener(listener);
			}
* 
 * */
/*1、AbstractDispatcherServletInitializer接口方法createRootApplicationContext()创建根容器：根容器配置文件通过 getRootConfigClasses()获取
	AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
	Class<?>[] configClasses = getServletConfigClasses();//该方法子类中实现
	rootAppContext.register(configClasses);*/
/*
 * 2、AbstractDispatcherServletInitializer接口方法createServletApplicationContext()创建web容器：web容器配置文件通过getServletConfigClasses()
 *    AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
 *    Class<?>[] configClasses = getRootConfigClasses();//该方法子类中实现
 *    servletAppContext.register(configClasses);
 * 
*/
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	//获取根容器的配置类；（Spring的配置文件）   父容器；
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[]{RootConfig.class};
	}

	//获取web容器的配置类（SpringMVC配置文件）  子容器；
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[]{AppConfig.class};
	}

	//获取DispatcherServlet的映射信息
	//  /：拦截所有请求（包括静态资源（xx.js,xx.png）），但是不包括*.jsp；
	//  /*：拦截所有请求；连*.jsp页面都拦截；jsp页面是tomcat的jsp引擎解析的；
	@Override
	protected String[] getServletMappings() {
		// TODO Auto-generated method stub
		return new String[]{"/"};
	}
@Override
protected Filter[] getServletFilters() {
	// TODO Auto-generated method stub
	return new Filter[] {new MyFirstFilter()};
}
}
