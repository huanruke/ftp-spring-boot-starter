package cn.digitlink.ftp;

import cn.hutool.extra.ftp.AbstractFtp;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import cn.hutool.extra.ssh.JschUtil;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.util.StringUtils;

import java.io.InvalidObjectException;
import java.nio.charset.Charset;

@Slf4j
public class FtpFactory implements PooledObjectFactory<AbstractFtp> {

    private final FtpProperties ftpProperties;

    public FtpFactory(FtpProperties ftpProperties) {
        this.ftpProperties = ftpProperties;
    }

    @Override
    public PooledObject<AbstractFtp> makeObject() {
        String type = ftpProperties.getType();
        String host = ftpProperties.getHost();
        Integer port = ftpProperties.getPort();
        String username = ftpProperties.getUsername();
        String password = ftpProperties.getPassword();
        String encoding = ftpProperties.getEncoding();
        Boolean passiveMode = ftpProperties.getPassiveMode();

        FtpMode model = passiveMode ? FtpMode.Passive : FtpMode.Active;
        Charset charset = null;
        if (StringUtils.hasText(encoding)) {
            charset = Charset.forName(encoding);
        }

        AbstractFtp ftp = null;
        try {
            switch (type) {
                case "ftp":
                    ftp = new Ftp(host, port, username, password, charset, null, null, model);
                    break;
                case "sftp":
                    Session session = JschUtil.createSession(host, port, username, password);
                    ftp = JschUtil.createSftp(session);
                    break;
                default:
                    throw new IllegalArgumentException("Illegal type of ftp");
            }
            log.info("ftp connected {}:{}", host, port);
        } catch (Exception e) {
            log.error("ftp connect failed {}:{} ", host, port, e);
        }

        return new DefaultPooledObject<>(ftp);
    }

    @Override
    public void destroyObject(PooledObject<AbstractFtp> pooledObject) {
        try {
            AbstractFtp ftp = pooledObject.getObject();
            if (ftp != null) {
                ftp.close();
            }
            log.info("ftp conn destroyed");
        } catch (Exception e) {
            log.error("ftp conn destroy failed ", e);
        }
    }

    @Override
    public boolean validateObject(PooledObject<AbstractFtp> pooledObject) {
        try {
            AbstractFtp ftp = pooledObject.getObject();
            if (ftp == null) {
                throw new InvalidObjectException("pooled object is null");
            }

            ftp.pwd();
            log.info("ftp conn validated");
        } catch (Exception e) {
            log.error("ftp conn validate failed ", e);
            return false;
        }

        return true;
    }

    @Override
    public void activateObject(PooledObject<AbstractFtp> pooledObject) {
        String basePath = ftpProperties.getBasePath();
        AbstractFtp ftp = pooledObject.getObject();
        ftp.cd(basePath);
    }

    @Override
    public void passivateObject(PooledObject<AbstractFtp> pooledObject) {
        // do something before return
    }

}
