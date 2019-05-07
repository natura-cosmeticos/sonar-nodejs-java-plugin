package org.sonarsource.plugins.nodejs.factories;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.sonarsource.plugins.nodejs.entities.NodeJSPackage;
import org.sonarsource.plugins.nodejs.factories.exceptions.EntityBuildingException;

public class NodeJSPackageFactory {
    private static final NodeJSPackageFactory instance = new NodeJSPackageFactory();
    private static final ObjectMapper mapper = new ObjectMapper();

    private NodeJSPackageFactory() {
    }

    public static NodeJSPackageFactory getInstance() {
        return instance;
    }

    public NodeJSPackage buildNodeJSPackage(InputStream inputFile) throws EntityBuildingException {
        try {
            return mapper.readValue(inputFile, NodeJSPackage.class);
        } catch (IOException e) {
            throw new EntityBuildingException("Couldn't build a NodeJSPackage: " + e.getMessage(), e);
        }
    }
}