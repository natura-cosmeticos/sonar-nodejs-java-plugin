package org.sonarsource.plugins.nodejs.measures;

import java.util.Arrays;
import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

public class NodeJSMetrics implements Metrics {

    public static final Metric<Integer> NUMBER_OF_DEPENDENCIES = new Metric.Builder("number_of_dependencies", "Number of nodeJS dependencies", Metric.ValueType.INT)
        .setDescription("Number of nodeJS dependencies")
        .setDirection(Metric.DIRECTION_BETTER)
        .setQualitative(false)
        .setDomain(CoreMetrics.DOMAIN_SIZE)
        .create();

    public static final Metric<Integer> NUMBER_OF_DEV_DEPENDENCIES = new Metric.Builder("number_of_dev_dependencies", "Number of nodeJS devDependencies", Metric.ValueType.INT)
        .setDescription("Number of nodeJS devDependencies")
        .setDirection(Metric.DIRECTION_BETTER)
        .setQualitative(false)
        .setDomain(CoreMetrics.DOMAIN_SIZE)
        .create();

    @Override
    public List<Metric> getMetrics() {
        return Arrays.asList(NUMBER_OF_DEPENDENCIES, NUMBER_OF_DEV_DEPENDENCIES);
    }

}