package com.epam.esm.util.linkbuilders;

import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The link builder class for {@link TagDTO} objects.
 */
@Component
public class TagLinkBuilder implements LinkBuilder<TagDTO> {

    @Override
    public void buildLink(TagDTO target) {
        Link selfLink = linkTo(TagController.class).slash(target.getId()).withSelfRel();
        target.add(selfLink);
    }

    @Override
    public void buildLinks(Collection<TagDTO> targets) {
        if (targets != null) {
            targets.forEach(this::buildLink);
        }
    }

    @Override
    public void buildPreviousPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(TagController.class).readAllTags(page - 1, size)).withRel("prev"));
    }

    @Override
    public void buildNextPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(TagController.class).readAllTags(page + 1, size)).withRel("next"));
    }
}
