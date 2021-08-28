package tech.noahgeren.template.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import tech.noahgeren.template.domain.User;


public interface UserRepository extends JpaRepository<User, String>, UserRepositoryCustom {
	
	// Add JPA method stubs here
	
	@Repository
	class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

		public UserRepositoryImpl() {
			super(User.class);
		}
		
		// Implement QueryDSL methods here
	}

}

interface UserRepositoryCustom {
	
	// Add QueryDSL method stubs here
	
}

