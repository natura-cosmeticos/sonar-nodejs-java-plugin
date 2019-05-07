package org.sonarsource.plugins.nodejs.entities;

import com.google.common.base.Objects;

public class NodeJSDependency {

    private String name;
    private String version;

    public NodeJSDependency(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object other) {
        if (super.equals(other))
            return true;
        if (!(other instanceof NodeJSDependency))
            return false;
        NodeJSDependency d = (NodeJSDependency) other;
        return Objects.equal(this.name, d.name) && Objects.equal(this.version, d.version);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, version);
    }
}