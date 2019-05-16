package org.sonarsource.plugins.nodejs.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DependenciesAnalysis {

    @JsonProperty("dependencies")
    private int numberOfDependencies;

    public int getNumberOfDependencies() {
        return numberOfDependencies;
    }
}