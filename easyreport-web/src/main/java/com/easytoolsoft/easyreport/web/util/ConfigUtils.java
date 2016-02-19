package com.easytoolsoft.easyreport.web.util;

import com.easytoolsoft.easyreport.common.util.PropertiesUtils;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

/**
 * Created by hongliangpan@gmail.com on 2016/2/19.
 */
public class ConfigUtils {
    private static final Logger logger = LoggerFactory.getLogger(ConfigUtils.class);
    public static Properties properties = new Properties();

    static {
        init();
    }

    public static void init() {
        try {
            URL url = Resources.getResource("resource.properties");
            properties.load(new FileInputStream(new File(url.toURI())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    public static void setValue(String key, String value) {
        properties.setProperty(key, value);
    }


}
