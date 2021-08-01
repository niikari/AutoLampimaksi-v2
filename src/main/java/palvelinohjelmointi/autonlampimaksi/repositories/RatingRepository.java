package palvelinohjelmointi.autonlampimaksi.repositories;



import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import palvelinohjelmointi.autonlampimaksi.models.Rating;

@RepositoryRestResource
public interface RatingRepository extends CrudRepository<Rating, Long> {

}
