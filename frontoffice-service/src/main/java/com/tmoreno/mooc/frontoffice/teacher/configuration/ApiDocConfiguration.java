package com.tmoreno.mooc.frontoffice.teacher.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocConfiguration {

    @Bean
    public OpenAPI apiInfo(@Value("${info.project.version}") String version) {
        return new OpenAPI()
            .info(new Info()
                .title("Frontoffice Service")
                .description("REST Api for the frontoffice app")
                .version(version)
            )
            .addTagsItem(createTag("Frontoffice Service", "REST Api for the frontoffice app"));
    }

    private static Tag createTag(String name, String description) {
        Tag tag = new Tag();
        tag.setName(name);
        tag.setDescription(description);
        return tag;
    }
}
