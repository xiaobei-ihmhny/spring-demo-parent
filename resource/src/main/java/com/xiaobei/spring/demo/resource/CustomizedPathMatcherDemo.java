package com.xiaobei.spring.demo.resource;

import com.xiaobei.spring.demo.resource.utils.ResourceUtils;
import org.junit.Test;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author <a href="https://github.com/xiaobei-ihmhny">xiaobei-ihmhny</a>
 * @date 2020/8/16 20:20
 */
public class CustomizedPathMatcherDemo {
    /**
     * @throws IOException
     *
     * <h2>注意</h2>
     * 使用 {@code main}方法和使用 {@link Test}获取到的 {@code System.getProperties().get("user.dir")}是不同的！！！
     * 当使用 {@code main}方法时在 {@code user.dir}后需要添加当前模块名，而{@link Test}则不需要添加
     */
    @Test
    public void originalPathMatcher() throws IOException {
        pathMatcher((resolver) -> {});
    }

    /**
     * 使用自定义的路径匹配规则
     * @throws IOException
     */
    @Test
    public void customPathMatcher() throws IOException {
        pathMatcher((resolver) -> resolver.setPathMatcher(new JavaPathMatcher()));
    }

    /**
     * 路径匹配测试方法
     * @param resolverConsumer
     * @throws IOException
     */
    private void pathMatcher(Consumer<PathMatchingResourcePatternResolver> resolverConsumer) throws IOException {
        String filePath = System.getProperties().get("user.dir") + "/src/main/java/com/xiaobei/spring/demo/resource/";
        filePath = filePath  + "*.java";
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(new FileSystemResourceLoader());
        resolverConsumer.accept(resolver);
        Resource[] resources = resolver.getResources(filePath);
        Stream.of(resources).map(ResourceUtils::getContext).forEach(System.out::println);
    }

    /**
     * 自定义的匹配java文件的{@link PathMatcher}
     *
     * @see PathMatcher
     */
    static class JavaPathMatcher implements PathMatcher {

        @Override
        public boolean isPattern(String path) {
            return path.endsWith(".java");
        }

        @Override
        public boolean match(String pattern, String path) {
            return path.endsWith(".java");
        }

        @Override
        public boolean matchStart(String pattern, String path) {
            return false;
        }

        @Override
        public String extractPathWithinPattern(String pattern, String path) {
            return null;
        }

        @Override
        public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
            return null;
        }

        @Override
        public Comparator<String> getPatternComparator(String path) {
            return null;
        }

        @Override
        public String combine(String pattern1, String pattern2) {
            return null;
        }
    }
}
