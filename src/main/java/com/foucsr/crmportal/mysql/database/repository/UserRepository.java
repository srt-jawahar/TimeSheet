package com.foucsr.crmportal.mysql.database.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.foucsr.crmportal.mysql.database.model.User;

/**
 * Created by FocusR .
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Override
    List<User> findAll();
    Optional<User> findByEmail(String email);
    
    Optional<User> findByEmployeeId(String employeeId);
    
    
    Optional<User> findById(String id);


    Optional<User> findByUsernameOrEmail(String username, String email);
    
    Optional<User> findByUsernameOrEmployeeId(String username, String employeeId);


    List<User> findByIdIn(List<Long> userIds);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    
    @Transactional
  	 @Modifying
  	 @Query(value = "DELETE FROM ROLES_MAP_BY_USERS WHERE user_id =:id ", nativeQuery = true)
  	    void deleteByIdInMap(@Param("id") long id);

    Boolean existsByEmployeeId(String employeeId);
   @Query(value = "SELECT * FROM USER_LOGIN_DETAILS a WHERE name = :name ", nativeQuery = true)
  List<User> findByFirstNameAndLastName(@Param("name") String firstName);
   
   @Query(value = "SELECT users.* FROM USER_LOGIN_DETAILS users,\n" + 
   		                  "ROLES_MAP_BY_USERS user_role_map,\n" + 
   		                  "USER_ROLES roles\n" + 
   		                  "where \n" + 
   		                  "users.id = user_role_map.user_id and\n" + 
   		                  "roles.id = user_role_map.role_id "
   		                  + " and roles.id != 1 "
   		                  + "order by users.id", nativeQuery = true)
   List<User> findListOfUsers();
   
  
   
   @Query(value = "SELECT * FROM USER_LOGIN_DETAILS WHERE agentId = :agentId AND (is_active is null or is_active = 'Y')", nativeQuery = true)
   List<User> findByAgentId(@Param("agentId") long agentId);
   
   @Query(value = "SELECT * FROM USER_LOGIN_DETAILS WHERE vendorID = :vendorId AND (is_active is null or is_active = 'Y')", nativeQuery = true)
   List<User> findByVendorId(@Param("vendorId") long vendorId);
   
   Optional<User> findByResetToken(String resetToken);
   
   @Query(value = "SELECT users.* FROM USER_LOGIN_DETAILS users,\n" + 
              "ROLES_MAP_BY_USERS user_role_map,\n" + 
              "USER_ROLES roles\n" + 
              "where \n" + 
              "users.id = user_role_map.user_id and\n" + 
              "roles.id = user_role_map.role_id "
              + "and roles.id = 1 "
              + "and users.username = 'sysadmin' "
              + "order by users.id", nativeQuery = true)
   Optional<User> findFirstAdmin();
   
   @Query(value = "SELECT DISTINCT users.USERNAME FROM USER_LOGIN_DETAILS users,\n" + 
           "ROLES_MAP_BY_USERS user_role_map,\n" + 
           "USER_ROLES roles\n" + 
           "where \n" + 
           "users.id = user_role_map.user_id and\n" + 
           "roles.id = user_role_map.role_id\n"
           + "and roles.name in ('ROLE_MANAGER','ROLE_SALES')", nativeQuery = true)
   List<String> findListOfLeadOwner();
   
   @Query(value = "SELECT * FROM USER_LOGIN_DETAILS WHERE id = :id AND (is_active is null or is_active = 'Y')", nativeQuery = true)
   Optional<User> findUser(@Param("id") long id);
   
   
   @Query(value = "SELECT * FROM USER_LOGIN_DETAILS WHERE id = :id ", nativeQuery = true)
   User findUserByIdToActiveOrInactive(@Param("id") long id);
   
   @Query(value = "SELECT * FROM USER_LOGIN_DETAILS WHERE id = :id ", nativeQuery = true)
   User findUsersByIdToActiveOrInactive(@Param("id") Long[] id);
   
   @Query(value = "SELECT * FROM USER_LOGIN_DETAILS WHERE username = :name ", nativeQuery = true)
   User findByTimesheetUsername(String name);

}
