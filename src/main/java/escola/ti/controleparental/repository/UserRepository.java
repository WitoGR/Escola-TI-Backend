package escola.ti.controleparental.repository;

import escola.ti.controleparental.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {}
