package com.xiaobei.spring.demo.dependency.conversion;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/9/8 21:23
 */
public class BooleanArrayEditor extends CustomCollectionEditor {

    private static final String TRUE = "true";

    private static final String FALSE = "false";

    public BooleanArrayEditor(Class<? extends Collection> collectionType) {
        super(collectionType);
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        boolean[] resultText;
        if(StringUtils.hasLength(text)) {
            String[] booleanArr = text.split(",");
            resultText = new boolean[booleanArr.length];
            for (int i = 0; i < booleanArr.length; i++) {
                String str = booleanArr[i].trim();
                if(TRUE.equalsIgnoreCase(str)) {
                    resultText[i] = Boolean.TRUE;
                } else if(FALSE.equalsIgnoreCase(str)) {
                    resultText[i] = Boolean.FALSE;
                } else {
                    // 异常处理...
                    super.setAsText(text);
                }
            }
            super.setValue(resultText);
        } else {
            // 异常处理...
            super.setAsText(text);
        }
    }
}
