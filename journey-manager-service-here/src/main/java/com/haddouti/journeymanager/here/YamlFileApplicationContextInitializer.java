package com.haddouti.journeymanager.here;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

/**
 * Use {@link YamlPropertySourceLoader} to load the additional yml files, cause
 * the regular PropertySource can not handle yml files.
 *
 */
class YamlFileApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
	@Override
	public void initialize(final ConfigurableApplicationContext applicationContext) {
		try {
			final Resource[] resources = applicationContext.getResources("classpath:here.*yml");
			final YamlPropertySourceLoader sourceLoader = new YamlPropertySourceLoader();
			int i = 0;
			for (final Resource res : resources) {
				final List<PropertySource<?>> yamlTestProperties = sourceLoader.load("ymlHere" + i++, res);
				applicationContext.getEnvironment().getPropertySources().addFirst(yamlTestProperties.get(0));
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}