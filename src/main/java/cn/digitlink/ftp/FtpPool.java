package cn.digitlink.ftp;

import cn.hutool.extra.ftp.AbstractFtp;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class FtpPool extends GenericObjectPool<AbstractFtp> {

    public FtpPool(FtpFactory ftpPooledObjectFactory) {
        super(ftpPooledObjectFactory);
    }

    public FtpPool(FtpFactory ftpPooledObjectFactory, GenericObjectPoolConfig<AbstractFtp> genericObjectPoolConfig) {
        super(ftpPooledObjectFactory, genericObjectPoolConfig);
    }

}
