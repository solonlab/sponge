package org.noear.rock.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.noear.snack.ONode;
import org.noear.water.utils.CallUtils;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.io.Serializable;
import java.io.StringReader;
import java.util.Properties;

/**
 * 应用设置
 * */
public class AppSettingModel implements Serializable {
	/**
	 * 应用组ID
	 * */
	public int agroup_id;
	/**
	 * 应用ID
	 * */
	public int app_id;
	/**
	 * 是否输出到客户端
	 * */
	public int is_client;
	/**
	 * 设置名称
	 * */
	public String name;
	/**
	 * 设置项值类型：0,文本；1,数字; 9,JSON
	 * */
	public int type;
	/**
	 * 值
	 * */
	public String value;
	/**
	 * 有效起始版本
	 * */
	public int ver_start;


	//获取数字类型的值
	@JSONField(serialize = false)
	public long getLong() {
		if (type == 1) {
			return Long.parseLong(value);
		} else {
			return 0;
		}
	}

	//获取数字类型的值
	@JSONField(serialize = false)
	public int getInt() {
		if (type == 1) {
			return Integer.parseInt(value);
		} else {
			return 0;
		}
	}


	//获取JSON类型的值里的属性
	@JSONField(serialize = false)
	public ONode get(String name) {
		if (_node_x == null) {
			_node_x = getNode();
		}

		return _node_x.get(name);
	}

	private transient ONode _node_x;

	//转为JSON对象
	@JSONField(serialize = false)
	public ONode getNode() {
		if (type == 1) {
			if (value == null || value.length() == 0) {
				return new ONode().val(0);
			} else {
				if (value.indexOf(".") > 0) {

					return new ONode().val(Double.parseDouble(value));
				} else {
					return new ONode().val(Integer.parseInt(value));
				}
			}
		} else if (type == 9) {
			return ONode.load(value);
		} else {
			return new ONode().val(value);
		}
	}

	/**
	 * 转为Properties
	 */
	private transient Properties _prop;

	@JSONField(serialize = false)
	public Properties getProp() {
		if (_prop == null) {
			_prop = new Properties();
			CallUtils.run(() -> _prop.load(new StringReader(value)));
		}

		return _prop;
	}
}