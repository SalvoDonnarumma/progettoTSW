package it.unisa;

import java.sql.SQLException;  
import java.util.Collection;
import it.model.OrderedProduct;
import it.model.ProductBean;
import it.model.SizesBean;


public interface IProductDao {
	
	public void doSave(ProductBean product) throws SQLException;

	public boolean doDelete(int code) throws SQLException;

	public ProductBean doRetrieveByKey(int code) throws SQLException;
	
	public Collection<ProductBean> doRetrieveAll(String order) throws SQLException;

	SizesBean getSizesByKey(int code) throws SQLException;

	void setSizesByKey(int code, SizesBean taglie) throws SQLException;

	public int doRetrieveByName(String nome) throws SQLException;
	
	Collection<ProductBean> sortByCategoria(String order) throws SQLException;
	Collection<ProductBean> sortByName(String order) throws SQLException;

	void doUpdateSizes(int code, SizesBean sizes) throws SQLException;

	void doUpdate(int code, ProductBean product) throws SQLException;

	void decreaseSize(SizesBean sizes, int qnt, String size, int code) throws SQLException;

	OrderedProduct doRetrieveByKeyO(int code) throws SQLException;
}



