package util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 * Description:
 * User:S-
 * Date:2020/1/4-10:51
 * Version: 1.0
 **/

public class Pingyin4j {

    /**
     * 中文字符格式
     */
    private static final String CHINESE_PATTERN = "[\\u4E00-\\u9FA5]";


    public static final HanyuPinyinOutputFormat FORMAT = new HanyuPinyinOutputFormat();

    static {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //设置拼音小写
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        //设置不带音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //设置带v字符
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    /**
     * 判断是否包含中文
     *
     * @
     */
    public static boolean containsChinese(String str) {
        return str.matches(".*" + CHINESE_PATTERN + ".*");//正则表达式表示包含中文
    }

//    public static String[][] test() throws BadHanyuPinyinOutputFormatCombination {
//        String[] pinyins = PinyinHelper.toHanyuPinyinStringArray('和', FORMAT);
//        System.out.println(Arrays.toString(pinyins));
//    }

    public static String[] get(String hanyu) {
        String[] arr = new String[2];
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < hanyu.length(); i++) {
            try {
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(hanyu.charAt(i), FORMAT);
                //中文字符返回的字符串数组，可能为null或者长度为0，那么就返回原始的字符
                if (pinyins == null || pinyins.length == 0) {
                    sb.append(hanyu.charAt(i));
                    sb1.append(hanyu.charAt(i));
                } else {//可以转换为拼音，只取第一个字符串作为拼音
                    sb.append(pinyins[0]);
                    sb1.append(pinyins[0].charAt(0));
                }
            } catch (Exception e) {//出现异常，返回原始的字符
                sb.append(hanyu.charAt(i));
                sb1.append(hanyu.charAt(i));
            }
        }
        arr[0] = sb.toString();
        arr[1] = sb1.toString();
        return arr;
    }

    public static String[][] get(String hanyu, boolean fullSpwll) {
        String[][] array = new String[hanyu.length()][];
        for (int i = 0; i < hanyu.length(); i++) {
            try {
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(hanyu.charAt(i), FORMAT);
                if (pinyins == null || pinyins.length == 0) {
                    array[i] = new String[]{String.valueOf(hanyu.charAt(i))};
                } else {
                    array[i] = unique(pinyins, fullSpwll);
                }
            } catch (Exception e) {
                array[i] = new String[]{String.valueOf(hanyu.charAt(i))};
            }
        }
        return array;
    }

    public static String[] unique(String[] pinyins, boolean fullSpell) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < pinyins.length; i++) {
            if (fullSpell) {
                set.add(pinyins[i]);
            } else {
                set.add(String.valueOf(pinyins[i].charAt(0)));
            }
        }
        return set.toArray(new String[set.size()]);
    }

    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {
        String[] arr = get("中华人民共和国");
        System.out.println(Arrays.toString(arr));
    }
}
