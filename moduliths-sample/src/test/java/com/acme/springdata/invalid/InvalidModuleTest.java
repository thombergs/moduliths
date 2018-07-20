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
package com.acme.springdata.invalid;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import de.olivergierke.moduliths.model.test.ModuleTest;
import de.olivergierke.moduliths.model.test.ModuleTestExecution;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.acme.myproject.NonVerifyingModuleTest;
import com.acme.springdata.moduleA.RepositoryA;
import com.acme.springdata.moduleA.internal.InternalRepositoryA;

/**
 * @author Tom Hombergs
 */
@NonVerifyingModuleTest(ModuleTest.BootstrapMode.DIRECT_DEPENDENCIES)
@RunWith(SpringRunner.class)
public class InvalidModuleTest {

	@Autowired ApplicationContext context;

	@Autowired ModuleTestExecution testExecution;

	@Test
	public void bootstrapsModuleBWithDependenciesFromModuleB() {

		context.getBean(InvalidComponent.class);
		context.getBean(RepositoryA.class);

		// InternalRepository is in another module, but is loaded into the ApplicationContext nevertheless, since we used
		// DIRECT_DEPENDENCIES bootstrap mode.
		context.getBean(InternalRepositoryA.class);

		// Invalid dependency between InvalidComponent and InternalRepositoryA.
		assertThatExceptionOfType(IllegalStateException.class).isThrownBy(() -> testExecution.verify());
	}
}
