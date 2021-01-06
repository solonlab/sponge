package org.noear.sponge.admin.utils;


import org.apache.http.util.TextUtils;
import org.noear.water.utils.EncryptUtils;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static<T> String join(String  mark,List<T> list){
        String res="";
        for (int i=0;i< list.size();i++){
            if(i==list.size()-1){
                res += list.get(i);
            }else {
                res += (list.get(i) + mark);
            }
        }
        return res;
    }

    //是否是数字
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

//替换手机号
    public static String mobile(String value,String change_value,int start,int end ){
        String startStr=value.substring(0,start);
        String endStr=value.substring(end,value.length());
        return startStr+change_value+endStr;
    }
    //取银行卡号后四位
    public static String card(String value,int start ){
        return value.substring(value.length()-start,value.length());
    }


    /**
     * Ascii转换为字符串
     * @param value
     * @return
     */
    public static String asciiToString(String value)
    {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");

        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            string.append((char) data);
        }

        return string.toString();
    }

    public static boolean isNumber(String str){
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }


    ///double格式化
    public static String doubleFormt(double input){
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.##");
        return df.format(input);
    }
    public static String doubleFormt2(double input){
        java.text.DecimalFormat   df=new   java.text.DecimalFormat("#.###");
        return df.format(input);
    }

    public static boolean idCardNoEquals(String input,String operator){

        String tmpa=subStringCardNo(input);
        String tmpb=subStringCardNo(operator);
        if(tmpa.equals(tmpb)){
            return true;
        }
        return false;
    }

    //截取身份证号码
    public static String subStringCardNo(String input){
        int length=input.length();
        if(length>8){
            String head=input.substring(0,4);
            String end=input.substring(length-4,length);
            return head+end;
        }else{
            return "";
        }
    }

    //校验身份证号码格式
    public static boolean isIDCard(String idcard) {
        String str ="^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
        Pattern pattern = Pattern.compile(str);
        return pattern.matcher(idcard).matches()?true:false;
    }

    //校验手机号格式
    public static boolean isMobile(String mobile) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (mobile.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobile);
            boolean isMatch = m.matches();
            if (!isMatch) {
                return false;
            }
            return isMatch;
        }

    }

    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String genUkey(int ugroupId,String mobile){
        return EncryptUtils.md5(ugroupId+"#"+mobile);
    }

    public static String hideName(String name) {
        if (!TextUtils.isEmpty(name)){
            String hideName = name;
            if (name.length()==2){
                hideName = name.substring(0,1)+"*";
            } else if (name.length()==3){
                hideName = name.substring(0,1)+"**";
            } else if (name.length()>3){
                hideName = name.substring(0,1)+"**";
            }
            return hideName;
        } else {
            return name;
        }

    }

    //获取魔蝎银行列表名
    public static String getMoxieBankName(int bank_id) {
        String bankName = "";
        switch (bank_id){
            case 1:bankName = "招商银行";break;
            case 2:bankName = "广发银行";break;
            case 3:bankName = "光大银行";break;
            case 4:bankName = "华夏银行";break;
            case 5:bankName = "建设银行";break;
            case 6:bankName = "民生银行";break;
            case 7:bankName = "农业银行";break;
            case 8:bankName = "浦发银行";break;
            case 9:bankName = "兴业银行";break;
            case 10:bankName = "中国银行";break;
            case 11:bankName = "中信银行";break;
            case 12:bankName = "工商银行";break;
            case 13:bankName = "交通银行";break;
            case 14:bankName = "邮储银行";break;
            case 15:bankName = "平安银行";break;
            case 16:bankName = "深发银行";break;
            case 17:bankName = "宁波银行";break;
            case 18:bankName = "北京银行";break;
            case 20:bankName = "河北银行";break;
            case 21:bankName = "大连银行";break;
            case 22:bankName = "徽商银行";break;
            case 23:bankName = "温州银行";break;
            case 24:bankName = "重庆农商";break;
            case 25:bankName = "重庆银行";break;
            case 26:bankName = "广州银行";break;
            case 27:bankName = "兰州银行";break;
            case 31:bankName = "鄞州银行";break;
            case 33:bankName = "上海银行";break;
            case 34:bankName = "宁夏银行";break;
            case 35:bankName = "长沙银行";break;
            case 36:bankName = "江苏农信";break;
            case 37:bankName = "福建农信";break;
            case 38:bankName = "成都农商";break;
            case 39:bankName = "富滇银行";break;
            case 40:bankName = "杭州银行";break;
            case 41:bankName = "江苏银行";break;
            case 42:bankName = "尧都农商";break;
            case 44:bankName = "包商银行";break;
            case 45:bankName = "台州银行";break;
            case 46:bankName = "华润银行";break;
            case 47:bankName = "吉林银行";break;
            case 48:bankName = "锦州银行";break;
            case 49:bankName = "上饶银行";break;
            case 50:bankName = "龙江银行";break;
            case 51:bankName = "青海银行";break;
            case 52:bankName = "汉口银行";break;
            case 53:bankName = "广州农商";break;
            case 57:bankName = "浙江农信";break;
            case 88:bankName = "浙商银行";break;
        }
        return bankName;
    }

    public static String getHideEmail(String email) {
        if (!TextUtils.isEmpty(email)){
            return email.substring(0,1)+"***"+email.substring(email.indexOf("@")-1,email.length());
        } else {
            return email;
        }
    }

    public static String getHideIdCode(String idCode) {
        if (!TextUtils.isEmpty(idCode)){
            return idCode.substring(0,3)+"***********"+idCode.substring(idCode.length()-3,idCode.length());
        } else {
            return idCode;
        }
    }

    public static String getHideBankCardCode(String cardCode) {
        if (!TextUtils.isEmpty(cardCode)){
            return cardCode.substring(0,6)+"**********"+cardCode.substring(cardCode.length()-4,cardCode.length());
        } else {
            return cardCode;
        }
    }

}
