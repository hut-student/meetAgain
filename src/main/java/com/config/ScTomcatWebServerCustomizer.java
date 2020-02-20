package com.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class ScTomcatWebServerCustomizer  implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
    public ScTomcatWebServerCustomizer() {
    }

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> connector.setAttribute("relaxedQueryChars", "{}[]|"));
    }
}