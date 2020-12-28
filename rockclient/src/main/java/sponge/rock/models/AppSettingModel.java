package sponge.rock.models;

import org.noear.snack.ONode;
import org.noear.water.utils.RunUtils;
import org.noear.weed.GetHandlerEx;
import org.noear.weed.IBinder;

import java.io.StringReader;
import java.util.Properties;

/// <summary>
/// 生成:2017/05/25 09:41:35
/// 
/// </summary>
public class AppSettingModel implements IBinder
{
	public int row_id;
	public int agroup_id;
	public int app_id;
	public int is_client;
    public String name;
    //设置项值类型：0,文本；1,数字; 9,JSON
    public int type;
    public String value;
    public int ver_start;

	public void bind(GetHandlerEx s)
	{
		//1.source:数据源
		//
		row_id = s.get("row_id").value(0);
		agroup_id = s.get("agroup_id").value(0);
		app_id = s.get("app_id").value(0);
		is_client = s.get("is_client").value(0);
        name = s.get("name").value(null);
		type = s.get("type").value(0);
        value = s.get("value").value(null);
        ver_start= s.get("ver_start").value(0);
	}
	
	public IBinder clone()
	{
		return new AppSettingModel();
	}


	//获取数字类型的值
	public long getLong(){
		if(type == 1) {
			return Long.parseLong(value);
		}else{
			return 0;
		}
	}

	//获取数字类型的值
	public int getInt(){
		if(type == 1) {
			return Integer.parseInt(value);
		}else{
			return 0;
		}
	}


	//获取JSON类型的值里的属性
	public ONode get(String name){
		if(_node == null){
			_node = getNode();
		}

		return _node.get(name);
	}
	private ONode _node;

	//转为JSON对象
	public ONode getNode(){
		if(type==1){
			if(value == null || value.length() == 0)
				return new ONode().val(0);
			else {
			    if(value.indexOf(".")>0) {

                    return new ONode().val(Double.parseDouble(value));
                }
			    else{
                    return new ONode().val(Integer.parseInt(value));
                }
            }
		}
		else if(type==9){
			return ONode.load(value);
		}else{
			return new ONode().val(value);
		}
	}

	/**
	 * 转为Properties
	 */
	private transient Properties _prop;
	public Properties getProp() {
		if (_prop == null) {
			_prop = new Properties();
			RunUtils.runActEx(() -> _prop.load(new StringReader(value)));
		}

		return _prop;
	}
}