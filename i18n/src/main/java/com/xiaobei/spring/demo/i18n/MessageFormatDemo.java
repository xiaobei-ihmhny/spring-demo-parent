package com.xiaobei.spring.demo.i18n;

import org.junit.Test;

import java.text.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020-08-17 14:51:51
 */
@SuppressWarnings("DuplicatedCode")
public class MessageFormatDemo {

    /**
     * {@link MessageFormat} 简单使用
     * <h2>运行结果：</h2>
     * <p>
     * At 15:01:10 on 2020-8-17, 出现了意外的bug on node 7.
     */
    @Test
    public void messageFormatSimple() {
        int node = 7;
        String event = "意外的bug";
        String result = MessageFormat
                .format("At {1, time} on {1, date}, 出现了{2} on node {0, number, integer}.", node, new Date(), event);
        System.out.println(result);
    }


    /**
     * <h2>运行结果：</h2>
     * The disk "MyDisk" contains 0 file(s).
     * The disk "MyDisk" contains 1 file(s).
     * The disk "MyDisk" contains 1,237 file(s).
     */
    @Test
    public void messageFormatRepeatedSimple() {
        MessageFormat messageFormat = new MessageFormat("The disk \"{1}\" contains {0} file(s).");
        System.out.println(messageFormat.format(new Object[]{(long) 0, "MyDisk"}));
        System.out.println(messageFormat.format(new Object[]{(long) 1, "MyDisk"}));
        System.out.println(messageFormat.format(new Object[]{(long) 1237, "MyDisk"}));
    }

    /**
     * 当单个参数在字符串中被多次解析时，最后的匹配将是解析的最终结果。
     * 3.14, 3.1
     * [3.1]
     */
    @Test
    public void messageFormat() {
        MessageFormat mf = new MessageFormat("{0,number,#.##}, {0,number,#.#}");
        Object[] objs = {3.1415};
        String result = mf.format( objs );
        System.out.println(result);
        // result now equals "3.14, 3.1"
        System.out.println(Arrays.toString(objs));
        objs = mf.parse(result, new ParsePosition(0));
        System.out.println(Arrays.toString(objs));
        // objs now equals {new Double(3.1)}
    }

    /**
     * {@link ChoiceFormat} 使用示例
     */
    @Test
    public void messageFormatForChoiceFormat() {
        MessageFormat messageFormat = new MessageFormat("The disk \"{1}\" contains {0} file(s).");
        double[] fileLimits = {0, 1, 2};
        String[] filePart = {"no files", "one file", "{0, number} files"};
        ChoiceFormat choiceFormat = new ChoiceFormat(fileLimits, filePart);
        messageFormat.setFormatByArgumentIndex(0, choiceFormat);
        System.out.println(messageFormat.format(new Object[]{(long) 0, "MyDisk"}));
        System.out.println(messageFormat.format(new Object[]{(long) 1, "MyDisk"}));
        // 此时的 {@code 1237} 并没有相关匹配，则取{@code index}小于1237的最大索引对应的format
        System.out.println(messageFormat.format(new Object[]{(long) 1237, "MyDisk"}));
    }

    /**
     * 重置 pattern
     *
     * <h2>运行结果</h2>
     * The disk "MyDisk" contains 0 file(s).<br>
     * The disk "MyDisk" contains 1 file(s).<br>
     * The disk "MyDisk" contains 1,237 file(s).<br>
     * There are no files.<br>
     * There is one file.<br>
     * There are 1,237 files.<br>
     */
    @Test
    public void messageFormatAdvancedUsageForApplyPattern() {
        MessageFormat messageFormat = new MessageFormat("The disk \"{1}\" contains {0} file(s).");
        System.out.println(messageFormat.format(new Object[]{(long) 0, "MyDisk"}));
        System.out.println(messageFormat.format(new Object[]{(long) 1, "MyDisk"}));
        System.out.println(messageFormat.format(new Object[]{(long) 1237, "MyDisk"}));
        // 重置 pattern
        messageFormat.applyPattern("There {0,choice,0#are no files|1#is one file|1<are {0,number,integer} files}.");
        System.out.println(messageFormat.format(new Object[]{(long) 0, "MyDisk"}));
        System.out.println(messageFormat.format(new Object[]{(long) 1, "MyDisk"}));
        System.out.println(messageFormat.format(new Object[]{(long) 1237, "MyDisk"}));
    }

    /**
     * <h2>运行结果：</h2>
     * Today is 下午03时55分28秒 on 2020年8月17日 星期一<br>
     * Today is 3:55:28 CST PM on Monday, August 17, 2020<br>
     *
     * <h2>注意：</h2>
     * 调用 {@link MessageFormat#setLocale(Locale)}后，
     * 需要调用 {@link MessageFormat#applyPattern(String)}才会生效？？？
     */
    @Test
    public void messageFormatAdvancedUsageForResetLocale() {
        String pattern = "Today is {0, time, long} on {0, date, full}";
        MessageFormat messageFormat = new MessageFormat(pattern);
        System.out.println(messageFormat.format(new Object[]{new Date()}));
        messageFormat.setLocale(Locale.CANADA);
        // 调用 {@code setLocale}之后需要调用 {@code applyPattern}，否则locale无效
        messageFormat.applyPattern(pattern);
        System.out.println(messageFormat.format(new Object[]{new Date()}));
    }

    /**
     * <h2>运行结果：</h2>
     * Today is 下午05时06分58秒 on 2020年8月17日 星期一<br>
     * Today is 下午05时06分58秒 on 2020-08-17 17:06:58<br>
     *
     * 通过 {@link MessageFormat#setFormatByArgumentIndex(int, Format)}来重置 {@link Format}
     */
    @Test
    public void messageFormatAdvancedUsageForSetFormatByArgumentIndex() {
        String pattern = "Today is {0, time, long} on {1, date, full}";
        MessageFormat messageFormat = new MessageFormat(pattern);
        System.out.println(messageFormat.format(new Object[]{new Date(), new Date()}));
        messageFormat.setFormatByArgumentIndex(1, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        System.out.println(messageFormat.format(new Object[]{new Date(), new Date()}));
    }

}