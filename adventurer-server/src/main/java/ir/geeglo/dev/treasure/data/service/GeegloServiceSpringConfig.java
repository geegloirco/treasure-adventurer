package ir.geeglo.dev.treasure.data.service;

import ir.geeglo.dev.treasure.data.dao.PersistenceSpringConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceSpringConfig.class)
@ComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class GeegloServiceSpringConfig {
}
