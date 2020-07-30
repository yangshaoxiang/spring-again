package com.springstudy.springstudy.spring_base.annotationtest.sometest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {MapperAutoConfiguredMyBatisRegistrar.class})
public class MapperAutoConfig {}