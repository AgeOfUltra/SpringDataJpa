package com.example.springbootjdbc.repo;

import com.example.springbootjdbc.data.Holidays;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  HolidayRepo extends CrudRepository<Holidays,String> {

}