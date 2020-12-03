package com.epam.esm.util.linkbuilders;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDTO;
import com.epam.esm.util.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * The link builder class for {@link GiftCertificateDTO} objects.
 */
@Component
public class GiftCertificateLinkBuilder implements LinkBuilder<GiftCertificateDTO> {

    private TagLinkBuilder tagLinkBuilder;

    @Autowired
    public void setTagLinkBuilder(TagLinkBuilder tagLinkBuilder) {
        this.tagLinkBuilder = tagLinkBuilder;
    }

    @Override
    public void buildLink(GiftCertificateDTO target) {
        Link selfLink = linkTo(GiftCertificateController.class).slash(target.getId()).withSelfRel();
        target.add(selfLink);
        tagLinkBuilder.buildLinks(target.getTags());
    }

    @Override
    public void buildLinks(Collection<GiftCertificateDTO> targets) {
        targets.forEach(this::buildLink);
    }

    @Override
    public void buildPreviousPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(GiftCertificateController.class)
                .readCertificatesByParams(null, page - 1, size))
                .withRel("prev"));
    }

    @Override
    public void buildNextPageLink(CollectionModel target, int page, int size) {
        target.add(linkTo(methodOn(GiftCertificateController.class)
                .readCertificatesByParams(null,page + 1, size))
                .withRel("next"));
    }

    public void buildPreviousPageLink(CollectionModel target, SearchCriteria searchCriteria, int page, int size) {
        target.add(linkTo(methodOn(GiftCertificateController.class)
                .readCertificatesByParams(searchCriteria,page - 1, size))
                .withRel("prev"));
    }

    public void buildNextPageLink(CollectionModel target, SearchCriteria searchCriteria, int page, int size) {
        target.add(linkTo(methodOn(GiftCertificateController.class)
                .readCertificatesByParams(searchCriteria,page + 1, size))
                .withRel("next"));
    }
}
