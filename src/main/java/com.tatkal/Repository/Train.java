package com.tatkal.Repository;

import com.tatkal.dao.TrainDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Train extends JpaRepository<TrainDAO,String> {

}
