package org.sonarsource.plugins.nodejs.web;

import org.sonar.api.web.page.Context;
import org.sonar.api.web.page.Page;
import org.sonar.api.web.page.Page.Scope;
import org.sonar.api.web.page.PageDefinition;

public class NodeJSPageDefinition implements PageDefinition {

    @Override
    public void define(Context context) {
        context.addPage(Page.builder("nodejs/dependencies_report")
            .setName("Dependencies report")
            .setScope(Scope.COMPONENT).build());
    }

}