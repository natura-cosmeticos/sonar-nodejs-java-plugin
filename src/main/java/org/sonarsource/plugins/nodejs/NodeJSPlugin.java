package org.sonarsource.plugins.nodejs;

import org.sonar.api.Plugin;
import org.sonarsource.plugins.nodejs.measures.DependenciesCounterSensor;
import org.sonarsource.plugins.nodejs.measures.NodeJSMetrics;
import org.sonarsource.plugins.nodejs.web.NodeJSPageDefinition;

public class NodeJSPlugin implements Plugin {

    @Override
    public void define(Context context) {
        // Metrics
        context.addExtensions(NodeJSMetrics.class, DependenciesCounterSensor.class);

        // Pages
        context.addExtension(NodeJSPageDefinition.class);
    }
}
