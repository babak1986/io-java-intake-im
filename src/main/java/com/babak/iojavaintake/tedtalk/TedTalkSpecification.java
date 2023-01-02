package com.babak.iojavaintake.tedtalk;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TedTalkSpecification implements Specification<TedTalk> {

    private TedTalkDto tedTalk = new TedTalkDto();

    /**
     * Apply filter model(TedTalkDto object) to search criteria on fields: title, author, views and likes
     *
     * @param root
     * @param query
     * @param criteriaBuilder
     * @return
     */
    @Override
    public Predicate toPredicate(Root<TedTalk> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(tedTalk.getTitle())) {
            predicates.add(criteriaBuilder.like(root.get("title"), '%' + tedTalk.getTitle() + '%'));
        }
        if (StringUtils.hasText(tedTalk.getAuthor())) {
            predicates.add(criteriaBuilder.like(root.get("author"), '%' + tedTalk.getAuthor() + '%'));
        }
        if (tedTalk.getViews() != 0) {
            predicates.add(criteriaBuilder.equal(root.get("views"), tedTalk.getViews()));
        }
        if (tedTalk.getLikes() != 0) {
            predicates.add(criteriaBuilder.equal(root.get("likes"), tedTalk.getLikes()));
        }
        return criteriaBuilder.and(
                criteriaBuilder.isFalse(root.get("deleted")),
                criteriaBuilder.or(predicates.toArray(new Predicate[]{})));
    }
}
