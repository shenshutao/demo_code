package edu.nus.dao;

import edu.nus.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    Product select(
            @Param("id")
                    long id);

    void update(Product product);
}