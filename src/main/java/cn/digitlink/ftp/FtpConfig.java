package cn.digitlink.ftp;

import cn.hutool.extra.ftp.AbstractFtp;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
@EnableConfigurationProperties(FtpProperties.class)
@ConditionalOnProperty(prefix = "ftp", name = "type")
public class FtpConfig {

    @PostConstruct
    public void initDir() {
        File file3 = new File(FtpUtils.FILE_TEMP_PATH);
        if (!file3.exists()) {
            file3.mkdirs();
        }
    }

    @Bean
    public FtpFactory ftpPooledObjectFactory(FtpProperties ftpProperties) {
        return new FtpFactory(ftpProperties);
    }

    @Bean
    public FtpPool ftpPool(FtpProperties ftpProperties, FtpFactory ftpFactory) {
        GenericObjectPoolConfig<AbstractFtp> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMinIdle(ftpProperties.getMinIdle());
        poolConfig.setMaxIdle(ftpProperties.getMaxIdle());
        poolConfig.setMaxTotal(ftpProperties.getMaxTotal());
        poolConfig.setTestWhileIdle(ftpProperties.getTestWhileIdle());
        poolConfig.setTimeBetweenEvictionRunsMillis(ftpProperties.getTimeBetweenEvictionRunsMillis());
        poolConfig.setMinEvictableIdleTimeMillis(ftpProperties.getMinEvictableIdleTimeMillis());
        poolConfig.setTestOnBorrow(ftpProperties.getTestOnBorrow());
        poolConfig.setTestOnReturn(ftpProperties.getTestOnReturn());
        poolConfig.setJmxEnabled(false);
        return new FtpPool(ftpFactory, poolConfig);
    }

    @Bean
    public FtpTemplate ftpTemplate(FtpPool ftpPool) {
        return new FtpTemplate(ftpPool);
    }

}
