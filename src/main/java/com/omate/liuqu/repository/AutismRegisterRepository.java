package com.omate.liuqu.repository;

import com.omate.liuqu.model.AutismRegister;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface AutismRegisterRepository extends CrudRepository<AutismRegister, Integer> {

}

