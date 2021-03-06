package com.epam.esm.util.linkbuilders;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.util.SearchCriteria;
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
        target.add(linkTo(methodOn(TagController.class).readTagsByParams(null, page - 1, size)).withRel("prev").expand());
    }

    @Override
    public void buildNextPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(TagController.class).readTagsByParams(null, page + 1, size)).withRel("next").expand());
    }

    @Override
    public void buildLastPageLink(CollectionModel target, int lastPage, int size) {
        target.add(linkTo(methodOn(TagController.class).readTagsByParams(null, lastPage, size)).withRel("last").expand());
    }

    public void buildPreviousPageLink(CollectionModel target, String name, int page, int size) {
        target.add(linkTo(methodOn(TagController.class).readTagsByParams(name, page - 1, size)).withRel("prev").expand());
    }

    public void buildNextPageLink(CollectionModel target, String name, int page, int size) {
        target.add(linkTo(methodOn(TagController.class).readTagsByParams(name, page + 1, size)).withRel("next").expand());
    }

    public void buildLastPageLink(CollectionModel target, String name, int lastPage, int size) {
        target.add(linkTo(methodOn(TagController.class).readTagsByParams(name, lastPage, size)).withRel("last").expand());
    }
}
