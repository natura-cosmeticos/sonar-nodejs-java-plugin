package org.sonarsource.plugins.nodejs.measures;

import java.io.IOException;

import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.ce.measure.Measure;
import org.sonar.api.ce.measure.MeasureComputer;
import org.sonar.api.measures.Metric;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.plugins.nodejs.entities.NodeJSPackage;
import org.sonarsource.plugins.nodejs.factories.NodeJSPackageFactory;
import org.sonarsource.plugins.nodejs.factories.exceptions.EntityBuildingException;

public class DependenciesCounterSensor implements Sensor, MeasureComputer {

    private static final Logger LOGGER = Loggers.get(DependenciesCounterSensor.class);
    private static final String NODEJS_PACKAGE_FILENAME = "dependencies-analysis.json";
    private static final String[] metricKeys = new String[] {NodeJSMetrics.NUMBER_OF_DEPENDENCIES.key(), NodeJSMetrics.NUMBER_OF_DEV_DEPENDENCIES.key()};

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("Computes the number of dependencies");
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        InputFile inputFile = fs.inputFile(fs.predicates().hasRelativePath(NODEJS_PACKAGE_FILENAME));
        if (inputFile != null) {
            try {
                NodeJSPackage nodeJSPackage = NodeJSPackageFactory.getInstance().buildNodeJSPackage(inputFile.inputStream());
                setMeasure(context, inputFile, NodeJSMetrics.NUMBER_OF_DEPENDENCIES, nodeJSPackage.getDependencies().size());
                setMeasure(context, inputFile, NodeJSMetrics.NUMBER_OF_DEV_DEPENDENCIES, nodeJSPackage.getDevDependencies().size());
            } catch (EntityBuildingException | IOException e) {
                LOGGER.error(String.format("Error to process the file %s. %s", NODEJS_PACKAGE_FILENAME, e));
            }
        }
    }

    private void setMeasure(SensorContext context, InputFile inputFile, Metric<Integer> metric, int value) {
        context.<Integer>newMeasure().forMetric(metric).on(inputFile).withValue(value).save();
    }

    @Override
    public MeasureComputerDefinition define(MeasureComputerDefinitionContext defContext) {
        return defContext.newDefinitionBuilder()
            .setOutputMetrics(metricKeys)
            .build();
    }

    @Override
    public void compute(MeasureComputerContext context) {
        for (String metricKey : metricKeys)
            for (Measure child : context.getChildrenMeasures(metricKey))
                context.addMeasure(metricKey, child.getIntValue());
    }
}