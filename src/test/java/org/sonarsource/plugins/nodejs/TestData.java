package org.sonarsource.plugins.nodejs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.google.common.collect.ImmutableSet;

import org.sonarsource.plugins.nodejs.entities.NodeJSDependency;

public final class TestData {

    public static final File FILE_PACKAGE_OK_JSON = new File("src/test/resources/test_package_ok.json");
    public static final File FILE_PACKAGE_OK_NO_DEV_DEPENDENCIES_JSON = new File("src/test/resources/test_package_ok_no_devdependencies.json");
    public static final File FILE_PACKAGE_NOT_OK_JSON = new File("src/test/resources/test_package_not_ok.json");
    public static final File FILE_DEPENDENCIES_ANALYSIS_OK_JSON = new File("src/test/resources/test_dependencies-analysis_ok.json");
    public static final File FILE_DEPENDENCIES_ANALYSIS_NOT_OK_JSON = new File("src/test/resources/test_dependencies-analysis_not_ok.json");
    public static final ImmutableSet<NodeJSDependency> PACKAGE_OK_JSON_DEPENDENCIES = ImmutableSet.of(new NodeJSDependency("babel-eslint", "10.0.1"), new NodeJSDependency("express", "4.16.3"));
    public static final ImmutableSet<NodeJSDependency> PACKAGE_OK_JSON_DEV_DEPENDENCIES = ImmutableSet.of(new NodeJSDependency("@types/eslint", "4.16.3"), new NodeJSDependency("@types/estree", "0.0.39"), new NodeJSDependency("typescript", "3.0.3"));
    public static final int NUMBER_OF_DEPENDENCIES_OF_THE_DEPENDENCIES_ANALYSIS_FILE = 7;

    public static FileInputStream fileInputStreamPackageOKJson() {
        return newFileInputStream(FILE_PACKAGE_OK_JSON);
    }

    public static FileInputStream fileInputStreamPackageOKNoDevDependenciesJson() {
        return newFileInputStream(FILE_PACKAGE_OK_NO_DEV_DEPENDENCIES_JSON);
    }

    public static FileInputStream fileInputStreamPackageNotOKJson() {
        return newFileInputStream(FILE_PACKAGE_NOT_OK_JSON);
    }

    public static FileInputStream fileInputStreamDependenciesAnalysisOKJson() {
        return newFileInputStream(FILE_DEPENDENCIES_ANALYSIS_OK_JSON);
    }

    public static FileInputStream fileInputStreamDependenciesAnalysisNotOKJson() {
        return newFileInputStream(FILE_DEPENDENCIES_ANALYSIS_NOT_OK_JSON);
    }

    private static FileInputStream newFileInputStream(File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}