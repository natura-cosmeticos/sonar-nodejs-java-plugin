package org.sonarsource.plugins.nodejs.factories;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.sonarsource.plugins.nodejs.entities.DependenciesAnalysis;
import org.sonarsource.plugins.nodejs.factories.exceptions.EntityBuildingException;

public class DependenciesAnalysisFactory {
    
    private static final DependenciesAnalysisFactory instance = new DependenciesAnalysisFactory();
    private static final ObjectMapper mapper = new ObjectMapper();

    private DependenciesAnalysisFactory() {
    }

    public static DependenciesAnalysisFactory getInstance() {
        return instance;
    }

    public DependenciesAnalysis buildDependenciesAnalysis(InputStream inputFile) throws EntityBuildingException {
        try {
            return mapper.readValue(inputFile, DependenciesAnalysis.class);
        } catch (IOException e) {
            throw new EntityBuildingException("Couldn't build a DependenciesAnalysis: " + e.getMessage(), e);
        }
    }
}