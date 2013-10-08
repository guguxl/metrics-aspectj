/**
 * Copyright (C) 2013 Antonin Stefanutti (antonin.stefanutti@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.stefanutti.metrics.aspectj;

/* packaged-protected */ class MetricStrategyFactory {

    static MetricStrategy newInstance(Object object) {
        try {
            getClassLoader(object.getClass()).loadClass("javax.el.ELProcessor");
        } catch (ClassNotFoundException cause) {
            // Expression Language 3.0 is not available, fall back to SE implementation
            return new JavaSeMetricStrategy();
        }
        return new JavaxElMetricStrategy(object);
    }

    static MetricStrategy newInstance(Class<?> clazz) {
        try {
            getClassLoader(clazz).loadClass("javax.el.ELProcessor");
        } catch (ClassNotFoundException cause) {
            // Expression Language 3.0 is not available, fall back to SE implementation
            return new JavaSeMetricStrategy();
        }
        return new JavaSeMetricStrategy();
    }

    private static ClassLoader getClassLoader(Class<?> clazz) {
        if (Thread.currentThread().getContextClassLoader() != null)
            return Thread.currentThread().getContextClassLoader();
        else
            return clazz.getClassLoader();
    }
}
