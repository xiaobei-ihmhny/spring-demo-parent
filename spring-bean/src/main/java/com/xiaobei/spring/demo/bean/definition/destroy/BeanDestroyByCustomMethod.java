package com.xiaobei.spring.demo.bean.definition.destroy;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/2 9:52
 */
public class BeanDestroyByCustomMethod {

    private String msg;

    public BeanDestroyByCustomMethod() {
    }

    public BeanDestroyByCustomMethod(String msg) {
        this.msg = msg;
        System.out.println("信息为：" + msg);
    }

    public void destroyCustom() {
        System.out.println(this.msg + "：自定义bean的销毁方法");
    }
}
