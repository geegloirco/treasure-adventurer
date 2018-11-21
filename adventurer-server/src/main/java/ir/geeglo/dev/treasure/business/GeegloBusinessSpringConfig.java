package ir.geeglo.dev.treasure.business;

import ir.geeglo.dev.treasure.data.service.GeegloServiceSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@Import(GeegloServiceSpringConfig.class)
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class GeegloBusinessSpringConfig {
}
