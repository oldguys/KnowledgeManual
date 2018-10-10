package com.hrh.kmanual.configs;/**
 * Created by Administrator on 2018/9/19 0019.
 */

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.hrh.kmanual.commons.utils.Log4jUtils;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/19 0019 23:12
 */
@Configuration
public class DruidConfiguration {

    public static final String SEPARATOR = ";";

    @Value("${km.configs.druid-console-username}")
    private String username;

    @Value("${km.configs.druid-console-password}")
    private String password;

    @Value("${km.configs.druid-console-springs}")
    private String springLogPatterns;

    @Value("${km.configs.druid-console-slowSql}")
    private Long slowSqlMillis;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource DruidDataSource(Log4jFilter log4jFilter, StatFilter statFilter, WebStatFilter webStatFilter) throws SQLException {
        DruidDataSource bean = new DruidDataSource();

        List<Filter> filters = new ArrayList<>();
        filters.add(log4jFilter);
        filters.add(statFilter);

        // 配合添加防火墙
        bean.setFilters("wall");
        bean.setProxyFilters(filters);
        return bean;
    }

    /**
     * 配置 Druid Monitor ==========================================
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {

        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> parameters = new HashMap<>(5);
        parameters.put(StatViewServlet.PARAM_NAME_USERNAME, username);
        parameters.put(StatViewServlet.PARAM_NAME_PASSWORD, password);
        parameters.put(StatViewServlet.PARAM_NAME_RESET_ENABLE, "false");
        bean.setInitParameters(parameters);
        return bean;
    }

    /**
     * SQL 监控 ==================================
     *
     * @return
     */
    @Bean
    public StatFilter statFilter() {
        StatFilter bean = new StatFilter();
        bean.setLogSlowSql(true);
        Log4jUtils.getInstance(getClass()).info("慢SQL标准(单位:ms):" + slowSqlMillis);
        bean.setSlowSqlMillis(slowSqlMillis);
        bean.setMergeSql(true);
        return bean;
    }

    /**
     * URI 监控 =====================================
     *
     * @param webStatFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean FilterRegistrationBean(WebStatFilter webStatFilter) {
        FilterRegistrationBean bean = new FilterRegistrationBean(webStatFilter);
        bean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return bean;
    }

    @Bean
    public WebStatFilter WebStatFilter() {
        WebStatFilter bean = new WebStatFilter();
        bean.setSessionStatEnable(true);
        return bean;
    }

    /**
     * 配置Spring拦截器 ====================================
     *
     * @return
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        DruidStatInterceptor bean = new DruidStatInterceptor();
        return bean;
    }

    @Bean
    @Scope("prototype")
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();

        if (StringUtils.isNotBlank(springLogPatterns)) {
            String[] patterns = springLogPatterns.split(SEPARATOR);
            pointcut.setPatterns(patterns);
        }

        return pointcut;
    }

    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }

    /**
     * 日志记录 ==============================================
     * <p>
     * statement-create-after-log-enabled: false
     * statement-close-after-log-enabled: false
     * result-set-open-after-log-enabled: false
     * result-set-close-after-log-enabled: false
     *
     * @return
     */
    @Bean
    public Log4jFilter log4jFilter() {
        Log4jFilter bean = new Log4jFilter();
        bean.setDataSourceLogEnabled(true);
        bean.setStatementExecutableSqlLogEnable(true);
//        bean.setConnectionLoggerName("druid");
        bean.setDataSourceLoggerName("druid");
        return bean;
    }


}
