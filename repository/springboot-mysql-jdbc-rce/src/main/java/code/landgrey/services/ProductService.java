package code.landgrey.services;

import code.landgrey.commands.ProductForm;
import code.landgrey.domain.Product;

import java.util.List;


public interface ProductService {

    List<Product> listAll();

    Product getById(Long id);

    Product saveOrUpdate(Product product);

    void delete(Long id);

    Product saveOrUpdateProductForm(ProductForm productForm);
}
