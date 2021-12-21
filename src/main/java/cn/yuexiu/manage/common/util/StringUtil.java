package cn.yuexiu.manage.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String工具类
 *
 * @author Administrator
 */
@SuppressWarnings("rawtypes")
@Slf4j
public class StringUtil {

    //private static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    private static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    private static final String MOBILE_REGEX = "^[1][0-9]{10}$";

    private static final String IPv6_REGEX = "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$";

    private static final String IPv4_REGEX = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";

    private static final String IMG_REGEX = ".+\\.(jpg|jpeg|png|gif|bmp)$";

    /**
     * 将其他类型转成字符串
     *
     * @param obj - 其他类型对象
     * @return - 字符串
     */
    public static String toString(Object obj) {

        if (null != obj) {
            return obj.toString();
        }
        return null;
    }

    /**
     * 将属于字符串类型的对象转成Integer对象
     *
     * @param o - 字符串类型的对象
     * @return - Integer对象
     */
    public static Integer strToInteger(Object o) {

        if (null == o || o.equals("")) {
            return null;
        }
        if (o instanceof Integer) {
            return (Integer) o;
        }
        return toInteger(o.toString());
    }

    /**
     * 将字符串转成Integer对象
     *
     * @param o - 字符串
     * @return - Integer对象
     */
    public static Integer toInteger(String o) {

        if (null == o || "".equals(o.trim())) {
            return null;
        }
        Integer t = Integer.valueOf(o);
        return t;
    }

    /**
     * 将属于字符串类型的对象转成Long对象
     *
     * @param o - 字符串类型的对象
     * @return - Long对象
     */
    public static Long strToLong(Object o) {

        if (null == o || o.equals("")) {
            return null;
        }
        if (o instanceof Long) {
            return (Long) o;
        }
        if (o instanceof Integer) {
            Integer i = (Integer) o;
            return Long.valueOf(i.longValue());
        }
        if (o instanceof Double) {
            Double d = (Double) o;
            return d.longValue();
        }
        if (o instanceof Float) {
            Float f = (Float) o;
            return f.longValue();
        }
        return toLong(o.toString());
    }

    /**
     * 将字符串转成Long对象
     *
     * @param o - 字符串
     * @return - Long对象
     */
    public static Long toLong(String o) {

        if (null == o || "".equals(o.trim())) {
            return null;
        }
        Long t = Long.valueOf(o);
        return t;
    }

