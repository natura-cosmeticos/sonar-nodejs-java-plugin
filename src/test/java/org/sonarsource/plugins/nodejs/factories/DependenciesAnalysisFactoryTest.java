package org.sonarsource.plugins.nodejs.factories;

import org.junit.Test;
import org.sonarsource.plugins.nodejs.entities.DependenciesAnalysis;
import org.sonarsource.plugins.nodejs.factories.exceptions.EntityBuildingException;

import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamDependenciesAnalysisOKJson;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamDependenciesAnalysisNotOKJson;
import static org.sonarsource.plugins.nodejs.TestData.NUMBER_OF_DEPENDENCIES_OF_THE_DEPENDENCIES_ANALYSIS_FILE;
import static org.assertj.core.api.Assertions.assertThat;

public class DependenciesAnalysisFactoryTest {

    @Test
    public void shouldBuildDependenciesAnalysisWithSevenDependencies() throws EntityBuildingException {
        DependenciesAnalysis dependenciesAnalysis = DependenciesAnalysisFactory.getInstance()
            .buildDependenciesAnalysis(fileInputStreamDependenciesAnalysisOKJson());
        assertThat(dependenciesAnalysis.getNumberOfDependencies()).isEqualTo(NUMBER_OF_DEPENDENCIES_OF_THE_DEPENDENCIES_ANALYSIS_FILE);
    }

    @Test(expected = EntityBuildingException.class)
    public void shouldThrowEntityBuildingExceptionWhenJsonParserFailed() throws EntityBuildingException {
        DependenciesAnalysisFactory.getInstance().buildDependenciesAnalysis(fileInputStreamDependenciesAnalysisNotOKJson());
    }
}