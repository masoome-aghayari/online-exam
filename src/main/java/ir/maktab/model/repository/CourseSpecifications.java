package ir.maktab.model.repository;

import com.mysql.cj.util.StringUtils;
import ir.maktab.model.entity.Category;
import ir.maktab.model.entity.Course;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseSpecifications {
    public static Specification<Course> findMaxMatch(Category category, String title, int duration, int capacity,
                                                     LocalDate startDate, LocalDate endDate) {
        return (Specification<Course>) (root, criteriaQuery, builder) -> {
            CriteriaQuery<Course> resultCriteria = builder.createQuery(Course.class);

            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(category)) {
                predicates.add(builder.equal(root.get("category"), category));
            }
            if (!StringUtils.isNullOrEmpty(title)) {
                predicates.add(builder.equal(root.get("title"), title));
            }
            if (duration != 0) {
                predicates.add(builder.equal(root.get("duration"), duration));
            }
            if (capacity != 0) {
                predicates.add(builder.equal(root.get("capacity"), capacity));
            }
            if (Objects.nonNull(startDate)) {
                predicates.add(builder.equal(root.get("startDate"), startDate));
            }
            if (Objects.nonNull(endDate)) {
                predicates.add(builder.equal(root.get("endDate"), endDate));
            }
            resultCriteria.select(root).where(predicates.toArray(new Predicate[0]));
            return resultCriteria.getRestriction();
        };
    }
}
