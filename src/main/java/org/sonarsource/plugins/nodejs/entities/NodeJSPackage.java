package org.sonarsource.plugins.nodejs.entities;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.google.common.collect.ImmutableSet;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeJSPackage {

    private ImmutableSet<NodeJSDependency> dependencies = ImmutableSet.of();
    private ImmutableSet<NodeJSDependency> devDependencies = ImmutableSet.of();

    public ImmutableSet<NodeJSDependency> getDependencies() {
        return dependencies;
    }

    public ImmutableSet<NodeJSDependency> getDevDependencies() {
        return devDependencies;
    }

    @JsonSetter("dependencies")
    private void setDependencies(Map<String, String> dependencies) {
        this.dependencies = dependencies.entrySet().stream().map(e -> new NodeJSDependency(e.getKey(), e.getValue()))
            .collect(ImmutableSet.toImmutableSet());
    }

    @JsonSetter("devDependencies")
    private void setDevDependencies(Map<String, String> devDependencies) {
        this.devDependencies = devDependencies.entrySet().stream().map(e -> new NodeJSDependency(e.getKey(), e.getValue()))
            .collect(ImmutableSet.toImmutableSet());
    }
}