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
package de.olivergierke.moduliths.model.test;

import lombok.EqualsAndHashCode;

import java.io.IOException;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

/**
 * @author Oliver Gierke
 */
@EqualsAndHashCode(callSuper = false)
class ModuleTypeExcludeFilter extends TypeExcludeFilter {

	private final ModuleTestExecution type;

	public ModuleTypeExcludeFilter(Class<?> testClass) {
		this.type = new ModuleTestExecution(testClass);
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.boot.context.TypeExcludeFilter#match(org.springframework.core.type.classreading.MetadataReader, org.springframework.core.type.classreading.MetadataReaderFactory)
	 */
	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {

		String typePackageName = ClassUtils.getPackageName(metadataReader.getClassMetadata().getClassName());

		return type.getBasePackages().noneMatch(typePackageName::startsWith);
	}
}