    /**
     * 半角转全角
     * <p>
     * 半角：Single Byte Character case,全角：Double Byte Character case
     * <p>
     * 全角空格为12288，半角空格为32
     * <p>
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input - 半角字符串
     * @return - 全角字符串
     */
    public static String toDoubleCase(String input) {

        if (null != input) {
            char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == 32) {
                    chars[i] = (char) 12288;
                } else if (c >= 33 && c <= 126) {
                    chars[i] = (char) ((int) c + 65248);
                }
            }
            return new String(chars);
        }
        return null;
    }

    /**
     * 全角转半角
     * <p>
     * 全角空格为12288，半角空格为32
     * <p>
     * 其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
     *
     * @param input - 全角字符串
     * @return - 半角字符串
     */
    public static String toSingleCase(String input) {

        if (null != input) {
            char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == 12288) {
                    chars[i] = (char) 32;
                } else if (c >= 65281 && c <= 65374) {
                    chars[i] = (char) ((int) c - 65248);
                }
            }
            return new String(chars);
        }
        return null;
    }

    /**
     * 编码转换,将源字符串转换成unicode字符串,和native2ascii 功能相同
     *
     * @param source - 源字符串,可以是任意字符,包括汉字
     * @return unicode - 转换之后的unicode字符串
     */
    public static String native2ascii(String source) {

        if (null == source) {
            return null;
        }
        StringBuilder unicode = new StringBuilder(source.length() * 5);
        for (char ch : source.toCharArray()) {
            unicode.append("\\u").append(Integer.toHexString(ch));

        }
        return unicode.toString();
    }

    /**
     * 判断字符串是否为空，null 或者 "" 或者 " "（只有空格）都算是空
     *
     * @param s - 字符串
     * @return - 为空返回true,否则返回false
     */
    public static boolean isEmpty(String s) {

        if (null == s || "".equals(s.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断值和对象是否为空
     *
     * @param obj
     * @return
     */

    public static boolean isEmpty(Collection obj) {

        if (obj == null) {
            return true;
        }

        return obj.isEmpty();
    }

    public static boolean isEmpty(List obj) {

        if (obj == null) {
            return true;
        }

        return obj.isEmpty();
    }

    public static boolean isEmpty(Map m) {

        if (null == m) {
            return true;
        }
        return m.isEmpty();
    }

    public static boolean isEmpty(Object o) {

        if (null == o) {
            return true;
        }
        if (o.getClass().isArray()) {
            return Array.getLength(o) == 0;
        }
        if (o instanceof String) {
            return isEmpty((String) o);
        }
        if (o instanceof List) {
            return isEmpty((List) o);
        }
        if (o instanceof Collection) {
            return isEmpty((Collection) o);
        }
        return false;
    }

    /**
     * 判断是否是纯数字([0,9])字符串
     *
     * @param o - 待判断字符串
     * @return - true, 数字字符串
     */
    public static boolean isDigitStr(Object o) {

        if (isEmpty(o)) {
            return false;
        }
        String str = o.toString();
        for (int i = str.length(); --i >= 0; ) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否是邮箱
     *
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean isEmail(String str) {

        if (isEmpty(str)) {
            return false;
        }
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = regex.matcher(str);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是手机号
     *
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean isMobile(String str) {

        if (isEmpty(str)) {
            return false;
        }
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(MOBILE_REGEX);
            Matcher matcher = regex.matcher(str);
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是图片名
     *
     * @param str
     * @return
     */
    @SuppressWarnings("unused")
    public static boolean isImg(String str) {

        if (isEmpty(str)) {
            return false;
        }
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile(IMG_REGEX);
            Matcher matcher = regex.matcher(str.toLowerCase());
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断字符串是否IPV4地址
     *
     * @param str
     * @return
     */
    public static boolean isIp(String str) {

        if (isEmpty(str)) {
            return false;
        }

        Pattern p1 = Pattern.compile(IPv4_REGEX);
        Matcher m1 = p1.matcher(str);
        if (m1.matches()) {
            return true;
        }

        Pattern p2 = Pattern.compile(IPv6_REGEX);
        Matcher m2 = p2.matcher(str);
        return m2.matches();
    }

    /**
     * 获取6位数字随机码
     *
     * @return - 6位数字随机码
     */
    public static String randomN6() {

        Random r = new Random();
        int x = r.nextInt(1000000);
        String rv = String.format("%06d", Integer.valueOf(x));
        return rv;
    }

    /**
     * 将邮箱部分用**加密
     *
     * @param email
     * @return
     */
    public static String decriptEmail(String email) {
        if (!isEmail(email)) {
            return null;
        }
        String[] emailCodes = email.split("@");
        email = emailCodes[0];
        StringBuffer decriptEmail = new StringBuffer("");
        char hideCode = '*';
        int emailLength = email.length();
        for (int i = 0; i < emailLength; i++) {
            if (i < 2 || i > emailLength - 3) {
                decriptEmail.append(email.charAt(i));
            } else {
                decriptEmail.append(hideCode);
            }
        }
        return decriptEmail.toString() + "@" + emailCodes[1];
    }

    /**
     * 将手机部分用**加密
     *
     * @param mobile
     * @return
     */
    public static String decriptMobile(String mobile) {
        if (!isMobile(mobile)) {
            return null;
        }
        StringBuffer decriptMobile = new StringBuffer("");
        char hideCode = '*';
        int mobileLength = mobile.length();
        for (int i = 0; i < mobileLength; i++) {
            if (i < 3 || i > mobileLength - 4) {
                decriptMobile.append(mobile.charAt(i));
            } else {
                decriptMobile.append(hideCode);
            }
        }
        return decriptMobile.toString();
    }

    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s = encode;
                return s;
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";
    }

    public static void filterLabel() {


    }


    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    /**
     * @param str
     * @return String
     * @throws
     * @Description: 过滤所有以<开头 ，>结尾的标签
     * @author rongjianping
     * 2015年4月29日上午10:49:48
     */
    public static String filterHtml(String str) {
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static Double toDouble(Object o) {

        if (null == o || o.equals("")) {
            return null;
        }
        if (o instanceof Double) {
            return (Double) o;
        }
        if (o instanceof Integer) {
            Integer i = (Integer) o;
            return i.doubleValue();
        }
        if (o instanceof Float) {
            Float f = (Float) o;
            return f.doubleValue();
        }
        if (o instanceof String) {
            String s = (String) o;
            return Double.parseDouble(s);
        }
        return null;
    }

    public static String filterEmoji(String source) {
        if (!isEmpty(source)) {
            //return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
            return source.replaceAll("[^\\u0000-\\uFFFF]", "");

        } else {
            return source;
        }


    }

    public static String contains(String str, String num) {
        if(StringUtil.isEmpty(str)){
            return num;
        }

        if (!isEmpty(num)) {
            String[] nums = str.split(",");
            Boolean flag = ArrayUtils.contains(nums, num);
            if (!flag) {
                return str + "," + num;
            }
        }
        return str;
    }

    private static int compare(String str, String target) {
        int d[][]; // 矩阵
        int n = str.length();
        int m = target.length();
        int i; // 遍历str的
        int j; // 遍历target的
        char ch1; // str的
        char ch2; // target的
        int temp; // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];
        for (i = 0; i <= n; i++) { // 初始化第一列
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) { // 初始化第一行
            d[0][j] = j;
        }

        for (i = 1; i <= n; i++) { // 遍历str
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }

                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + temp);
            }
        }
        return d[n][m];
    }


    private static int min(int one, int two, int three) {
        return (one = one < two ? one : two) < three ? one : three;
    }


    /**
     * 获取两字符串的相似度
     *
     * @param str
     * @param target
     * @return
     */

    public static float getSimilarityRatio(String str, String target) {
        return 1 - (float) compare(str, target) / Math.max(str.length(), target.length());
    }


    /**
     * 去掉占4个字节的字符
     *
     * @param content
     * @return
     */
    public static String removeFourChar(String content) {
        if (null == content) {
            return null;
        }
        byte[] conbyte = content.getBytes();
        boolean changed = false;
        for (int i = 0; i < conbyte.length; i++) {
            if ((conbyte[i] & 0xF8) == 0xF0) {
                for (int j = 0; j < 4; j++) {
                    conbyte[i + j] = 0x30;
                }
                i += 3;
                changed = true;
            }
        }
        if (changed) {
            content = new String(conbyte);
            return content.replaceAll("0000", "");
        }
        return content;

    }

    /**
     * 扫描字符, 进行全半角转换, 中文字符跳过
     *
     * @param resource 源字符串
     * @param type     :1全角转半角, 2半角转全角
     * @return
     * @author fanjin
     * @date 2017年10月30日
     */
    public static String convert_ABC_DBC(String resource, String type) {

        char[] chars = resource.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            String temp = String.valueOf(chars[i]);
            //判断是中文字符
            if (temp.matches("[\u4e00-\u9fa5]")) {//判断是否是中文字符
                continue;
            }
            //判断是全角字符
            if ("1".equals(type) && temp.matches("[^\\x00-\\xff]")) {
                char c = temp.toCharArray()[0];
                if (c == '\u3000') {
                    c = ' ';
                } else if (c > '\uFF00' && c < '\uFF5F') {
                    c = (char) (c - 65248);
                }
                chars[i] = c;
            }
            //判断是半角字符
            if ("2".equals(type) && !temp.matches("[^\\x00-\\xff]")) {
                char c = temp.toCharArray()[0];
                if (c == ' ') {
                    c = '\u3000';
                } else if (c < '\177') {
                    c = (char) (c + 65248);
                }
                chars[i] = c;
            }
        }
        return new String(chars);
    }

    /**
     * 防止非法实例化
     */
    private StringUtil() {
    }

    /**
     * 将obj类型转化为string类型
     *
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        if (null == obj) {
            return "";
        }
        return obj.toString();
    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

    public static String getCaseNum(String type) {
        switch (type) {
            case "face":
                return "25";
            case "person":
                return "26";
            case "bicycle":
                return "29";
            case "tricycle":
                return "30";
            case "car":
                return "28";
			default:
				return "27";
        }
    }

	public static String getType(String type) {
		switch (type) {
			case "face":
				return "42";
			case "person":
				return "43";
			case "bicycle":
				return "23";
			case "tricycle":
				return "24";
			case "car":
				return "22";

			default:
				return "21";
		}
	}

    public static String guiji(String type) {
        switch (type) {
            case "face":
                return "81";
            case "person":
                return "82";
            case "bicycle":
                return "83";
            case "tricycle":
                return "84";
            case "car":
                return "85";

            default:
                return "86";
        }
    }


    public static String getSuspicionNum(String type) {
		switch (type) {
			case "face":
				return "31";
			case "person":
				return "32";
			case "bicycle":
				return "35";
			case "tricycle":
				return "36";
			case "car":
				return "34";
			default:
				return "33";
		}
	}

    public static Boolean existGait(String types) {
        if(StringUtil.isEmpty(types)){
            return false;
        }
        return  types.contains("gait");
    }

    public static String getTargetType(String types) {
        log.info("types = "+types);
        if(existGait(types)){
           List<String> typeList = Arrays.asList(types.split(","));
           typeList = new ArrayList(typeList);
            log.info("typeList = "+typeList);

            for (int i = typeList.size() - 1; i >= 0; i--) {
               if("gait".equals(typeList.get(i))){
                   log.info("typeList = "+typeList);
                   typeList.remove(i);
               }
           }
           if(typeList.isEmpty()){
               return null;
           }
           return StringUtils.join(typeList,",");
        }else{
            return types;
        }
    }

}
