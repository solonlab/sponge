package org.noear.rock.model;

import org.noear.snack4.ONode;
import org.noear.solon.Utils;

import java.io.Serializable;
import java.util.Properties;

/**
 * 应用设置
 * */
public class AppSettingModel implements Serializable {
    /**
     * 应用组ID
     */
    public int agroup_id;
    /**
     * 应用ID
     */
    public int app_id;
    /**
     * 是否输出到客户端
     */
    public int is_client;
    /**
     * 设置名称
     */
    public String name;
    /**
     * 设置项值类型：0,文本；1,数字; 9,JSON
     */
    public int type;
    /**
     * 值
     */
    public String value;
    /**
     * 有效起始版本
     */
    public int ver_start;


    //获取数字类型的值
    public long getLong() {
        if (type == 1) {
            return Long.parseLong(value);
        } else {
            return 0;
        }
    }

    //获取数字类型的值
    public int getInt() {
        if (type == 1) {
            return Integer.parseInt(value);
        } else {
            return 0;
        }
    }


    //获取JSON类型的值里的属性
    public ONode get(String name) {
        if (_node_x == null) {
            _node_x = getNode();
        }

        return _node_x.get(name);
    }

    private transient ONode _node_x;

    //转为JSON对象
    public ONode getNode() {
        if (type == 1) {
            if (value == null || value.length() == 0) {
                return new ONode(0);
            } else {
                if (value.indexOf(".") > 0) {

                    return new ONode(Double.parseDouble(value));
                } else {
                    return new ONode(Integer.parseInt(value));
                }
            }
        } else if (type == 9) {
            return ONode.ofJson(value);
        } else {
            return new ONode(value);
        }
    }

    /**
     * 转为 Properties
     */
    private transient Properties _prop;

    public Properties getProp() {
        if (_prop == null) {
            if (Utils.isEmpty(value)) {
                _prop = new Properties();
            } else {
                _prop = Utils.buildProperties(value);
            }
        }

        return _prop;
    }

    /**
     * 转为 Bean
     *
     */
    public <T> T getBean(Class<T> tClass) {
        Properties data = getProp();
        return ONode.ofBean(data).toBean(tClass);
    }
}