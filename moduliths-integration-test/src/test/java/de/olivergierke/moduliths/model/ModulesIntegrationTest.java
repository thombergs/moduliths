/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.olivergierke.moduliths.model;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;

import com.acme.myproject.Application;

/**
 * @author Oliver Gierke
 * @author Peter Gafert
 */
public class ModulesIntegrationTest {

	Modules modules = Modules.of(Application.class);

	@Test
	public void exposesModulesForPrimaryPackages() {

		Optional<Module> module = modules.getModuleByName("moduleB");

		assertThat(module).hasValueSatisfying(it -> {
			assertThat(it.getDependencies(modules)).anySatisfy(dep -> {
				assertThat(dep.getName()).isEqualTo("moduleA");
			});
		});
	}

	@Test
	public void usesExplicitlyAnnotatedDisplayName() {

		Optional<Module> module = modules.getModuleByName("moduleC");

		assertThat(module).hasValueSatisfying(it -> {
			assertThat(it.getDisplayName()).isEqualTo("MyModule C");
		});
	}

	@Test
	public void rejectsDependencyIntoInternalPackage() {

		Optional<Module> module = modules.getModuleByName("invalid");

		assertThat(module).hasValueSatisfying(it -> {
			assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> {
				it.verifyDependencies(modules);
			});
		});
	}

	@Test
	public void complexModuleExposesNamedInterfaces() {

		Optional<Module> module = modules.getModuleByName("complex");

		assertThat(module).hasValueSatisfying(it -> {
			assertThat(it.getNamedInterfaces().stream().map(NamedInterface::getName)) //
					.containsExactlyInAnyOrder("API", "SPI");
		});
	}
}
