package hu.isakots.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
@OpenAPIDefinition(
    servers = {@Server(url = "/api", description = "With api servlet context")},
    info = @Info(title = "Sample API", version = "v1.0.0")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer"
)
public class OpenApiConfiguration {

    @Bean
    @Profile("prod")
    public GroupedOpenApi prodApi() {
        return GroupedOpenApi.builder()
            .group("sample-api")
            .pathsToExclude("/**")
            .build();
    }

    @Bean
    @Profile("!prod")
    public GroupedOpenApi openApi() {
        return GroupedOpenApi.builder()
            .addOpenApiCustomiser(sortOperationsByTagNameCustomizer())
            .addOpenApiCustomiser(securitySchemeCustomizer())
            .group("sample-api")
            .pathsToMatch("/**")
            .build();
    }

    public OpenApiCustomiser securitySchemeCustomizer() {
        return openApi -> openApi.setSecurity(Collections.singletonList(new SecurityRequirement().addList("bearerAuth")));
    }

    public OpenApiCustomiser sortOperationsByTagNameCustomizer() {
        return openApi -> {
            Paths paths = openApi.getPaths().entrySet()
                .stream()
                .sorted(Comparator.comparing(entry -> getOperationTag(entry.getValue())))
                .collect(Paths::new, (map, item) -> map.addPathItem(item.getKey(), item.getValue()), Paths::putAll);

            openApi.setPaths(paths);
        };
    }

    private String getOperationTag(PathItem pathItem) {
        return Stream.of(pathItem.getGet(), pathItem.getPost(), pathItem.getDelete(), pathItem.getPut(),
                pathItem.getHead(), pathItem.getOptions(), pathItem.getTrace(), pathItem.getPatch())
            .filter(Objects::nonNull)
            .map(Operation::getTags)
            .flatMap(Collection::stream)
            .findFirst()
            .orElse("");
    }

}
