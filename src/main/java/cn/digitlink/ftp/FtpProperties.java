package cn.digitlink.ftp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ftp")
public class FtpProperties {

    /** 类型ftp|sftp 默认 ftp */
    @Value("${ftp.type:ftp}")
    private String type;

    /** 地址 */
    @Value("${ftp.host}")
    private String host;

    /** 端口 */
    @Value("${ftp.port}")
    private Integer port;

    /** 用户名 */
    @Value("${ftp.username}")
    private String username;

    /** 密码 */
    @Value("${ftp.password}")
    private String password;

    /** 密钥 默认:空 */
    @Value("${ftp.private-key:}")
    private String privateKey;

    /** 编码 默认:UTF-8 */
    @Value("${ftp.encoding:UTF-8}")
    private String encoding;

    /** 是否被动模式 默认:true */
    @Value("${ftp.passive-mode:true}")
    private Boolean passiveMode;

    /** 文件存储路径 默认:/ */
    @Value("${ftp.base-path:/}")
    private String basePath;

    /** 最小空闲数 默认:0 */
    @Value("${ftp.pool.min-idle:0}")
    private Integer minIdle;

    /** 最大空闲数 默认:4 */
    @Value("${ftp.pool.max-idle:4}")
    private Integer maxIdle;

    /** 最大连接数 默认:8 */
    @Value("${ftp.pool.max-total:8}")
    private Integer maxTotal;

    /** 最大连接建立等待时间 默认:10000 */
    @Value("${ftp.pool.max-wait:10000}")
    private Integer maxWaitMillis;

    /** 是否进行空闲检测 默认:true */
    @Value("${ftp.pool.test-while-idle:true}")
    private Boolean testWhileIdle;

    /** 空闲连接回收线程执行周期 默认:60000 */
    @Value("${ftp.pool.time-between-eviction-runs-millis:60000}")
    private Long timeBetweenEvictionRunsMillis;

    /** 可被回收的最小空闲时间 默认:600000 */
    @Value("${ftp.pool.min-evictable-idle-time-millis:600000}")
    private Long minEvictableIdleTimeMillis;

    /** 获取时检测 默认:true */
    @Value("${ftp.pool.test-on-borrow:true}")
    private Boolean testOnBorrow;

    /** 归还时检测 默认:false */
    @Value("${ftp.pool.test-on-return:false}")
    private Boolean testOnReturn;

}
