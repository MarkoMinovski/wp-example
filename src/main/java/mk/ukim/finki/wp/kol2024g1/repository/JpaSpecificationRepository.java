package mk.ukim.finki.wp.kol2024g1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface JpaSpecificationRepository<T,ID> extends JpaRepository<T, ID> {
    Page<T> findAll(Specification<T> filter, Pageable pageable);
}
