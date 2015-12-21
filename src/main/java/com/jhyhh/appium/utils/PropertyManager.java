package com.jhyhh.appium.utils;

 
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.jhyhh.appium.exception.PropertyException;
import com.jhyhh.appium.logger.LogWriter;

/**
 * 对 Property 进行处理的处理。
 * @author albert
 */
public class PropertyManager {

    /**
     * 属性文件的名称
     */
    private final String propertyFileName = "config.properties";
    private final Logger logger = Logger.getLogger(PropertyManager.class);
    private static PropertyManager instance;
    private final Properties properites = new Properties();

    private PropertyManager() {
    }

    synchronized static public PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
            instance.load();
        }
        return instance;
    }

    /**
     * 读取文件的位置。
     * @return
     */
    private File getFile() {
        // 从工作目录加载此文件 
        File file = new File( propertyFileName);
         
        if (!file.exists()) {
            try {
                file.createNewFile();
                LogWriter.info2(logger, "创建新的属性文件[%s]。", file.getAbsolutePath());
            } catch (IOException ex) {
                LogWriter.warn2(logger, ex, "创建属性文件[%s]失败。", file.getAbsolutePath());
            }
        }
        return file;
    }

    private void load() {
        File file = getFile();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            properites.load(fis);
        } catch (Exception ex) {
            LogWriter.warn2(logger, ex, "属性文件[%s]不存在。", file.getAbsolutePath());
        } finally {
            IOHelper.closeSilently(fis);
        }
        LogWriter.debug2(logger, "属性文件[%s]装载完毕。", file.getAbsolutePath());
    }
    
    synchronized public void reload() {
        properites.clear();
        load();
    } 
    
    /**
     * 返回所有属性名称。
     * @return
     */
    public Set<String> getNames() {
        return properites.stringPropertyNames();
    }

    /**
     * 读取参数名称。
     * @param name
     * @return
     */
    public String getString(String name) {
        assertName(name);
        return getString(name, null);
    }

    public String getString(String name, String defaultValue) {
        LogWriter.debug2(logger, "读取属性[%s]。", name);
        assertName(name);
        String value = properites.getProperty(name);
        if (StringUtils.isEmpty(value)) {
            value = defaultValue;
        }
        return value;
    }

    public int getInt(String name, int defaultValue) {
        int value = defaultValue;
        String strValue = getString(name, String.valueOf(defaultValue));
        if (StringUtils.isNotEmpty(strValue)) {
            try {
                value = Integer.parseInt(strValue);
            } catch (NumberFormatException nfe) {
                throw new PropertyException(String.format("属性[%s]的值[%s]不能转换为整数。", name, strValue));
            }
        }
        return value;
    }
    
    public long getLong(String name, long defaultValue) {
        long value = defaultValue;
        String strValue = getString(name, String.valueOf(defaultValue));
        if (StringUtils.isNotEmpty(strValue)) {
            try {
                value = Long.parseLong(strValue);
            } catch (NumberFormatException nfe) {
                throw new PropertyException(String.format("属性[%s]的值[%s]不能转换为整数。", name, strValue));
            }
        }
        return value;
    }
    
    public double getDouble(String name, double defaultValue) {
        double value = defaultValue;
        String strValue = getString(name, String.valueOf(defaultValue));
        if (StringUtils.isNotEmpty(strValue)) {
            try {
                value = Double.parseDouble(strValue);
            } catch (NumberFormatException nfe) {
                throw new PropertyException(String.format("属性[%s]的值[%s]不能转换为双精度。", name, strValue));
            }
        }
        return value;
    }

    public boolean getBoolean(String name, boolean defaultValue) {
        boolean value = defaultValue;
        String strValue = getString(name, String.valueOf(defaultValue));
        if (StringUtils.isNotEmpty(strValue)) {
            value = BooleanUtils.isTrue(strValue, defaultValue);
        }
        return value;
    }
    
    private void assertName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new PropertyException("属性名称不能为空。");
        }
    }
}
