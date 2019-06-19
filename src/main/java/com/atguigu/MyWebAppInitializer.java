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
/*1��Servlet������������ʱ�򣬻�ɨ��ÿ��jar���µ�META-INF/services/javax.servlet.ServletContainerInitializer
spring-web-4.3.11.RELEASE.jar����META-INF/services/javax.servlet.ServletContainerInitializer,���ļ��а���org.springframework.web.SpringServletContainerInitializer
          �����ϵ�ע��Ϊ@HandlesTypes(WebApplicationInitializer.class)*/
//MyWebAppInitializer(�Լ���������̳�AbstractAnnotationConfigDispatcherServletInitializer)
// AbstractAnnotationConfigDispatcherServletInitializerʵ���˳����� AbstractDispatcherServletInitializer
	// AbstractDispatcherServletInitializer ���onStartup(ServletContext servletContext)������registerDispatcherServlet(servletContext);
			/*�÷���ʵ�֣�
			 *  WebApplicationContext servletAppContext = createServletApplicationContext(); //����web����
			 *  FrameworkServlet dispatcherServlet = createDispatcherServlet(servletAppContext);
				dispatcherServlet.setContextInitializers(getServletApplicationContextInitializers());	
				ServletRegistration.Dynamic registration = servletContext.addServlet(servletName, dispatcherServlet);
				registration.setLoadOnStartup(1);
				registration.addMapping(getServletMappings());//��ȡӳ��   �÷���������ʵ��
				registration.setAsyncSupported(isAsyncSupported());
				Filter[] filters = getServletFilters();
					if (!ObjectUtils.isEmpty(filters)) {
						for (Filter filter : filters) {
							registerServletFilter(servletContext, filter);//�÷���������ʵ��
						}
					}
			
			*/
/*AbstractDispatcherServletInitializerʵ���˳�����AbstractContextLoaderInitializer
 *   AbstractContextLoaderInitializer���onStartup(ServletContext servletContext)����������registerContextLoaderListener(servletContext);
 *      ���뷨ʵ�֣�
 *      	WebApplicationContext rootAppContext = createRootApplicationContext();//����������
			if (rootAppContext != null) {
				ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
				listener.setContextInitializers(getRootApplicationContextInitializers());
				servletContext.addListener(listener);
			}
* 
 * */
/*1��AbstractDispatcherServletInitializer�ӿڷ���createRootApplicationContext()�����������������������ļ�ͨ�� getRootConfigClasses()��ȡ
	AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
	Class<?>[] configClasses = getServletConfigClasses();//�÷���������ʵ��
	rootAppContext.register(configClasses);*/
/*
 * 2��AbstractDispatcherServletInitializer�ӿڷ���createServletApplicationContext()����web������web���������ļ�ͨ��getServletConfigClasses()
 *    AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
 *    Class<?>[] configClasses = getRootConfigClasses();//�÷���������ʵ��
 *    servletAppContext.register(configClasses);
 * 
*/
public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	//��ȡ�������������ࣻ��Spring�������ļ���   ��������
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[]{RootConfig.class};
	}

	//��ȡweb�����������ࣨSpringMVC�����ļ���  ��������
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// TODO Auto-generated method stub
		return new Class<?>[]{AppConfig.class};
	}

	//��ȡDispatcherServlet��ӳ����Ϣ
	//  /�������������󣨰�����̬��Դ��xx.js,xx.png���������ǲ�����*.jsp��
	//  /*����������������*.jspҳ�涼���أ�jspҳ����tomcat��jsp��������ģ�
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
