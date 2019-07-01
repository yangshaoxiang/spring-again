package com.springstudy.springstudy.spring_base.annotationtest.config;


import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;


/**
 * 自定义扫包过滤规则 配置在@ComponentScan注解的excludeFilters中的话 true表示过滤
 */
public class SelfScanFilterConfig implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)  {
        //获取当前类的注解源信息
       // AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前类的class的源信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类的资源信息
       // Resource resource = metadataReader.getResource();
        //包含Dao的不扫描
        return classMetadata.getClassName().contains("Dao");

    }
}
