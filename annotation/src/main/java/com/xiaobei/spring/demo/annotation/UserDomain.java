package com.xiaobei.spring.demo.annotation;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/26 22:17
 */
@HierarchicalCustomizedComponent
public class UserDomain {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserDomain{" +
                "id=" + id +
                '}';
    }
}
