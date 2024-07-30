package dev.thiagooliveira.delivery.menus.mappers;

import dev.thiagooliveira.delivery.menus.model.MenuItem;
import dev.thiagooliveira.delivery.menus.spec.dto.MenuPage;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@Mapper
public interface MenuMapper {
    dev.thiagooliveira.delivery.menus.spec.dto.MenuItem toMenuItem(MenuItem item);

    MenuItem toMenuItem(dev.thiagooliveira.delivery.menus.spec.dto.MenuItem item);

    MenuPage toMenuPage(org.springframework.data.domain.Page<dev.thiagooliveira.delivery.menus.model.MenuItem> page);

    default PageImpl map(MenuPage page) {
        return new PageImpl<>(
                page.getContent(),
                PageRequest.of(
                        page.getPageable().getPageNumber(), page.getPageable().getPageSize()),
                page.getTotalElements());
    }
}
