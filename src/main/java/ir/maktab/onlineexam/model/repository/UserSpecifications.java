package ir.maktab.onlineexam.model.repository;

import com.mysql.cj.util.StringUtils;
import ir.maktab.onlineexam.model.entity.Role;
import ir.maktab.onlineexam.model.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserSpecifications {
    public static Specification<User> findMaxMatch(String name,
                                                   String family,
                                                   String email,
                                                   Role role) {
        return (Specification<User>) (root, criteriaQuery, builder) -> {
            CriteriaQuery<User> resultCriteria = builder.createQuery(User.class);

            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isNullOrEmpty(name)) {
                predicates.add(builder.equal(root.get("name"), name));
            }
            if (!StringUtils.isNullOrEmpty(family)) {
                predicates.add(builder.equal(root.get("family"), family));
            }
            if (!StringUtils.isNullOrEmpty(email)) {
                predicates.add(builder.equal(root.get("email"), email));
            }
            if (Objects.nonNull(role)) {
                predicates.add(builder.equal(root.get("role"), role));
            }
            predicates.add(builder.notEqual(root.get("role").get("roleName"), "admin"));
            resultCriteria.select(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }
}
