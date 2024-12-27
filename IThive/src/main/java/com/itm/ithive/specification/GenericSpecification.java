package com.itm.ithive.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.Map;

public class GenericSpecification<T> {
    public static <T> Specification<T> dynamicSearch(Map<String, String> params, Class<T> entityClass) {
        return (root, query, criteriaBuilder) -> {
            Predicate finalPredicate = criteriaBuilder.disjunction();

            for (Map.Entry<String, String> entry : params.entrySet()) {

                String key = entry.getKey().toLowerCase();
                String value = entry.getValue().toLowerCase();

                Field field = null;
                for (Field f : entityClass.getDeclaredFields()) {
                    if (f.getName().toLowerCase().equals(key)) {
                        field = f;
                        key = f.getName();
                        break;
                    }
                }

                if (field != null && field.getType().equals(String.class)) {

                    //value = gav SELECT * FROM EntityName WHERE entity_attr LIKE "% value % "
                    Predicate likePredicate = criteriaBuilder.like(root.get(key), "%" + value + "%");

                    //results from before + current_result
                    finalPredicate = criteriaBuilder.or(finalPredicate, likePredicate);

                } else if (field != null && field.getType().equals(int.class)) {
                    //SELECT * FROM EntityName WHERE entity_attr = valuefrommap
                    Predicate equalPredicate = criteriaBuilder.equal(root.get(key), Integer.parseInt(value));
                    finalPredicate = criteriaBuilder.or(finalPredicate, equalPredicate);
                }
            }

            return finalPredicate;
        };
    }
}
