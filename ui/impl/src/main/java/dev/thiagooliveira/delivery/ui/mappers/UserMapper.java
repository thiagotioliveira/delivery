package dev.thiagooliveira.delivery.ui.mappers;

import dev.thiagooliveira.delivery.ui.dto.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

@Mapper
public interface UserMapper {

    @Mapping(target = "id", expression = "java(principal.getAttribute(\"sub\"))")
    @Mapping(target = "name", expression = "java(principal.getAttribute(\"name\"))")
    @Mapping(target = "email", expression = "java(principal.getAttribute(\"email\"))")
    @Mapping(target = "token", expression = "java(principal.getIdToken().getTokenValue())")
    User toUser(OidcUser principal);
}
