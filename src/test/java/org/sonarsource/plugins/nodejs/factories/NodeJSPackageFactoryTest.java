package org.sonarsource.plugins.nodejs.factories;


import static org.assertj.core.api.Assertions.assertThat;
import static org.sonar.api.internal.google.common.collect.Sets.symmetricDifference;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamPackageNotOKJson;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamPackageOKJson;
import static org.sonarsource.plugins.nodejs.TestData.fileInputStreamPackageOKNoDevDependenciesJson;
import static org.sonarsource.plugins.nodejs.TestData.PACKAGE_OK_JSON_DEPENDENCIES;
import static org.sonarsource.plugins.nodejs.TestData.PACKAGE_OK_JSON_DEV_DEPENDENCIES;

import org.junit.Test;
import org.sonarsource.plugins.nodejs.entities.NodeJSPackage;
import org.sonarsource.plugins.nodejs.factories.exceptions.EntityBuildingException;

public class NodeJSPackageFactoryTest {

    @Test
    public void shouldBuildNodeJSPackageWithTwoDependenciesAndThreeDevDependencies() throws EntityBuildingException {
        NodeJSPackage nodePackage = NodeJSPackageFactory.getInstance().buildNodeJSPackage(fileInputStreamPackageOKJson());
        assertThat(symmetricDifference(PACKAGE_OK_JSON_DEPENDENCIES, nodePackage.getDependencies())).isEmpty();
        assertThat(symmetricDifference(PACKAGE_OK_JSON_DEV_DEPENDENCIES, nodePackage.getDevDependencies())).isEmpty();
    }

    @Test
    public void shouldBuildNodeJSPackageWithTwoDependenciesAndZeroDevDependencies() throws EntityBuildingException {
        NodeJSPackage nodePackage = NodeJSPackageFactory.getInstance().buildNodeJSPackage(fileInputStreamPackageOKNoDevDependenciesJson());
        assertThat(symmetricDifference(PACKAGE_OK_JSON_DEPENDENCIES, nodePackage.getDependencies())).isEmpty();
        assertThat(nodePackage.getDevDependencies()).isEmpty();
    }

    @Test(expected = EntityBuildingException.class)
    public void shouldThrowEntityBuildingExceptionWhenJsonParserFailed() throws EntityBuildingException {
        NodeJSPackageFactory.getInstance().buildNodeJSPackage(fileInputStreamPackageNotOKJson());
    }
}